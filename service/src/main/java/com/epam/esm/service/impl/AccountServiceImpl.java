package com.epam.esm.service.impl;

import com.epam.esm.entity.Account;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.AccountRepository;
import com.epam.esm.service.AccountService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.epam.esm.exception.ErrorCode.ACCOUNT_EXISTS_ERROR;
import static com.epam.esm.exception.ErrorCode.ACCOUNT_NOT_FOUND_ERROR;

@Service
@Data
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found", ACCOUNT_NOT_FOUND_ERROR.getCode()));
    }

    @Override
    public Long saveAccount(String userId, String userName) {
        if (!accountRepository.existsByUserId(userId)) {
            throw new EntityExistsException("Account already exists", ACCOUNT_EXISTS_ERROR.getCode());
        }
        Account account = new Account(0L, userId, userName);
        return accountRepository.save(account).getId();
    }
}
