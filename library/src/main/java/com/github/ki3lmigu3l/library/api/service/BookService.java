package com.github.ki3lmigu3l.library.api.service;

import com.github.ki3lmigu3l.library.api.model.Book;

import java.util.Optional;

public interface BookService {
    Book save (Book any);

    Optional<Book> getById(Long id);
}
