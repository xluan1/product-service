package com.xuanluan.mc.product.model.entity;

import com.xuanluan.mc.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Xuan Luan
 * @createdAt 12/24/2022
 */
@Getter
@Setter
@Entity
public class ProductVariant extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @Column(nullable = false, updatable = false)
    private String orgId;
    @Column(nullable = false, updatable = false)
    private String pVariantId;
    private String sku;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String color;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)
    private Product product;
    @OneToOne
    @JoinColumn(name = "fileId", referencedColumnName = "id")
    private FileStorage fileStorage;
}
