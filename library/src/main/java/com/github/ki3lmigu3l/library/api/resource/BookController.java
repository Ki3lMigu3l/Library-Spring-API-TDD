package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private ModelMapper modelMapper;
    private BookService bookService;

    @Autowired
    public BookController (ModelMapper modelMapper, BookService bookService) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook (@RequestBody BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookService.save(book);
        return modelMapper.map(book, BookDTO.class);
    }
}
