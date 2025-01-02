package com.eazybytes.loans.mapper;


import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import lombok.AllArgsConstructor;
import lombok.Data;


public class LoansMapper {

    public static LoansDto mapToLoansDto (Loans loans, LoansDto loansDto)
    {
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutStandingAmount(loans.getAmountPaid());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setLoanNumber(loans.getLoanNumber());

        return loansDto;
    }

    public static Loans mapToLoans (LoansDto loansDto , Loans loans)
    {
        loans.setOutstandingAmount(loansDto.getOutStandingAmount());
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());

        return loans;
    }
}

