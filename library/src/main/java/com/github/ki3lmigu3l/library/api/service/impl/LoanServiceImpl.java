package com.github.ki3lmigu3l.library.api.service.impl;

import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.repository.LoanRepository;
import com.github.ki3lmigu3l.library.api.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoanServiceImpl implements LoanService {


    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        return repository.save(loan);
    }
}