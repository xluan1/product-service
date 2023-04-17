package com.xuanluan.mc.product.repository.product;

import com.xuanluan.mc.product.model.request.filter.ProductFilter;
import com.xuanluan.mc.product.repository.BaseProductRepository;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
public interface ProductRepositoryCustom<T> extends BaseProductRepository<T, ProductFilter> {
    T findBySkuOrSlug(String clientId, String orgId, String sku, String slug);
}
