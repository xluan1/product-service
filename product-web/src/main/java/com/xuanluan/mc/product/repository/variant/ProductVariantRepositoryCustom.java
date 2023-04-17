package com.xuanluan.mc.product.repository.variant;

import com.xuanluan.mc.product.model.entity.ProductVariant;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
public interface ProductVariantRepositoryCustom {
    ProductVariant getNewestVariantOfProductId(String clientId, String orgId, String productId);
}
