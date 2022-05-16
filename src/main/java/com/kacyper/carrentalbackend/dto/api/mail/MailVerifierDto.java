package com.kacyper.carrentalbackend.dto.api.mail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailVerifierDto {

    @JsonProperty("smtpCheck")
    boolean smtpCheck = true;
}