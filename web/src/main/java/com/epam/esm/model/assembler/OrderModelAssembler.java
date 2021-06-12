package com.epam.esm.model.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.AccountController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Account;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.AccountModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {

    public OrderModelAssembler() {
        super(AccountController.class, OrderModel.class);
    }

    @Override
    @NonNull
    public OrderModel toModel(@NonNull Order order) {
        OrderModel orderModel = instantiateModel(order);

        orderModel.setId(order.getId());
        orderModel.setOrderDate(order.getOrderDate());
        orderModel.setOrderCost(order.getOrderCost());
        orderModel.setAccountModel(toAccountModel(order.getAccount()));
        orderModel.setCertificate(toGiftCertificateModel(order.getCertificate()));

        return orderModel;
    }

    private GiftCertificateModel toGiftCertificateModel(GiftCertificate certificate) {

        return GiftCertificateModel.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDate(certificate.getCreateDate())
                .lastUpdateDate(certificate.getLastUpdateDate())
                .build()
                .add(linkTo(
                        methodOn(GiftCertificateController.class).getGiftCertificate(certificate.getId())).withSelfRel());
    }

    private AccountModel toAccountModel(Account account) {
        return AccountModel.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .build()
                .add(linkTo(
                        methodOn(AccountController.class).findAll(null)).withSelfRel());
    }
}