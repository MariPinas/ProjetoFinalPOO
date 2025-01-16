package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Model.Livro;
import com.example.Biblioteca.DAOs.DAOLivro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class ControllerLivro {

    private DAOLivro daoLivro;

    public ControllerLivro() {
        this.daoLivro = new DAOLivro();
    }

    @PostMapping
    public ResponseEntity<String> criarLivro(@RequestBody Livro livro) {
        try { // try criar se nao conseguir catch o erro
            daoLivro.create(livro);
            return new ResponseEntity<>("Livro criado com sucesso!", HttpStatus.CREATED);  //201
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao criar livro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarLivro(@PathVariable int id, @RequestBody Livro livro) {
        try {
            livro.setId(id);
            daoLivro.update(livro);
            return new ResponseEntity<>("Livro atualizado com sucesso!", HttpStatus.OK);  //200
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao atualizar livro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable int id) {
        try {
            Livro livro = new Livro();
            livro.setId(id);
            daoLivro.delete(livro);
            return new ResponseEntity<>("Livro deletado com sucesso!", HttpStatus.NO_CONTENT);  //204
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao deletar livro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        try {
            List<Livro> livros = daoLivro.getAllLivros();
            return new ResponseEntity<>(livros, HttpStatus.OK);  //200
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable int id) {
        try {
            Livro livro = daoLivro.getById(id);
            if (livro != null) {
                return new ResponseEntity<>(livro, HttpStatus.OK);  //200
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  //404
            }
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }
}
