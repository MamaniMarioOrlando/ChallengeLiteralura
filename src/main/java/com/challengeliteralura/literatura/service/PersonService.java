package com.challengeliteralura.literatura.service;

import com.challengeliteralura.literatura.model.Person;
import com.challengeliteralura.literatura.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Service
public class PersonService {
    @Autowired
    private IPersonRepository authorRepository;
    public void showAuthors() {
        List<Person> authors = authorRepository.findAll();
        authors.stream()
                .sorted(Comparator.comparing(Person::getName))
                .forEach(System.out::println);
    }
    public void showAuthorsAliveInYear(int year) {
        List<Person> authors = authorRepository.authorForYear(year);
        if (authors.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + year);
            System.out.println("\n****************************************\n");
        } else {
            authors.stream()
                    .sorted(Comparator.comparing(Person::getName))
                    .forEach(System.out::println);
        }
    }

}
