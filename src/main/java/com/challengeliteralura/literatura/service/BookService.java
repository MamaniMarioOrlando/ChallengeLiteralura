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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
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

    public void showBookForLanguage(){
        List<String> validLanguages = Arrays.asList("es","en","fr","pt");
        var menu = """
                    es - español
                    en - ingles
                    fr - francia
                    pt - portugal
                """;
        System.out.println(menu);
        System.out.println("Ingrese una opcion: ");
        Scanner leer = new Scanner(System.in);
        String datoIngresado = leer.nextLine().trim().toLowerCase();
        if(!validLanguages.contains(datoIngresado)){
            System.out.println("Opción de idioma invalida. Por favor, elija una de las opciones validas.");
            return;
        }
        try{
            List<Book> books = bookRepository.findByLanguagesContains(datoIngresado);
            if(books.isEmpty()){
                System.out.print("\n\n***************************************************\n\n");
                System.out.print("No se encontraron libros en el idioma: "+datoIngresado);
                System.out.print("\n\n***************************************************\n\n");
            }else{
                System.out.println("***************** Libros segun el idioma ******************");
                books.forEach(System.out::println);
            }
        }catch (Exception e){
            System.out.print("\n\n***************************************************\n\n");
            System.err.print("Ocurrió un error al buscar los libros: " + e.getMessage());
            System.out.print("\n\n***************************************************\n\n");
        }
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
