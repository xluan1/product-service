package com.xuanluan.mc.product.model.entity;

import com.xuanluan.mc.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Xuan Luan
 * @createdAt 12/30/2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FileStorage extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String clientId;
    @Column(nullable = false, updatable = false)
    private String orgId;
    @Column(nullable = false, updatable = false)
    private String type; // png/jpg/...
    private String name;
    private String originFile;
    @Column(updatable = false)
    private long size;
    @Column(updatable = false)
    @Lob
    private byte[] data;
    @Column(nullable = false, updatable = false)
    private String entityId;
    @Column(nullable = false, updatable = false)
    private String entityClass;
}
