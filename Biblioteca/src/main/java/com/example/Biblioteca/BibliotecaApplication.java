package com.example.Biblioteca;

import com.example.Biblioteca.Banco.iniciarBanco;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

// DUPLA: Mariana Pereira e Flavia Goes

@SpringBootApplication
public class BibliotecaApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		iniciarBanco init = new iniciarBanco();
		init.inicializarBanco();
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}
