package com.xuanluan.mc.product.repository.product_share;

import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.product.model.request.filter.ProductShareFilter;
import com.xuanluan.mc.product.repository.BaseProductRepository;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
public interface ProductShareRepositoryCustom<T> extends BaseProductRepository<T, ProductShareFilter> {
    ResultList<T> searchFromAnotherClient(String toClientId, String toOrgId, ProductShareFilter filter);

    T findByProductIdFromAnotherClient(String toClientId, String toOrgId, String productId);
}
