package com.xuanluan.mc.product.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 3/30/2023
 */
@Getter
@Setter
@Builder
public class NewProductVariantsInput {
    private List<ProductVariantInput> variants;
    private String byUser;
}
