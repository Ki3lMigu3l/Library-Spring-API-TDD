package com.github.ki3lmigu3l.library.api.repository;

import com.github.ki3lmigu3l.library.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}
