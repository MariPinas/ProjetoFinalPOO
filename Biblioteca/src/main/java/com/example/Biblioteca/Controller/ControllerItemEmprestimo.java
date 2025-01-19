package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Model.ItemEmprestimo;
import com.example.Biblioteca.DAOs.DAOItemEmprestimo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/itens-emprestimo")
public class ControllerItemEmprestimo {

    private DAOItemEmprestimo daoItemEmprestimo;

    public ControllerItemEmprestimo() {
        this.daoItemEmprestimo = new DAOItemEmprestimo();
    }
    @GetMapping("/emprestimo/{emprestimoId}")
    public ResponseEntity<List<ItemEmprestimo>> getLivrosByEmprestimoId(@PathVariable int emprestimoId) {
        try {
            List<ItemEmprestimo> itens = daoItemEmprestimo.getLivrosByEmprestimoId(emprestimoId);
            return new ResponseEntity<>(itens, HttpStatus.OK);  // 200
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 500
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<ItemEmprestimo>> getAllItens() {
        try {
            List<ItemEmprestimo> itens = daoItemEmprestimo.getAll();
            return new ResponseEntity<>(itens, HttpStatus.OK);  // 200 OK
        } catch (SQLException | ClassNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);  // 500 erro interno
        }
    }
}
