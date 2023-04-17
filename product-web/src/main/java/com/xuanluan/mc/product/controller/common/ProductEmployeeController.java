package com.xuanluan.mc.product.controller.common;

import com.xuanluan.mc.controller.BaseController;
import com.xuanluan.mc.domain.model.WrapperResponse;
import com.xuanluan.mc.domain.model.filter.ResultList;
import com.xuanluan.mc.product.model.entity.Product;
import com.xuanluan.mc.product.model.entity.ProductVariant;
import com.xuanluan.mc.product.model.request.NewProduct;
import com.xuanluan.mc.product.model.request.NewProductVariantsInput;
import com.xuanluan.mc.product.model.request.filter.ProductFilter;
import com.xuanluan.mc.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("emp/1.0.0")
public class ProductEmployeeController extends BaseController {
    private final IProductService productService;

    @PostMapping(value = "create_new_product/{orgId}")
    public WrapperResponse<Product> createNewProduct(@RequestHeader String clientId,
                                                     @PathVariable String orgId,
                                                     @RequestBody @Valid NewProduct request) {
        return response("Thêm sản phẩm mới thành công!", productService.createNewProduct(clientId, orgId, request));
    }

    @PostMapping(value = "create_new_product/{orgId}/{productId}")
    public WrapperResponse<List<ProductVariant>> addVariantForProduct(@RequestHeader String clientId,
                                                                      @PathVariable String orgId,
                                                                      @PathVariable String productId,
                                                                      @RequestBody @Valid NewProductVariantsInput request) {
        return response("Thêm biến thể mới cho sản phẩm thành công!", productService.addVariantsForProduct(clientId, orgId, productId, request));
    }

    @PostMapping(value = "search_product/{orgId}")
    public WrapperResponse<ResultList<Product>> searchProduct(@RequestHeader String clientId,
                                                              @PathVariable String orgId,
                                                              @RequestBody ProductFilter request) {
        return response("Tìm kiếm sản phẩm thành công!", productService.searchProduct(clientId, orgId, request));
    }
}
