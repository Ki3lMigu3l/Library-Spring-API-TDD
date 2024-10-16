package com.github.ki3lmigu3l.library.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/books";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest () throws Exception {

        BookDTO dto = BookDTO
                .builder()
                .id(1L)
                .author("Robert C. Martin")
                .title("Código Limpo")
                .isbn("8576082675")
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("title").value(dto.title()))
                .andExpect(jsonPath("author").value(dto.author()))
                .andExpect(jsonPath("isbn").value(dto.isbn()));
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando for informado dados insuficiente para criação do livro.")
    public void createInvalidBookTest () {

    }
}
