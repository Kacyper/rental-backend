package com.kacyper.carrentalbackend.service;

import com.kacyper.carrentalbackend.api.vinDecoder.client.VinDecoderClient;
import com.kacyper.carrentalbackend.dto.api.vinDecoder.VinDecoderBodyDto;
import com.kacyper.carrentalbackend.dto.api.vinDecoder.VinDecoderDto;
import com.kacyper.carrentalbackend.service.vinDecoderService.VinDecoderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VinDecoderServiceTestSuite {

    @InjectMocks
    private VinDecoderService vinDecoderService;

    @Mock
    private VinDecoderClient vinDecoderClient;

    @DisplayName("Decoding VIN")
    @Test
    public void vinDecoderTest() {

        //Given
        VinDecoderBodyDto vinDecoderBodyDto = VinDecoderBodyDto.builder()
                .carCompany("NISSAN NORTH AMERICA, INC.")
                .model("Frontier")
                .fuelType("Gasoline")
                .plantCity("CANTON")
                .productionYear("2019")
                .build();
        List<VinDecoderBodyDto> vinDecoderBodyDtoList = Collections.singletonList(vinDecoderBodyDto);
        VinDecoderDto vinDecoderDto = new VinDecoderDto(vinDecoderBodyDtoList);

        when(vinDecoderClient.vinToBeDecoded("1N6AD0ER7KN790030")).thenReturn(vinDecoderDto);

        //When
        VinDecoderDto vinDecoderDto1 = vinDecoderService.vinDecoder("1N6AD0ER7KN790030");

        //Then
        Assertions.assertEquals("Frontier", vinDecoderDto1.getVinDecoderBodyDtoList().get(0).getModel());
    }
}