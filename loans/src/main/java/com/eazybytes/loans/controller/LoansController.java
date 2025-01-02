package com.eazybytes.loans.controller;


import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansContactInfoDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(path= "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoansController {

    private final ILoansService iLoansService ;

    public LoansController(ILoansService iLoansService)
    {
        this.iLoansService=iLoansService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;





    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber)
    {
        iLoansService.createLoan(mobileNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201,LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public  ResponseEntity<LoansDto> fetchLoanDetail(@RequestParam String mobileNumber){

        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @PutMapping("/update")
    public  ResponseEntity<ResponseDto> updateLoanDetails(@RequestBody LoansDto loansDto){
        boolean isUpdated = iLoansService.updateLoan(loansDto);

        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }
        else
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_UPDATE));
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam String mobileNumber)
    {
        boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
        if(isDeleted)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));

        }
        else
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    public  ResponseEntity<String> getBuildInfo(){

        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion()
    {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo()
    {
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
    }

}
