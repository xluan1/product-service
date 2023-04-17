package com.xuanluan.mc.product.repository.product_share;

import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.product.model.entity.ProductShare;
import com.xuanluan.mc.product.model.request.filter.ProductShareFilter;
import com.xuanluan.mc.repository.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
public class ProductShareRepositoryCustomImpl extends BaseRepository<ProductShare> implements ProductShareRepositoryCustom<ProductShare> {
    protected ProductShareRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, ProductShare.class);
    }

    /**
     * @param clientId is clientId
     * @param orgId    is orgId
     */
    @Override
    public ResultList<ProductShare> searchFullText(String clientId, String orgId, ProductShareFilter filter) {
        refresh();
        HashMap<String, String> filterSearch = new HashMap<>();
        filterSearch.put("productId", filter.getSearch());
        filterSearch.put("toClientId", filter.getSearch());
        filterSearch.put("toOrgId", filter.getSearch());
        List<Predicate> filters = appendFilter("productId", filter.getProductId(), getFilterSearch(clientId, orgId, filterSearch, filter));
        return getResultList(filters, filter.getIndex(), filter.getMaxResult());
    }

    @Override
    public ProductShare findByProductId(String clientId, String orgId, String productId) {
        refresh();
        return getSingleResult(appendFilter("productId", productId, getFilters(clientId, orgId)));
    }

    @Override
    public ResultList<ProductShare> searchFromAnotherClient(String toClientId, String toOrgId, ProductShareFilter filter) {
        refresh();
        HashMap<String, String> filterSearch = new HashMap<>();
        filterSearch.put("productId", filter.getSearch());
        filterSearch.put("clientId", filter.getSearch());
        filterSearch.put("orgId", filter.getSearch());
        List<Predicate> filters = getFilterSearch(null, null, filterSearch, filter);
        appendFilter("toClientId", toClientId, filters);
        appendFilter("toOrgId", toOrgId, filters);
        appendFilter("productId", filter.getProductId(), filters);
        return getResultList(filters, filter.getIndex(), filter.getMaxResult());
    }

    @Override
    public ProductShare findByProductIdFromAnotherClient(String toClientId, String toOrgId, String productId) {
        refresh();
        List<Predicate> filters = new ArrayList<>();
        appendFilter("toClientId", toClientId, filters);
        appendFilter("toOrgId", toOrgId, filters);
        appendFilter("productId", productId, filters);
        return getSingleResult(filters);
    }
}
