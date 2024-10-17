package com.github.ki3lmigu3l.library.service;

import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.repository.BookRepository;
import com.github.ki3lmigu3l.library.api.service.BookService;
import com.github.ki3lmigu3l.library.api.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUpService () {
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar o livro")
    public void saveBookTest() {
        Book book = Book.builder()
                .id(1L)
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("8576082675")
                .build();

        Mockito.when(repository.save(book)).thenReturn(Book.builder()
                .id(1L)
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("8576082675")
                .build());

        Book savedBook = service.save(book);
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("8576082675");
        assertThat(savedBook.getTitle()).isEqualTo("Código Limpo");
        assertThat(savedBook.getAuthor()).isEqualTo("Robert C. Martin");
    }
}


