package com.xuanluan.mc.product.external_api;

import com.xuanluan.mc.restclient.OrganizationEmpRestClient;
import lombok.Getter;

/**
 * @author Xuan Luan
 * @createdAt 4/2/2023
 */
@Getter
public class ExternalRestDto {
    private final OrganizationEmpRestClient orgEmpRest;

    public ExternalRestDto(String clientId) {
        this.orgEmpRest = new OrganizationEmpRestClient(clientId);
    }
}
