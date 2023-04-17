package com.xuanluan.mc.product.repository;

import com.xuanluan.mc.domain.model.filter.BaseFilter;
import com.xuanluan.mc.domain.model.filter.ResultList;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
public interface BaseProductRepository<T, S extends BaseFilter> {
    ResultList<T> searchFullText(String clientId, String orgId, S filter);

    T findByProductId(String clientId, String orgId, String productId);
}
