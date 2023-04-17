package com.xuanluan.mc.product.repository.product;

import com.xuanluan.mc.product.model.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, String>, ProductRepositoryCustom<Product> {
}
