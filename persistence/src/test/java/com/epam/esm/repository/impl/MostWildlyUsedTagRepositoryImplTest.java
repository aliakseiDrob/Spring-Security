package com.epam.esm.repository.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.repository.MostWidelyUsedTagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.NoResultException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = TestConfig.class)
public class MostWildlyUsedTagRepositoryImplTest {

    private static final MostWidelyUsedTag MOST_WIDELY_USED_TAG = new MostWidelyUsedTag(1L, "first",
            new BigDecimal("340.00"));

    @Autowired
    MostWidelyUsedTagRepository mostWidelyUsedTagRepository;

    @Test
    public void testGetMostWildlyUsedTagShouldReturnMostWildlyUsedTag() {
        //then
        assertEquals(MOST_WIDELY_USED_TAG, mostWidelyUsedTagRepository.findMostWidelyUsedTag(1L));
    }

    @Test
    public void testGetMostWildlyUsedTagShouldThrowExceptionIfTagNotExists() {
        assertThrows(NoResultException.class, () -> mostWidelyUsedTagRepository.findMostWidelyUsedTag(5L));
    }
}