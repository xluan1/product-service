package com.xuanluan.mc.product.repository.variant;

import com.xuanluan.mc.product.model.entity.ProductVariant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@Repository
public interface ProductVariantRepository extends CrudRepository<ProductVariant, String>, ProductVariantRepositoryCustom {
}
