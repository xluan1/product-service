package com.xuanluan.mc.product.repository.product;

import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.product.model.entity.Product;
import com.xuanluan.mc.product.model.request.filter.ProductFilter;
import com.xuanluan.mc.repository.BaseRepository;
import com.xuanluan.mc.utils.MapUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
public class ProductRepositoryCustomImpl extends BaseRepository<Product> implements ProductRepositoryCustom<Product> {
    protected ProductRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Product.class);
    }

    @Override
    public Product findBySkuOrSlug(String clientId, String orgId, String sku, String slug) {
        refresh();
        List<Predicate> filtersOr = appendFilter("sku", sku, appendFilter("slug", slug, new ArrayList<>()));
        List<Predicate> filters = getFilters(clientId, orgId);
        if (filtersOr.size()>0){
            filters.add(builder.or(filtersOr.toArray(new Predicate[0])));
        }
        return getSingleResult(filters);
    }

    @Override
    public Product findByProductId(String clientId, String orgId, String productId) {
        refresh();
        return getSingleResult(appendFilter("productId", productId, getFilters(clientId, orgId)));
    }

    @Override
    public ResultList<Product> searchFullText(String clientId, String orgId, ProductFilter filter) {
        refresh();
        HashMap<String, String> filterSearch = MapUtils.append("name", filter.getSearch(), new HashMap<>());
        MapUtils.append("sku", filter.getSearch(), filterSearch);
        MapUtils.append("slug", filter.getSearch(), filterSearch);
        MapUtils.append("createdBy", filter.getSearch(), filterSearch);

        List<Predicate> filters = getFilterSearch(clientId, orgId, filterSearch, filter);
        appendFilter("isSelling", filter.getIsSelling(), filters);
        appendFilter("isSharing", filter.getIsSharing(), filters);
        return getResultList(filters, filter.getIndex(), filter.getMaxResult());
    }
}
