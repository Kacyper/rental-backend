package com.kacyper.carrentalbackend.controller;

import com.kacyper.carrentalbackend.dto.api.vinDecoder.VinDecoderDto;
import com.kacyper.carrentalbackend.service.vinDecoderService.VinDecoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/vinDecoder")
public class VinDecoderController {

    private final VinDecoderService vinDecoderService;

    @Autowired
    public VinDecoderController(VinDecoderService vinDecoderService) {
        this.vinDecoderService = vinDecoderService;
    }

    @GetMapping(value = "/{vin}")
    public VinDecoderDto decodingVin(@PathVariable String vin) {
        return vinDecoderService.vinDecoder(vin);
    }
}
