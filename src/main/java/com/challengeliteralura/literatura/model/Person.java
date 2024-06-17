package com.challengeliteralura.literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "personas")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "birth_year", nullable = true)
    private Integer birth_year;
    @Column(name = "death_year", nullable = true)
    private Integer death_year;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "authors",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Book> books;

    public Person(){}

    public Person(DatoPerson datoPerson){
        this.name = datoPerson.name();
        this.birth_year = datoPerson.birth_year();
        this.death_year = datoPerson.death_year();
    }

    public Long getId() {
        return id;
    }


    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        String bookList = books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(", "));
        return
                "Nombre: " + this.name +"\n"+
                "Año nacimiento: " + this.birth_year +"\n"+
                "Año fallecimiento: " + this.death_year +"\n"+
                "libros: " + bookList+
                "\n\n***************************************************\n";

    }
}
