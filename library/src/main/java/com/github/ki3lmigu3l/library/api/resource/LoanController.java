package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import com.github.ki3lmigu3l.library.api.dto.LoanDTO;
import com.github.ki3lmigu3l.library.api.dto.LoanFilterDTO;
import com.github.ki3lmigu3l.library.api.dto.ReturnedLoanDto;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.service.BookService;
import com.github.ki3lmigu3l.library.api.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo Empréstimo", description = "Salva um novo empréstimo de livro, associando-o a um cliente.")
    public Long create(@RequestBody LoanDTO loanDTO) {
        Book book = bookService.getByIsbn(loanDTO.getIsbn())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn"));


        Loan loan = Loan.builder()
                .book(book)
                .customer(loanDTO.getCustomer())
                .loanDate(LocalDate.now())
                .build();

        Loan loanSaved = loanService.save(loan);
        return loanSaved.getId();
    }

    @PatchMapping("{id}")
    @Operation(summary = "Retorna um Livro", description = "Atualiza o status de um empréstimo, marcando o livro como retornado.")
    public void returnedBook(@PathVariable Long id, @RequestBody ReturnedLoanDto loanDto) {
        Loan loan = loanService.getById(id).get();
        loan.setReturned(loanDto.getReturned());
        loanService.update(loan);
    }


    @GetMapping
    @Operation(summary = "Lista de Empréstimos", description = "Retorna uma lista de empréstimos, podendo aplicar filtros.")
    public Page<LoanDTO> find(LoanFilterDTO dto, Pageable pageRequest) {
        Page<Loan> result = loanService.find(dto, pageRequest);
        List<LoanDTO> loans = result
                .getContent()
                .stream()
                .map(loan -> {
                    Book book = loan.getBook();
                    BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
                    LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
                    loanDTO.setBookDTO(bookDTO);
                    return loanDTO;
                }).collect(Collectors.toList());
        return new PageImpl<LoanDTO>(loans, pageRequest, result.getTotalElements());
    }


}
