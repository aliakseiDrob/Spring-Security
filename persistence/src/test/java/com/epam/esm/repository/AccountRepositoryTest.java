package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository userRepository;

    @Test
    public void testFindByByUserIdShouldReturnAccountByUserId() {
        //given
        Optional<Account> optionalAccount = Optional.of(new Account(1L, "first", "Ivan"));
        //then
        assertEquals(optionalAccount, userRepository.findByUserId("first"));
    }

    @Test
    public void testFindByUserIdShouldReturnEmptyOptionalIfAccountNotExists() {
        //given
        Optional<Account> emptyUser = Optional.empty();
        //then
        assertEquals(emptyUser, userRepository.findByUserId("someId"));
    }

    @Test
    public void testFindByIdShouldReturnAccountById() {
        //given
        Optional<Account> optionalAccount = Optional.of(new Account(1L, "first", "Ivan"));
        //then
        assertEquals(optionalAccount, userRepository.findById(1L));
    }

    @Test
    public void testFindByIdShouldReturnEmptyOptionalIfAccountNotExists() {
        //given
        Optional<Account> emptyUser = Optional.empty();
        //then
        assertEquals(emptyUser, userRepository.findById(7L));
    }

    @Test
    public void testExistsByUserIdShouldReturnTrueIfAccountExists() {
        //then
        assertTrue(userRepository.existsByUserId("first"));
    }

    @Test
    public void testExistsByUserIdShouldReturnFalseIfAccountExists() {
        //then
        assertFalse(userRepository.existsByUserId("some id"));
    }
}
