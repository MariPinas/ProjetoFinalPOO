package com.example.Biblioteca.Model;

import java.util.ArrayList;
import java.util.List;

public class Emprestimo {
    private int id;
    private String usuario;
    private String dataEmprestimo;
    private List<ItemEmprestimo> itens;

    public Emprestimo() {
    }


    public Emprestimo(String usuario, String dataEmprestimo) {
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.itens = new ArrayList<>();
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDataEmprestimo() {
        return this.dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setItens(List<ItemEmprestimo> itens) {
        this.itens = itens;
    }

    public List<ItemEmprestimo> getItens() {
        return this.itens;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "usuario='" + usuario + '\'' +
                ", dataEmprestimo='" + dataEmprestimo + '\'' +
                ", itens=" + itens +
                '}';
    }
}
