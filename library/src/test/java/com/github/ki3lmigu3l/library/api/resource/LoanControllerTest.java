package com.github.ki3lmigu3l.library.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ki3lmigu3l.library.api.dto.LoanDTO;
import com.github.ki3lmigu3l.library.api.exception.BusinessException;
import com.github.ki3lmigu3l.library.api.model.Book;
import com.github.ki3lmigu3l.library.api.model.Loan;
import com.github.ki3lmigu3l.library.api.service.BookService;
import com.github.ki3lmigu3l.library.api.service.LoanService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = LoanController.class)
@AutoConfigureMockMvc
public class LoanControllerTest {

    static final String LOAN_API = "/loans";

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @MockBean
    LoanService loanService;


    @Test
    @DisplayName("Deve realizar um emprestimo")
    public void createLoanTest() throws Exception {

        LoanDTO loanDto = LoanDTO.builder().isbn("123").customer("Kiel").build();
        String json = new ObjectMapper().writeValueAsString(loanDto);

        Book book = Book.builder().isbn("123").build();
        BDDMockito.given(bookService.getByIsbn("123"))
                .willReturn(Optional.of(book));

        Loan loan = Loan.builder().id(1l).customer("Kiel").book(book).loanDate(LocalDate.now()).build();
        BDDMockito.given(loanService.save(Mockito.any(Loan.class))).willReturn(loan);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LOAN_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }


    @Test
    @DisplayName("Deve retornar um erro ao tentar fazer um emprestimo de um livro inexistente")
    public void invalidIsbnCreateLoanTest () throws Exception {

        LoanDTO loanDto = LoanDTO.builder().isbn("123").customer("Kiel").build();
        String json = new ObjectMapper().writeValueAsString(loanDto);


        BDDMockito.given(bookService.getByIsbn("123"))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LOAN_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Book not found for passed isbn"));
    }


    @Test
    @DisplayName("Deve retornar um erro ao tentar fazer um emprestimo de um livro já emprestado")
    public void loanedBookErrorOnCreateLoanTest () throws Exception {

        LoanDTO loanDto = LoanDTO.builder().isbn("123").customer("Kiel").build();
        String json = new ObjectMapper().writeValueAsString(loanDto);

        Book book = Book.builder().isbn("123").build();
        BDDMockito.given(bookService.getByIsbn("123")).willReturn(Optional.of(book));

        BDDMockito.given(loanService.save(Mockito.any(Loan.class))).willThrow(new BusinessException("Book already loaned"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(LOAN_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Book already loaned"));
    }
}
