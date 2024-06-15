package com.challengeliteralura.literatura.principal;

import com.challengeliteralura.literatura.repository.IBookRepository;
import com.challengeliteralura.literatura.model.Book;
import com.challengeliteralura.literatura.model.DatoBook;
import com.challengeliteralura.literatura.model.GutenbergResponse;
import com.challengeliteralura.literatura.service.BookService;
import com.challengeliteralura.literatura.service.ConsumoApi;
import com.challengeliteralura.literatura.service.ConvertirDato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner leer = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "http://gutendex.com/books";
    private final String KEY = System.getenv("KEY_API");

    private ConvertirDato conversor = new ConvertirDato();
    
    private List<DatoBook> listaDeLibros=new ArrayList<>();

    private IBookRepository bookRepository;
    @Autowired
    private BookService bookService;
    public Principal(IBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public void mostrarMenuPrincipal(){
        int opcion = -1;
        System.out.println("\n\n********* Bienvenido a BookMax ********\n\n");
        var menu = """
                    1- Buscar por titulo
                    2 - Mostrar libros registrados
                    0- salir
                """;
        while(opcion != 0){
            System.out.println(menu);
            System.out.println("ingrese una opcion: ");
            opcion = leer.nextInt();
            leer.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarTodosLibrosRegistrados();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo(){
        System.out.println("Ingrese el nombre del libro: ");
        String nameBook = leer.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nameBook.replace(" ", "+").toLowerCase());
        bookService.getByTitle(nameBook, json);
    }

    private void buscarTodosLibrosRegistrados(){

        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("************ Lista de Libros Registrados ************\n");
            books.forEach(book -> {
                System.out.println(book.toString());
            });
        }
    }
}
