package com.example.Biblioteca.Controller;

import com.example.Biblioteca.DAOs.DAOEmprestimo;
import com.example.Biblioteca.Model.Emprestimo;
import com.example.Biblioteca.Model.ItemEmprestimo;
import com.example.Biblioteca.Model.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class ControllerEmprestimo {

    private DAOEmprestimo daoEmprestimo;
    private Livro livro;

    public ControllerEmprestimo() {
        this.daoEmprestimo = new DAOEmprestimo();
    }

    @PostMapping
    public ResponseEntity<String> criarEmprestimo(@RequestBody Emprestimo emprestimo) {
        //lista vaiza ?
        if (emprestimo.getItens() == null || emprestimo.getItens().isEmpty()) {
            return new ResponseEntity<>("Erro: O emprestimo deve ter ao menos um item.", HttpStatus.BAD_REQUEST); // 400
        }

        try {
            daoEmprestimo.create(emprestimo, emprestimo.getItens());
            return new ResponseEntity<>("Emprestimo criado com sucesso!", HttpStatus.CREATED);  // 201
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao criar emprestimo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEmprestimo(@PathVariable int id, @RequestBody Emprestimo emprestimo) {
        try {
            emprestimo.setId(id);
            daoEmprestimo.update(emprestimo);
            return new ResponseEntity<>("Emprestimo atualizado com sucesso!", HttpStatus.OK);  //200
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao atualizar emprestimo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEmprestimo(@PathVariable int id) {
        try {
            daoEmprestimo.delete(id);
            return new ResponseEntity<>("Emprestimo deletado com sucesso!", HttpStatus.NO_CONTENT);  //204
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>("Erro ao deletar emprestimo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        try {
            List<Emprestimo> emprestimos = daoEmprestimo.getAllEmprestimos();
            return new ResponseEntity<>(emprestimos, HttpStatus.OK);  //200
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoPorId(@PathVariable int id) {
        try {
            Emprestimo emprestimo = daoEmprestimo.getEmprestimoById(id);
            if (emprestimo != null) {
                return new ResponseEntity<>(emprestimo, HttpStatus.OK);  //200
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  //404
            }
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }


}
