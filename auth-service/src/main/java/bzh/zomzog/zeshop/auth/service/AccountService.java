package bzh.zomzog.zeshop.auth.service;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.exception.LoginAlreadyInUseException;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import bzh.zomzog.zeshop.auth.service.mapper.AccountMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for managing Accounts
 */
@Service
@Transactional
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

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

    @Transactional(readOnly = true)
    public Page<AccountDTO> findAll(final Pageable pageable) {
        this.log.debug("Request to get all accounts");
        final Page<Account> result = this.accountRepository.findAll(pageable);
        return result.map(account -> this.accountMapper.map(account));
    }
}
