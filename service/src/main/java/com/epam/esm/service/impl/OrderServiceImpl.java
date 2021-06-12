package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDetails;
import com.epam.esm.dto.Purchase;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Account;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.repository.AccountRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Data
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Override
    @Transactional
    public Long save(Purchase purchase) {
        Account account = accountRepository.findById(purchase.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found", ErrorCode.USER_NOT_FOUND_ERROR.getCode()));
        GiftCertificate certificate = giftCertificateRepository.findById(purchase.getCertificateId())
                .orElseThrow(() -> new EntityNotFoundException("Certificate not found",  ErrorCode.CERTIFICATE_NOT_FOUND_ERROR.getCode()));
        Order order = new Order();
        order.setAccount(account);
        order.setCertificate(certificate);
        return orderRepository.save(order).getId();
    }

    @Override
    public Page<Order> findAllAccountOrders(Long accountId, Pageable pageable) {
        return orderRepository.findAllByAccountId(accountId, pageable);
    }

    @Override
    public OrderDetails findUserOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByAccountIdAndId(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not exists", ErrorCode.ORDER_NOT_FOUND_ERROR.getCode()));
        return new OrderDetails(order.getOrderDate(), order.getOrderCost());
    }
}
