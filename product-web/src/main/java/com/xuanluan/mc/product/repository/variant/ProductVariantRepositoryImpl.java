package com.xuanluan.mc.product.repository.variant;

import com.xuanluan.mc.product.model.entity.ProductVariant;
import com.xuanluan.mc.repository.BaseRepository;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
public class ProductVariantRepositoryImpl extends BaseRepository<ProductVariant> implements ProductVariantRepositoryCustom {
    protected ProductVariantRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ProductVariant.class);
    }

    @Override
    public ProductVariant getNewestVariantOfProductId(String clientId, String orgId, String productId) {
        refresh();
        List<Predicate> filters = getFilters(clientId, orgId);
        appendFilter(builder.equal(root.join("product", JoinType.INNER).get("productId"), productId), filters);
        return getSingleResult(filters, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
