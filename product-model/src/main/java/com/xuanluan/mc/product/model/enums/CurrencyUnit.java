package com.xuanluan.mc.product.model.enums;

import lombok.RequiredArgsConstructor;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@RequiredArgsConstructor
public enum CurrencyUnit {
    VND(1, "₫"), USD(2, "$"), EUR(3, "€");

    public final int value;
    public final String symbol;
}
