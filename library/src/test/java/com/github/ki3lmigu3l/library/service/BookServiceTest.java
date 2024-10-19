package com.github.ki3lmigu3l.library.service;

import com.github.ki3lmigu3l.library.api.exception.BusinessException;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.repository.BookRepository;
import com.github.ki3lmigu3l.library.api.service.BookService;
import com.github.ki3lmigu3l.library.api.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    @MockBean
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

        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(book)).thenReturn(createValidBook());

        Book savedBook = service.save(book);
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("8576082675");
        assertThat(savedBook.getTitle()).isEqualTo("Código Limpo");
        assertThat(savedBook.getAuthor()).isEqualTo("Robert C. Martin");
    }

    @Test
    @DisplayName("Deve lança erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shuldNotSaveABookWithDuplicatedISBN() {

        Book book = createValidBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);
        Throwable exception = Assertions.catchThrowable(() -> service.save(book));

        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("ISBN já cadastrado");

        Mockito.verify(repository,Mockito.never()).save(book);

    }

    @Test
    @DisplayName("Deve obter um livro por ID")
    public void getByIdTest () {
        Long id = 1l;
        Book book = createValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = service.getById(id);

        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um livro por ID inexistente")
    public void getByIdBookInexistentTest () {
        Long id = 1l;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Optional<Book> foundBook = service.getById(id);
        assertThat(foundBook.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deleteBookTest () {
        Book book = Book.builder().id(1l).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(book));
        Mockito.verify(repository, Mockito.times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve ocorrer um erro ao tentar deletar um livro inexistente")
    public void deleteInvalidBookTest () {
        Book book = new Book();
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(book));
        Mockito.verify(repository, Mockito.never()).delete(book);
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    public void updateBookTest() {
        Long id = 1l;

        Book updatingBook = Book.builder().id(id).build();

        Book updatedBook = createValidBook();
        updatedBook.setId(id);
        Mockito.when(repository.save(updatingBook)).thenReturn(updatedBook);

        Book book = service.update(updatingBook);

        assertThat(book.getId()).isEqualTo(updatedBook.getId());
        assertThat(book.getTitle()).isEqualTo(updatedBook.getTitle());
        assertThat(book.getAuthor()).isEqualTo(updatedBook.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(updatedBook.getIsbn());

    }

    @Test
    @DisplayName("Deve ocorrer um erro ao tentar atualizar um livro inexistente")
    public void updateInvalidBookTest () {
        Book book = new Book();
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(book));
        Mockito.verify(repository, Mockito.never()).save(book);
    }

    @Test
    @DisplayName("Deve filtrar livros pelas propriedades")
    public void findBookTest () {

        Book book = createValidBook();

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Book> list = Arrays.asList(book);
        Page<Book> page = new PageImpl<Book>(list, pageRequest, 1);

        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<Book> result = service.find(book, pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(list);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);

    }

    private Book createValidBook () {
        return Book.builder()
                .id(1L)
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("8576082675")
                .build();
    }
}


