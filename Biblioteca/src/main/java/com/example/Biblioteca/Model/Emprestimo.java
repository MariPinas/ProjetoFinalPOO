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

    public void adicionarItem(ItemEmprestimo item) {
        Livro livro = item.getLivro();
        if (livro.estaDisponivel()) {
            itens.add(item);
            livro.emprestar();
        } else {
            System.out.println("Livro " + livro.getTitulo() + " nao esta disponivel");
        }
    }

    public void devolverLivro(Livro livro) {
        for (ItemEmprestimo item : itens) {
            if (item.getLivro().equals(livro)) {
                livro.devolver();
                itens.remove(item);
                break;
            }
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void setItens(List<ItemEmprestimo> itens) {
        this.itens = itens;
    }

    public List<ItemEmprestimo> getItens() {
        return itens;
    }

    public int getId() {
        return id;
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
