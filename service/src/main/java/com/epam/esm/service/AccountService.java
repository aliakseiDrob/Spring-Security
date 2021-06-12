package com.epam.esm.service;

import com.epam.esm.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for serving Account objects according to the business logic of Account
 */
public interface AccountService {

    Page<Account> findAll(Pageable pageable);

    Account findById(Long id);

    Long saveAccount(String userId, String userName);

}
