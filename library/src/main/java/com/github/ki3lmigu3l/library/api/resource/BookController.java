package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import com.github.ki3lmigu3l.library.api.exception.ApiErrors;
import com.github.ki3lmigu3l.library.api.exception.BusinessException;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.service.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException (MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException (BusinessException exception) {
        return new ApiErrors(exception);
    }
}
