package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error Message representing the error happened"
    )
    private String errorMessage;

    @Schema(
            description = "Error Time representing the time when the error happened"
    )
    private LocalDateTime errorTime;


}
