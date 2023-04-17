package com.xuanluan.mc.product.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@Getter
@Setter
public class NewProductShare {
    private Set<String> productIds;
    private String toClientId;
    private Set<String> toOrgIds;
    private String byUser;
}
