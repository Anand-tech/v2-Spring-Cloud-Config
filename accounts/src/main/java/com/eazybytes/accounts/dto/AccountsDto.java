package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account Information"
)
public class AccountsDto {

    @NotEmpty(message = "Account Number cannot be null or Empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Account Number of Eazy Bank account", example = "1234567890"
    )
    private Long accountNumber;

    @Schema(
            description = "Account Type of Eazy bank Account",example = "Savings"
    )
    @NotEmpty(message = "Account Type cannot be null or Empty")
    private String accountType;

    @Schema(
            description = "Eazy Bank Branch Address", example = "123 NewYork"
    )
    @NotEmpty(message = "Branch Address cannot be null or Empty ")
    private String branchAddress;
}
