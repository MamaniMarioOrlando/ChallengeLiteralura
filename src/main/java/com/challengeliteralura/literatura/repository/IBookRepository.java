package com.challengeliteralura.literatura.repository;

import com.challengeliteralura.literatura.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findFirstByTitleContainingIgnoreCase(String title);
    @EntityGraph(attributePaths = "authors")
    //todos los libros pero con el entityGraph para que me traiga los autores
    @Query("SELECT b FROM Book b")
    List<Book> findAllWithAuthors();

    Book findByTitleIgnoreCase(String title);

    List<Book> findByLanguagesContains(String language);
}
