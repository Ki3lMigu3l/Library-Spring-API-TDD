package com.github.ki3lmigu3l.library.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ki3lmigu3l.library.api.dto.ReturnedLoanDto;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    LoanRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Deve verificar se existe emprestimo não devolvido para um livro.")
    public void existsByBookAndNotReturnedTest () {

        Book book = createNewBook();
        entityManager.persist(book);

        Loan loan = Loan.builder().book(book).customer("Ezequiel").loanDate(LocalDate.now()).build();
        entityManager.persist(loan);

        boolean exists = repository.existsByBookAndNotReturned(book);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve buscar emprestimo pelo isbn do livro ou customer")
    public void findByBookIsbnOrCustumerTest() {
        Book book = createNewBook();
        entityManager.persist(book);

        Loan loan = Loan.builder().book(book).customer("Ezequiel").loanDate(LocalDate.now()).build();
        entityManager.persist(loan);

        Page<Loan> result = repository
                .findByBookIsbnOrCustomer(
                        book.getIsbn(),
                        loan.getCustomer(),
                        PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent()).contains(loan);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve obter emprestimos cuja a data do emprestimo seja menor ou igual a três dias e não retornado")
    public void findByLoanDateLessThanAndNotReturnedTest(){
        Loan loan = createAndPersistLoan(LocalDate.now().minusDays(5));
        List<Loan> result = repository
                .findByLoansDateLassThanAndNotReturned(LocalDate.now().minusDays(4));

        assertThat(result).hasSize(1).contains(loan);

    }

    @Test
    @DisplayName("Deve retornar vazio quando não houver emprestimos atrasados")
    public void notFindByLoanDateLessThanAndNotReturnedTest(){
        Loan loan = createAndPersistLoan(LocalDate.now());

        List<Loan> result = repository
                .findByLoansDateLassThanAndNotReturned(LocalDate.now().minusDays(4));

        assertThat(result).isEmpty();
    }

    private Book createNewBook() {
        return Book.builder()
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("123")
                .build();
    }

    public Loan createAndPersistLoan(LocalDate loanDate) {
        Book book = createNewBook();
        book.setIsbn("123");
        entityManager.persist(book);

        Loan loan = Loan.builder()
                .book(book)
                .customer("Kiel")
                .loanDate(loanDate)
                .build();

        entityManager.persist(loan);

        return loan;
    }
}
