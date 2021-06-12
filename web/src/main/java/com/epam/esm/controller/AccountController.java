package com.epam.esm.controller;

import com.epam.esm.dto.OrderDetails;
import com.epam.esm.dto.Purchase;
import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Account;
import com.epam.esm.model.AccountModel;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.assembler.OrderModelAssembler;
import com.epam.esm.model.assembler.AccountModelAssembler;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.AccountService;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@Data
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final OrderService orderService;
    private final TagService tagService;
    private final PagedResourcesAssembler<Account> accountPagedResourcesAssembler;
    private final AccountModelAssembler accountModelAssembler;
    private final PagedResourcesAssembler<Order> orderPagedResourcesAssembler;
    private final OrderModelAssembler orderModelAssembler;


    @GetMapping
    @RolesAllowed("ADMIN")
    public PagedModel<AccountModel> findAll(Pageable pageable) {
        return accountPagedResourcesAssembler.toModel(accountService.findAll(pageable), accountModelAssembler);
    }

    @GetMapping("{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public AccountModel findById(@PathVariable Long id) {
        return accountModelAssembler.toModel(accountService.findById(id));
    }

    @PostMapping("/{accountId}/orders")
    @RolesAllowed({"ADMIN", "USER"})
    @ResponseStatus(HttpStatus.CREATED)
    public Long createOrder(@PathVariable Long accountId, @RequestBody Purchase purchase) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        verifyPrinciple(accountId, authentication);
        return orderService.save(purchase);
    }

    @GetMapping("/{accountId}/orders")
    @RolesAllowed({"ADMIN", "USER"})
    public PagedModel<OrderModel> getUserOrders(@PathVariable Long accountId, Pageable pageable) {
        return orderPagedResourcesAssembler.toModel(orderService.findAllAccountOrders(accountId, pageable),
                orderModelAssembler);
    }

    @GetMapping("/{accountId}/orders/{orderId}")
    @RolesAllowed({"ADMIN", "USER"})
    public OrderDetails getUserOrder(@PathVariable Long accountId, @PathVariable Long orderId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        verifyPrinciple(accountId, authentication);
        return orderService.findUserOrder(accountId, orderId);
    }

    @GetMapping("/{userId}/mostUsedTag")
    @RolesAllowed({"ADMIN", "USER"})
    public MostWidelyUsedTag getMostWidelyUsedTag(@PathVariable Long userId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        verifyPrincipleOrAdmin(userId, authentication);
        return tagService.getMostWidelyUsedTag(userId);
    }

    private void verifyPrincipleOrAdmin(Long id, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .map(String::valueOf)
                .noneMatch(role -> role.equals("ROLE_ADMIN"))) {
            verifyPrinciple(id, authentication);
        }
    }

    private void verifyPrinciple(Long id, Authentication authentication) {
        String userId = authentication.getName();
        Account account = accountService.findById(id);
        if (!account.getUserId().equals(userId)) {
            throw new AccessDeniedException("denied in access!");
        }
    }
}