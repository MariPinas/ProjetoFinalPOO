package com.example.Biblioteca.Banco;

import com.example.Biblioteca.DAOs.DAOEmprestimo;
import com.example.Biblioteca.DAOs.DAOItemEmprestimo;
import com.example.Biblioteca.DAOs.DAOLivro;

import java.sql.SQLException;

public class iniciarBanco {

        private DAOLivro daoLivro;
        private DAOEmprestimo daoEmprestimo;
        private DAOItemEmprestimo daoItemEmprestimo;

        public iniciarBanco() {
            this.daoLivro = new DAOLivro();
            this.daoEmprestimo = new DAOEmprestimo();
            this.daoItemEmprestimo = new DAOItemEmprestimo();
        }

        public void inicializarBanco() throws SQLException, ClassNotFoundException {
            daoLivro.criarTabelaLivro();
            daoEmprestimo.criarTabelaEmprestimo();
            daoItemEmprestimo.criarTabelaItemEmprestimo();
        }

}
