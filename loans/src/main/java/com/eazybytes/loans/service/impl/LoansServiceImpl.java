package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoansAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository ;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);

        if(optionalLoans.isPresent()){

            throw new LoansAlreadyExistsException("Loan Already exist with the given mobile Number:"+ mobileNumber);

        }
        loansRepository.save(createNewLoan(mobileNumber));

    }


    private  Loans createNewLoan(String mobileNUmber)
    {
        Loans newLoans = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoans.setLoanNumber(Long.toString(randomLoanNumber));
        newLoans.setMobileNumber(mobileNUmber);
        newLoans.setLoanType(LoansConstants.HOME_LOAN);
        newLoans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoans.setAmountPaid(0);
        newLoans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoans;

    }
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );

        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","LoanNumber",loansDto.getLoanNumber())
        );
        LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Loan","mobileNumber",mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());


        return true;
    }
}
