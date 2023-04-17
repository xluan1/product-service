package com.xuanluan.mc.product.service;

import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.exception.ServiceException;
import com.xuanluan.mc.product.ProductServiceApplication;
import com.xuanluan.mc.product.model.entity.Product;
import com.xuanluan.mc.product.model.entity.ProductVariant;
import com.xuanluan.mc.product.model.enums.CurrencyUnit;
import com.xuanluan.mc.product.model.request.NewProduct;
import com.xuanluan.mc.product.model.request.NewProductVariantsInput;
import com.xuanluan.mc.product.model.request.ProductVariantInput;
import com.xuanluan.mc.product.model.request.filter.ProductFilter;
import com.xuanluan.mc.utils.BaseStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Xuan Luan
 * @createdAt 4/6/2023
 */
@SpringBootTest(classes = ProductServiceApplication.class)
public class ProductServiceTest {
    @Autowired
    private IProductService productService;

    private final String clientId = "test-cProduct-1";
    private final String orgId = "test-oProduct-1";
    private final String byUser = "Luan";

    private NewProduct initNewProduct() {
        NewProduct newProduct = new NewProduct();
        newProduct.setByUser(byUser);
        newProduct.setMaxPrice(200);
        newProduct.setCurrency(CurrencyUnit.VND);
        newProduct.setMinPrice(50);
        newProduct.setSalePrice(100);
        newProduct.setName("san pham test");
        return newProduct;
    }

    private Product createNewProduct() {
        NewProduct newProduct = initNewProduct();
        Product result = productService.createNewProduct(clientId, orgId, newProduct);
        assertNotNull(result);
        assertTrue(BaseStringUtils.hasTextAfterTrim(result.getProductId()));
        assertEquals(result.getMinPrice(), newProduct.getMinPrice());
        assertEquals(result.getMaxPrice(), newProduct.getMaxPrice());
        assertEquals(result.getSalePrice(), newProduct.getSalePrice());
        assertEquals(result.getName(), newProduct.getName());
        assertEquals(result.getClientId(), clientId);
        assertEquals(result.getOrgId(), orgId);
        assertEquals(result.getCreatedBy(), newProduct.getByUser());
        assertEquals(result.getSku(), result.getProductId());
        assertEquals(result.getSlug(), result.getProductId());
        return result;
    }

    private Product getProductFirst() {
        ProductFilter filter = new ProductFilter();
        filter.setIsActive(true);
        filter.setMaxResult(1);
        ResultList<Product> resultList = productService.searchProduct(clientId, orgId, filter);
        if (resultList.getResultList().size() > 0) {
            assertNotNull(resultList);
            assertEquals(filter.getMaxResult(), resultList.getMaxResult());
            assertTrue(resultList.getTotal() > 0);
            return resultList.getResultList().get(0);
        } else {
            return createNewProduct();
        }
    }

    @Test
    public void testCreateProduct() {
        try {
            createNewProduct();
        } catch (ServiceException e) {
            fail(e.getStatus() + "->" + e.getMessage());
        }
    }

    private List<ProductVariantInput> initProductVariantInputs() {
        return List.of(
                ProductVariantInput.builder().color("đen").description("variant den").build(),
                ProductVariantInput.builder().color("xanh").description("variant xanh").build()
        );
    }

    private NewProductVariantsInput initNewProductVariants() {
        return NewProductVariantsInput.builder().byUser(byUser).variants(initProductVariantInputs()).build();
    }

    private List<ProductVariant> addVariantsForProduct(Product product) {
        NewProductVariantsInput request = initNewProductVariants();
        List<ProductVariant> result = productService.addVariantsForProduct(clientId, orgId, product.getProductId(), request);
        assertNotNull(result);
        assertEquals(result.size(), request.getVariants().size());

        Map<String, String> itemIds = new HashMap<>();
        for (ProductVariant variant : result) {
            assertNotNull(variant.getColor());
            assertTrue(variant.isActive());
            assertEquals(variant.getProduct().getClientId(), product.getClientId());
            assertEquals(variant.getProduct().getMinPrice(), product.getMinPrice());
            assertEquals(variant.getProduct().getMaxPrice(), product.getMaxPrice());
            assertEquals(variant.getProduct().getSalePrice(), product.getSalePrice());
            assertEquals(variant.getProduct().getName(), product.getName());
            assertEquals(variant.getProduct().getClientId(), product.getClientId());
            assertEquals(variant.getProduct().getOrgId(), product.getOrgId());
            assertEquals(variant.getProduct().getCreatedBy(), product.getCreatedBy());

            String itemId = product.getProductId() + "." + variant.getPVariantId();
            assertFalse(itemIds.containsKey(itemId));
            itemIds.put(itemId, itemId);
            assertEquals(variant.getSku(), itemId);
            String variantName = product.getName() + " - " + variant.getColor();
            assertEquals(variant.getName(), variantName);
        }
        return result;
    }

    @Test
    public void testAddVariantsForProduct() {
        try {
            Product productFirst = getProductFirst();
            addVariantsForProduct(productFirst);
        } catch (ServiceException e) {
            fail(e.getStatus() + "->" + e.getMessage());
        }
    }
}
