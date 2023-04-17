package com.xuanluan.mc.product.service;

import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.product.model.entity.Product;
import com.xuanluan.mc.product.model.entity.ProductShare;
import com.xuanluan.mc.product.model.entity.ProductVariant;
import com.xuanluan.mc.product.model.request.*;
import com.xuanluan.mc.product.model.request.filter.ProductFilter;

import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
public interface IProductService {
    Product createNewProduct(String clientId, String orgId, NewProduct request);

    List<ProductVariant> addVariantsForProduct(String clientId, String orgId, String productId, NewProductVariantsInput request);

    ResultList<Product> searchProduct(String clientId, String orgId, ProductFilter filter);

    List<ProductShare> shareProductsForClient(String clientId, String orgId, NewProductShare request);

    ProductShare updateProductShare(String clientId, String orgId, String productId, ChangeProductShare request);
}
