package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import com.github.ki3lmigu3l.library.api.dto.LoanDTO;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.service.BookService;
import com.github.ki3lmigu3l.library.api.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo Livro", description = "Salva um livro na base de dados.")
    public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO) {
        log.info(" creating a book for isbn: {} ", bookDTO.getIsbn());
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookService.save(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @GetMapping("{id}")
    @Operation(summary = "Obtém detalhes de um Livro", description = "Recupera um livro da base de dados pelo ID.")
    public BookDTO get(@PathVariable Long id) {
        log.info(" obtaining detais for book id: {}", id);
        return bookService
                .getById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta um Livro", description = "Remove um livro da base de dados pelo ID.")
    public void delete(@PathVariable Long id) {
        log.info(" deleting book of id: {}", id);
        Book book = bookService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookService.delete(book);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um Livro", description = "Atualiza as informações de um livro existente na base de dados pelo ID.")
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        log.info(" updating book of id: {}", id);
        Book book = bookService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(book.getAuthor());
        bookService.update(book);
        return modelMapper.map(book, BookDTO.class);
    }

    @GetMapping
    @Operation(summary = "Lista de Livros", description = "Retorna uma lista de livros, podendo aplicar filtros.")
    public Page<BookDTO> find(BookDTO bookDTO, Pageable pageRequest) {
        Book filter = modelMapper.map(bookDTO, Book.class);
        Page<Book> result = bookService.find(filter, pageRequest);
        List<BookDTO> list = result.getContent()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<BookDTO>(list, pageRequest, result.getTotalElements());
    }

    @GetMapping("{id}/loans")
    @Operation(summary = "Lista de Empréstimos de um Livro", description = "Recupera todos os empréstimos associados a um livro pelo ID.")
    public Page<LoanDTO> loansByBook(@PathVariable Long id, Pageable pageable) {
        Book book = bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Page<Loan> result = loanService.getLoansByBook(book, pageable);
        List<LoanDTO> list = result.getContent()
                .stream()
                .map(loan -> {
                    Book loanBook = loan.getBook();
                    BookDTO bookDTO = modelMapper.map(loanBook, BookDTO.class);
                    LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
                    loanDTO.setBookDTO(bookDTO);
                    return loanDTO;
                }).collect(Collectors.toList());

        return new PageImpl<LoanDTO>(list, pageable, result.getTotalElements());
    }
}
