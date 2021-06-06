package com.epam.esm.model.assembler;

import com.epam.esm.controller.AccountController;
import com.epam.esm.entity.Account;
import com.epam.esm.model.AccountModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountModelAssembler extends RepresentationModelAssemblerSupport<Account, AccountModel> {

    public AccountModelAssembler() {
        super(AccountController.class, AccountModel.class);
    }

    @Override
    @NonNull
    public AccountModel toModel(@NonNull Account account) {
        AccountModel accountModel = instantiateModel(account);
        accountModel.setId(account.getId());
        accountModel.setUserId(account.getUserId());
        accountModel.setUserName(account.getUserName());
             return accountModel;
    }

    @Override
    @NonNull
    public CollectionModel<AccountModel> toCollectionModel(
            @NonNull Iterable<? extends Account> entities) {
        CollectionModel<AccountModel> userModels = super.toCollectionModel(entities);
        userModels.add(linkTo(methodOn(AccountController.class)
                .findAll(PageRequest.of(0, 0))).withSelfRel());
        return userModels;
    }
}
