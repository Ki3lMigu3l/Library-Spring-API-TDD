package com.github.ki3lmigu3l.library.api.service;

import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.resource.BookController;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Loan loan);
}
