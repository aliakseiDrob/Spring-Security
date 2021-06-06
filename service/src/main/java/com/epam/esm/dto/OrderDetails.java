package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDetails implements Serializable {

    LocalDateTime orderDate;
    BigDecimal orderCost;
}
