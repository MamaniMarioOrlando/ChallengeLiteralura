package com.challengeliteralura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name ="libros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name= "download_count")
    private Integer download_count;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Person> authors;
    private String languages;

    public Book() {
    }
    public Book(DatoBook datoBook, List<Person> authors){
        this.title = datoBook.titulo();
        this.download_count = datoBook.download_count();
        this.authors = authors;
        this.languages = getFirstLanguage(datoBook);

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer copyright) {
        this.download_count = download_count;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
    public String getFirstLanguage(DatoBook dataBook) {
        return dataBook.languages() != null && !dataBook.languages().isEmpty() ? dataBook.languages().get(0) : null;
    }

    @Override
    public String toString() {
        String authorsNames = authors.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", "));
        return
                "Titulo= " + title + "\n" +
                "Autor/es= " + authorsNames + "\n" +
                "Lenguaje= " + languages + "\n" +
                "Numero de descargas= " + this.download_count + "\n" +
                "\n***************************************************\n";


    }
}
