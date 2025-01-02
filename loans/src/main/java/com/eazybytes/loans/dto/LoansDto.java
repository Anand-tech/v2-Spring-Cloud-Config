package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoansDto {

    private String mobileNumber;


    private String loanNumber;

    private  String loanType;

    private int totalLoan;

    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(

            description = "Total outstanding amount against a loan" , example = "90000"
    )
    private int outStandingAmount;
}
