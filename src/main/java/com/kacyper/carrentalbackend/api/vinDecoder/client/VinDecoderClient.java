package com.kacyper.carrentalbackend.api.vinDecoder.client;

import com.kacyper.carrentalbackend.api.vinDecoder.config.VinDecoderConfig;
import com.kacyper.carrentalbackend.dto.api.vinDecoder.VinDecoderDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class VinDecoderClient {

    private final RestTemplate restTemplate;
    private final VinDecoderConfig vinDecoderConfig;

    public VinDecoderClient(RestTemplate restTemplate, VinDecoderConfig vinDecoderConfig) {
        this.restTemplate = restTemplate;
        this.vinDecoderConfig = vinDecoderConfig;
    }

    public VinDecoderDto vinToBeDecoded(String vin) {
        URI uri = getVinApiUri(vin);
        return restTemplate.postForObject(uri, vin, VinDecoderDto.class);
    }

    private URI getVinApiUri(String vin) {
        return UriComponentsBuilder.fromHttpUrl(vinDecoderConfig.getVinEndpoint() + vin)
                .queryParam("format", "json")
                .build().encode().toUri();
    }

}
