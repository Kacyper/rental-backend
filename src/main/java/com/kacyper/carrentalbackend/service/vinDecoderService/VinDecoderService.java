package com.kacyper.carrentalbackend.service.vinDecoderService;

import com.kacyper.carrentalbackend.api.vinDecoder.client.VinDecoderClient;
import com.kacyper.carrentalbackend.dto.api.vinDecoder.VinDecoderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VinDecoderService {

    private final VinDecoderClient vinDecoderClient;

    @Autowired
    public VinDecoderService(VinDecoderClient vinDecoderClient) {
        this.vinDecoderClient = vinDecoderClient;
    }

    public VinDecoderDto vinDecoder(String vin) {
        return vinDecoderClient.vinToBeDecoded(vin);
    }
}
