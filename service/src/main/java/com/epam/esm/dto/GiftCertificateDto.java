package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto implements Serializable {
    Long id;
    String name;
    String description;
    BigDecimal price;
    int duration;
    Set<TagDto> tags;
}
