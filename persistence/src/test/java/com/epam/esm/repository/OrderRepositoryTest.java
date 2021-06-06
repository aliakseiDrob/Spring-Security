package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Account;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@Transactional
public class OrderRepositoryTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Account ACCOUNT_INSTANCE = new Account(1L, "first", "Ivan");
    private static final List<Order> ACCOUNT_ORDERS =
            Arrays.asList(new Order(1L, LocalDateTime.parse("2021-05-24 20:11:10", DATE_TIME_FORMATTER),
                            new BigDecimal("340.00"), ACCOUNT_INSTANCE, null),
                    new Order(2L, LocalDateTime.parse("2021-05-30 20:11:10", DATE_TIME_FORMATTER),
                            new BigDecimal("100.00"), ACCOUNT_INSTANCE, null));
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testGetAllAccountOrdersShouldReturnAllAccountOrders() {
        //then
        assertEquals(new PageImpl<>(ACCOUNT_ORDERS), orderRepository.findAllByAccountId(1L, Pageable.unpaged()));
    }

    @Test
    public void testGetAccountOrderShouldReturnOrder() {
        //given
        Optional<Order> expectedOrder = Optional.of(ACCOUNT_ORDERS.get(0));
        //then
        assertEquals(expectedOrder, orderRepository.findByAccountIdAndId(1L, 1L));
    }

    @Test
    public void testCreateOrderShouldCreateOrder() {
        //given
        Account user = new Account(1L, "first", "Ivan");
        GiftCertificate giftCertificate = new GiftCertificate(1L, "first", "for men",
                new BigDecimal("128.01"), 11, 1, LocalDateTime.now(), LocalDateTime.now(), null);
        Order order = new Order(3L, LocalDateTime.parse("2021-03-24 20:11:10", DATE_TIME_FORMATTER),
                new BigDecimal("128.01"), user, giftCertificate);

        //then
        Order savedOrder = orderRepository.save(order);

        assertEquals(3L, savedOrder.getId());
        assertEquals(LocalDateTime.now().format(FORMATTER), savedOrder.getOrderDate().format(FORMATTER));
        assertEquals(giftCertificate.getPrice(), savedOrder.getOrderCost());
        assertEquals(user.getId(), savedOrder.getAccount().getId());
    }
}
