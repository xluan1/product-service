package com.xuanluan.mc.product.repository.product_share;

import com.xuanluan.mc.product.model.entity.ProductShare;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@Repository
public interface ProductShareRepository extends CrudRepository<ProductShare, String>, ProductShareRepositoryCustom<ProductShare> {
}
