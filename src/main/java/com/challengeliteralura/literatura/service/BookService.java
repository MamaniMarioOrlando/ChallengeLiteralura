package com.challengeliteralura.literatura.service;

import com.challengeliteralura.literatura.model.Book;
import com.challengeliteralura.literatura.model.DatoBook;
import com.challengeliteralura.literatura.model.GutenbergResponse;
import com.challengeliteralura.literatura.model.Person;
import com.challengeliteralura.literatura.repository.IBookRepository;
import com.challengeliteralura.literatura.repository.IPersonRepository;
import org.hibernate.boot.query.HbmResultSetMappingDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private ConvertirDato conversor;
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private IPersonRepository personRepository;

    public void getByTitle(String title,String json){
        try{
            DatoBook datoBook = getResult(title, json);

            if(datoBook == null) {
                System.out.println("\n* No se encuentra el Libro de nombre: " + title );
                return;
            }
            Optional<Book> isPresentBook = bookRepository.findFirstByTitleContainingIgnoreCase(title);
            if(isPresentBook.isPresent()){
                System.out.println("\n**************** DATOS DEL LIBRO ****************\n\n" + isPresentBook.get().toString());
            }else{
                List<Person> authorList = getListPerson(datoBook);
                Book book = new Book(datoBook, authorList);
                bookRepository.save(book);
                System.out.println("\n**************** DATOS DEL LIBRO ****************\n\n" + book.toString());
            }

        }catch(Exception e){
            System.err.println("Ocurrio un problema en getResult: " + e.getMessage());
        }

    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    private DatoBook getResult(String title,String json){
        try{
            GutenbergResponse result = conversor.obtenerDatos(json, GutenbergResponse.class);
            Optional<DatoBook> getBook = result.getResults().stream()
                    .filter(b -> b.titulo().toLowerCase().contains(title.toLowerCase()))
                    .peek(System.out::println)
                    .findFirst();

            return getBook.orElse(null);
        }catch(Exception e){
            System.err.println("Ocurrio un problema en: " + e.getMessage());
            return null;
        }
    }
    private List<Person> getListPerson(DatoBook datoBook){
        List<Person> authorList = datoBook.authors().stream()
                .map(dataAuthor -> {
                    return personRepository.findByName(dataAuthor.name())
                            .orElseGet(() -> personRepository.save(new Person(dataAuthor)));
                })
                .collect(Collectors.toList());
        return authorList;
    }
}
