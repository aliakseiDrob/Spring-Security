package com.epam.esm.service;

import com.epam.esm.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for serving User objects according to the business logic of User
 */
public interface AccountService {

    Page<Account> findAll(Pageable pageable);

    Account findById(Long id);

    public Account findByUserId(String userId);

}
