package com.github.ki3lmigu3l.library.api.resource;

import com.github.ki3lmigu3l.library.api.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook (@RequestBody BookDTO bookDTO) {
        return bookDTO;
    }
}
