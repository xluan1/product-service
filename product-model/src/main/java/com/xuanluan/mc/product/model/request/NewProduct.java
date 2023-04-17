package com.xuanluan.mc.product.model.request;

import com.xuanluan.mc.domain.model.request.FileRequest;
import com.xuanluan.mc.domain.validation.file.ValidImage;
import com.xuanluan.mc.product.model.enums.CurrencyUnit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@Getter
@Setter
public class NewProduct {
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    private String name;
    private String description;
    private boolean isSharing = false;
    private boolean isSelling = false;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{1,15}$", message = "Mã rút gọn từ 1-15 ký tự và không chứa ký tự đặc biệt")
    private String slug;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{1,30}$", message = "Mã định danh từ 1-30 và không chứa ký tự đặc biệt")
    private String sku ;
    private float weight = 0;
    private float volume = 0;
    private float width = 0;
    private float height = 0;
    private float depth = 0;
    private int guaranteeDays = 0;
    private int expiredDays = 0;
    private String byUser;

    //set min and max price of product
    private double minPrice = 0;
    private double salePrice = 0;
    private double maxPrice = Double.MAX_VALUE;
    private CurrencyUnit currency = CurrencyUnit.VND;

    //file
    @ValidImage
    private FileRequest file;
}
