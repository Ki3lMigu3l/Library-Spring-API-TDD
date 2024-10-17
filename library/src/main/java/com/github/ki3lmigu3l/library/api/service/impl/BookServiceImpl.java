package com.github.ki3lmigu3l.library.api.service.impl;

import com.github.ki3lmigu3l.library.api.exception.BusinessException;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.repository.BookRepository;
import com.github.ki3lmigu3l.library.api.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw  new BusinessException("ISBN já cadastrado");
        }

        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book update(Book book) {
        return null;
    }
}
