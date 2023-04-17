package com.xuanluan.mc.product.model.entity;

import com.xuanluan.mc.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * weight, volume, width, height, depth is cm unit
 *
 * @author Xuan Luan
 * @createdAt 12/24/2022
 */
@Getter
@Setter
@Entity
public class Product extends BaseEntity implements Serializable {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @Column(nullable = false, updatable = false)
    private String orgId;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false, updatable = false, unique = true)
    private String productId;
    @Column(nullable = false, updatable = false, unique = true)
    private String sku;
    @Column(nullable = false, updatable = false, unique = true)
    private String slug;
    private float weight;
    private float volume;//khối lượng của sản phẩm
    private float width;
    private float height;
    private float depth;
    private float rating;
    private int guaranteeDays;
    private int expiredDays;
    private boolean isSelling;
    private boolean isSharing;

    //
    private double salePrice;
    private double minPrice;
    private double maxPrice;
    private int currencyUnit;//value of CurrencyUnit enum

    @OneToOne
    @JoinColumn(name = "fileId", referencedColumnName = "id")
    private FileStorage fileStorage;
}
