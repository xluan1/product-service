package com.xuanluan.mc.product.utils;

import com.xuanluan.mc.product.model.entity.Product;
import com.xuanluan.mc.product.model.entity.ProductShare;
import com.xuanluan.mc.product.model.entity.ProductVariant;
import com.xuanluan.mc.product.model.request.NewProduct;
import com.xuanluan.mc.product.model.request.NewProductShare;
import com.xuanluan.mc.product.model.request.ProductVariantInput;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
public class MapperUtils {
    /**
     * convert from NewProduct to Product
     *
     * @param request NewProduct
     */
    public static Product convertToProduct(NewProduct request) {
        Product product = new Product();
        product.setDepth(request.getDepth());
        product.setExpiredDays(request.getExpiredDays());
        product.setGuaranteeDays(request.getGuaranteeDays());
        product.setDescription(request.getDescription());
        product.setHeight(request.getHeight());
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setSlug(request.getSlug());
        product.setVolume(request.getVolume());
        product.setWeight(request.getWeight());
        product.setWidth(request.getWidth());
        product.setSelling(request.isSelling());
        product.setSharing(request.isSharing());
        product.setCreatedBy(request.getByUser());
        product.setCurrencyUnit(request.getCurrency().value);
        product.setMinPrice(request.getMinPrice());
        product.setMaxPrice(request.getMaxPrice());
        product.setSalePrice(request.getSalePrice());

        return product;
    }

    public static Product convertToProduct(String clientId, String orgId, String productId, NewProduct request) {
        Product product = convertToProduct(request);
        product.setOrgId(orgId);
        product.setClientId(clientId);
        product.setProductId(productId);

        return product;
    }

    /**
     * convert from NewProductVariant to ProductVariant
     *
     * @param request NewProductVariant
     */
    public static ProductVariant convertToProductVariant(String clientId, String orgId, Product product, ProductVariantInput request) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setProduct(product);
        productVariant.setClientId(clientId);
        productVariant.setOrgId(orgId);
        productVariant.setColor(request.getColor());
        productVariant.setDescription(request.getDescription());
        productVariant.setName(product.getName() + " - " + productVariant.getColor());

        return productVariant;
    }

    /**
     * convert from NewProductShare to List ProductShare
     *
     * @param request NewProductShare
     */
    public static List<ProductShare> convertToProductShares(String clientId, String orgId, String toOrgId, NewProductShare request) {
        return request.getProductIds().stream().map(productId -> {
            ProductShare share = new ProductShare();
            share.setClientId(clientId);
            share.setOrgId(orgId);
            share.setToClientId(request.getToClientId());
            share.setToOrgId(toOrgId);
            share.setCreatedBy(request.getByUser());
            share.setProductId(productId);
            return share;
        }).collect(Collectors.toList());
    }
}
