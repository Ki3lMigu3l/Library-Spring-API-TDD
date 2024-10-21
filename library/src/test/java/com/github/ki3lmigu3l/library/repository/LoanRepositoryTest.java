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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

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

    private Book createNewBook() {
        return Book.builder()
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("123")
                .build();
    }
}
