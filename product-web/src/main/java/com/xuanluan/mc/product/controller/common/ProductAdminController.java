package com.xuanluan.mc.product.controller.common;

import com.xuanluan.mc.controller.BaseController;
import com.xuanluan.mc.domain.model.WrapperResponse;
import com.xuanluan.mc.product.model.entity.ProductShare;
import com.xuanluan.mc.product.model.request.NewProductShare;
import com.xuanluan.mc.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xuan Luan
 * @createdAt 3/30/2023
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("ad/1.0.0")
public class ProductAdminController extends BaseController {
    private final IProductService productService;

    @PostMapping(value = "share_products_client/{clientId}/{orgId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public WrapperResponse<List<ProductShare>> shareProductsForClient(@PathVariable String clientId,
                                                                      @PathVariable String orgId,
                                                                      @RequestBody NewProductShare request) {
        return response("Chia sẻ dữ liệu thành công!", productService.shareProductsForClient(clientId, orgId, request));
    }
}
