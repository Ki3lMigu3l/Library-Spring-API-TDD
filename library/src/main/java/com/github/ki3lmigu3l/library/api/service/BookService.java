package com.github.ki3lmigu3l.library.api.service;

import com.github.ki3lmigu3l.library.api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    Book save (Book any);

    Optional<Book> getById(Long id);
    Optional<Book> getByIsbn(String isbn);

    void delete(Book book);

    Book update(Book book);

    Page<Book> find(Book filter, Pageable pageRequest);
}
