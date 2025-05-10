package com.order.tracking.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String zipCode;
    private String baseAddress;
    private String detailAddress;
    private String extraAddress;

    public String getFullAddress() {
        return String.format("%s %s %s", baseAddress, detailAddress, extraAddress != null ? extraAddress : "").trim();
    }
} 