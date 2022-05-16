package com.kacyper.carrentalbackend.api.vinDecoder.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class VinDecoderConfig {

    @Value("${vin.api.endpoint}")
    private String vinEndpoint;

}