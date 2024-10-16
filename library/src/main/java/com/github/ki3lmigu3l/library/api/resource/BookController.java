package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook (@RequestBody BookDTO bookDTO) {
        Book book = Book
                .builder()
                .id(bookDTO.id())
                .title(bookDTO.title())
                .author(bookDTO.author())
                .isbn(bookDTO.isbn())
                .build();

        book = bookService.save(book);

        return BookDTO
                .builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }
}
