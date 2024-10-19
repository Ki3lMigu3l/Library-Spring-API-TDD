package com.github.ki3lmigu3l.library.repository;

import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.repository.BookRepository;
import com.github.ki3lmigu3l.library.api.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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
        Book book = createNewBook(isbn);
        testEntityManager.persist(book);
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

    @Test
    @DisplayName("Deve obter um livro por ID")
    public void findByIdTest() {
        Book book = createValidBook();
        testEntityManager.persist(book);

        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertThat(foundBook.isPresent()).isTrue();
    }

    private Book createValidBook () {
        return Book.builder()
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("8576082675")
                .build();
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        Book book = createNewBook("123");
        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest1() {

        Book book = createNewBook("123");
        testEntityManager.persist(book);

        Book foundBook = testEntityManager.find(Book.class, book.getId());
        bookRepository.delete(foundBook);

        Book deletedBook = testEntityManager.find(Book.class, book.getId());
        assertThat(deletedBook).isNull();
    }



    private Book createNewBook(String isbn) {
        return Book.builder()
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn(isbn)
                .build();
    }
}
