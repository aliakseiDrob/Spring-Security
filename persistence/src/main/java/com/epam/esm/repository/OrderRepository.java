package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Order entity
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * finds sublist orders by user id
     *
     * @param accountId account's id
     * @param pageable object for pagination information
     * @return A sublist of a list of objects
     */
    Page<Order> findAllByAccountId(Long accountId, Pageable pageable);

    /**
     * finds order by id and order id
     *
     * @param accountId Accounts's id
     * @param orderId Order's id
     * @return Optional Order can be null
     */
    Optional<Order> findByAccountIdAndId(Long accountId, Long orderId);
}
