package com.xuanluan.mc.product.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@Getter
@Setter
public class ChangeProductShare {
    private Double officialPrice;
    private boolean isOpen;
    private String byUser;
}
