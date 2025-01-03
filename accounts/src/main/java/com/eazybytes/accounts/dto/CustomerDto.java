package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name ="Customer",
        description = "Schema to hold Customer and Account Information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer" ,example = "Eazy Bytes"
    )
    @NotEmpty(message = "Name  cannot be null or Empty")
    @Size(min =5 , max =30 ,message = "The Length of the Customer Should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email Address of the customer" ,example = "tutor@eazybytes.com"
    )
    @NotEmpty(message = "Email Address cannot be null or Empty")
    @Email(message = "Email Address should be valid value")
    private String email;

    @Schema(
            description = "Mobile NUmber of the customer", example = "9789668317"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
