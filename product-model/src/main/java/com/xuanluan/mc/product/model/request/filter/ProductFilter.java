package com.xuanluan.mc.product.model.request.filter;

import com.xuanluan.mc.domain.model.filter.BaseFilter;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xuan Luan
 * @createdAt 3/2/2023
 */
@Getter
@Setter
public class ProductFilter extends BaseFilter {
    private String productId;
    private Boolean isSharing;
    private Boolean isSelling;
}
