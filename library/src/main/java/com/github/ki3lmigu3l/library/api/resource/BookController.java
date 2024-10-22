package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.service.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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
    public BookDTO createBook (@RequestBody @Valid BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookService.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @GetMapping("{id}")
    public BookDTO get(@PathVariable Long id) {
        return bookService
                .getById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Book book = bookService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookService.delete(book);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO update (@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book book = bookService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(book.getAuthor());
        bookService.update(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @GetMapping
    public Page<BookDTO> find (BookDTO bookDTO, Pageable pageRequest) {
        Book filter = modelMapper.map(bookDTO, Book.class);
        Page<Book> result = bookService.find(filter, pageRequest);
        List<BookDTO> list = result.getContent()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<BookDTO>(list, pageRequest, result.getTotalElements());
    }

}
