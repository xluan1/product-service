package com.xuanluan.mc.product.model.entity;

import com.xuanluan.mc.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Xuan Luan
 * @createdAt 12/24/2022
 */
@Getter
@Setter
@Entity
public class Material extends BaseEntity {
    private String clientId;
    private String orgId;
    private String productId;
    private String name;
    private String description;
    private String country;
    private int quantity;
//    private int productUnit;
}
