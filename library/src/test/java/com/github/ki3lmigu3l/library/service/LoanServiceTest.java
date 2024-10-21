package com.github.ki3lmigu3l.library.service;

import com.github.ki3lmigu3l.library.api.exception.BusinessException;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.repository.LoanRepository;
import com.github.ki3lmigu3l.library.api.service.LoanService;
import com.github.ki3lmigu3l.library.api.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    LoanService service;
    @MockBean
    LoanRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new LoanServiceImpl(repository);
    }


    @Test
    @DisplayName("Deve salvar um emprestimo")
    public void saveLoanTest() {
        String customer = "Ezequiel";

        Book book = Book.builder().id(1l).build();
        Loan savingLoan = Loan.builder()
                .loanDate(LocalDate.now())
                .customer(customer)
                .book(book)
                .build();

        Loan savedLoan = Loan.builder()
                .id(1l)
                .loanDate(LocalDate.now())
                .customer(customer)
                .book(book)
                .build();

        Mockito.when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan loan = service.save(savingLoan);
        assertThat(loan.getId()).isEqualTo(savedLoan.getId());
        assertThat(loan.getBook().getId()).isEqualTo(savedLoan.getBook().getId());
        assertThat(loan.getCustomer()).isEqualTo(savedLoan.getCustomer());
        assertThat(loan.getLoanDate()).isEqualTo(savedLoan.getLoanDate());
    }

    @Test
    @DisplayName("Deve lancar erro de negocio ao salvar um emprestimo com o livro já emprestado")
    public void lonedBookSaveTest () {
        String customer = "Ezequiel";

        Book book = Book.builder().id(1l).build();
        Loan savingLoan = Loan.builder()
                .loanDate(LocalDate.now())
                .customer(customer)
                .book(book)
                .build();

        Mockito.when(repository.existsByBookAndNotReturned(book)).thenReturn(true);
        Throwable exception = catchThrowable(() -> service.save(savingLoan));

        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Book already loaned");

        Mockito.verify(repository, Mockito.never()).save(savingLoan);
    }

    @Test
    @DisplayName("Deve obter as informações de um emprestimo pelo ID")
    public void getLoanDetailsTest () {
        Long id = 1l;
        Loan loan = createLoan();
        loan.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(loan));
        Optional<Loan> result = service.getById(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getCustomer()).isEqualTo(loan.getCustomer());
        assertThat(result.get().getBook()).isEqualTo(loan.getBook());
        assertThat(result.get().getLoanDate()).isEqualTo(loan.getLoanDate());

        Mockito.verify(repository).findById(id);
    }

    public Loan createLoan () {
        Book book = Book.builder().id(1l).build();
        String customer = "Ezequiel";

        return Loan.builder()
                .book(book)
                .customer(customer)
                .loanDate(LocalDate.now())
                .build();
    }
}
