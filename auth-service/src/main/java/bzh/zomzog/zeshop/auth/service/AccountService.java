package bzh.zomzog.zeshop.auth.service;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.exception.LoginAlreadyInUseException;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import bzh.zomzog.zeshop.auth.service.mapper.AccountMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService {

    private static final int RANDOM_KEY_SIZE = 20;

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final MailService mailService;


    public AccountService(final AccountRepository accountRepository, final AccountMapper accountMapper, final MailService mailService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.mailService = mailService;
    }

    @Transactional
    public AccountDTO registerAccount(final ManagedAccountDTO managedAccountDTO) throws LoginAlreadyInUseException {

        final Optional<Account> inbaseAccount = this.accountRepository
                .findOneByLogin(managedAccountDTO.getLogin().toLowerCase());
        if (inbaseAccount.isPresent()) {
            throw new LoginAlreadyInUseException();
        }

        final Account account = this.accountMapper.map(managedAccountDTO);

        account.setActivationKey(RandomStringUtils.randomAlphanumeric(RANDOM_KEY_SIZE));

        this.accountRepository.save(account);
        this.mailService.sendActivationEmail(account);
        final AccountDTO accountDTO = this.accountMapper.map(account);
        return accountDTO;
    }

    @Transactional
    public Account activateRegistration(final String activationKey) {
        final Optional<Account> account = this.accountRepository.findOneByActivationKey(activationKey);
        return account.map(acc -> acc.activated(true))
                .map(acc -> acc.activationKey(null))
                .map(this.accountRepository::save)
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
