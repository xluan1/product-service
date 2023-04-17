package com.xuanluan.mc.product.model.entity;

import com.xuanluan.mc.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Xuan Luan
 * @createdAt 2/27/2023
 */
@Getter
@Setter
@Entity
public class ProductShare extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @Column(nullable = false, updatable = false)
    private String orgId;
    @Column(nullable = false, updatable = false)
    private String toClientId;
    @Column(nullable = false, updatable = false)
    private String toOrgId;
    @Column(nullable = false, updatable = false)
    private String productId;
    private double officialPrice;
    private boolean isOpening;
}
