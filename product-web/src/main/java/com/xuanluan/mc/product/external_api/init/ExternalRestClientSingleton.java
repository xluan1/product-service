package com.xuanluan.mc.product.external_api.init;

import com.xuanluan.mc.product.external_api.ExternalRestDto;
import com.xuanluan.mc.utils.BaseStringUtils;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuan Luan
 * @createdAt 4/2/2023
 */
public class ExternalRestClientSingleton {
    private static final Map<String, ExternalRestDto> externalRest = new HashMap<>();

    private ExternalRestClientSingleton() {

    }

    public static ExternalRestDto getExternalRest(String clientId) {
        Assert.isTrue(BaseStringUtils.hasTextAfterTrim(clientId), "clientId must not be is null");
        ExternalRestDto o = externalRest.get(clientId);
        if (o == null) {
            o = new ExternalRestDto(clientId);
            externalRest.put(clientId, o);
        }
        return o;
    }
}
