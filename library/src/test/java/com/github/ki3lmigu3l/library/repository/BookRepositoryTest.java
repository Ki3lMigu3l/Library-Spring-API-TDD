package com.github.ki3lmigu3l.library.repository;

import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
    public void returnTrueWhenIsbnExists() {
        String isbn = "8576082675";
        testEntityManager.persist(Book
                .builder()
                .isbn("8576082675")
                .build());

        boolean existsIsbn = bookRepository.existsByIsbn(isbn);
        assertThat(existsIsbn).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro na base com o isbn informado")
    public void returnTrueWhenIsbnDoesntExists() {
        String isbn = "8576082675";
        boolean existsIsbn = bookRepository.existsByIsbn(isbn);
        assertThat(existsIsbn).isFalse();
    }
}
