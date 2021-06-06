package com.epam.esm.servise.impl;

import com.epam.esm.entity.Account;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.AccountRepository;
import com.epam.esm.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    private static List<Account> accounts;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl service;

    @BeforeAll
    public static void init() {
        accounts = Arrays.asList(new Account(1L, "first_user_id", "fist"),
                new Account(2L, "second_user_id", "second"));
    }

    @Test
    public void testFindAllShouldReturnAllAccounts() {
        //given
        Page<Account> accountPage = new PageImpl<>(accounts);
        //when
        when(accountRepository.findAll(Pageable.unpaged())).thenReturn(accountPage);

        assertEquals(service.findAll(Pageable.unpaged()), accountPage);
        verify(accountRepository).findAll(Pageable.unpaged());
    }

    @Test
    public void testGetByIdShouldReturnAccountWhenAccountExists() {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(accounts.get(0)));
        //then
        assertEquals(accounts.get(0), service.findById(1L));
        verify(accountRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetByIdShouldTrowExceptionWhenAccountNotExists() {
        //when
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

        verify(accountRepository, times(1)).findById(anyLong());
    }


    @Test
    public void testSaveAccountShouldReturnSavedAccountId() {
        //given
        String userId = "first_user_id";
        String userName = "fist";

        // when
        when(accountRepository.save(any(Account.class))).thenReturn(accounts.get(0));
        when(accountRepository.existsByUserId(userId)).thenReturn(true);
        //then
        assertEquals(1L, service.saveAccount(userId, userName));

        verify(accountRepository, times(1)).save(any(Account.class));

    }

    @Test
    public void testShouldThrowExceptionWhenUserExists() {
        //given
        String userId = "first_user_id";
        String userName = "fist";
        // when
        when(accountRepository.existsByUserId(userId)).thenReturn(false);
        //then
        assertThrows(EntityExistsException.class, () -> service.saveAccount(userId, userName));

        verify(accountRepository, times(1)).existsByUserId(anyString());
    }
}