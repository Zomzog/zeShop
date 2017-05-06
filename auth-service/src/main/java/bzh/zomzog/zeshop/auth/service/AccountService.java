package bzh.zomzog.zeshop.auth.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import bzh.zomzog.zeshop.auth.domain.Account;
import bzh.zomzog.zeshop.auth.exception.LoginAlreadyInUseException;
import bzh.zomzog.zeshop.auth.repository.AccountRepository;
import bzh.zomzog.zeshop.auth.service.dto.AccountDTO;
import bzh.zomzog.zeshop.auth.service.dto.ManagedAccountDTO;
import bzh.zomzog.zeshop.auth.service.mapper.AccountMapper;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper     accountMapper;

    public AccountService(final AccountRepository accountRepository, final AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public AccountDTO registerAccount(final ManagedAccountDTO managedAccountDTO) throws LoginAlreadyInUseException {

        final Optional<Account> inbaseAccount = this.accountRepository
                .findOneByLogin(managedAccountDTO.getLogin().toLowerCase());
        if (inbaseAccount.isPresent()) {
            throw new LoginAlreadyInUseException();
        }

        final Account account = this.accountMapper.map(managedAccountDTO);
        this.accountRepository.save(account);
        // mailService.sendActivationEmail(user);
        final AccountDTO accountDTO = this.accountMapper.map(account);
        return accountDTO;
    }

}
