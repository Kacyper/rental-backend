package com.kacyper.carrentalbackend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Mail {

    private final String mailTo;
    private String toCC;
    private final String subject;
    private final String message;

}