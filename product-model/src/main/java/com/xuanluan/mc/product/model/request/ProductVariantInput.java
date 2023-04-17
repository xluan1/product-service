package com.xuanluan.mc.product.model.request;

import com.xuanluan.mc.domain.model.request.FileRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@Getter
@Setter
@Builder
public class ProductVariantInput {
    private String color;
    private String description;
    private FileRequest file;
}
