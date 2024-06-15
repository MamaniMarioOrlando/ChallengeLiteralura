package com.challengeliteralura.literatura;

import com.challengeliteralura.literatura.principal.Principal;
import com.challengeliteralura.literatura.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {
	@Autowired
    private Principal menuPrincipal;
	private IBookRepository bookRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
        menuPrincipal.mostrarMenuPrincipal();
	}
}
