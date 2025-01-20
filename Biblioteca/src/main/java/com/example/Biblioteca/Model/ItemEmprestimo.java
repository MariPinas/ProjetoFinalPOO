package com.example.Biblioteca.Model;

public class ItemEmprestimo {
    private int id;
    private int emprestimoId;
    private int livroId;
    private String nomeLivro;
    private int quantidade;

    public ItemEmprestimo() {

    }

    public ItemEmprestimo(int emprestimoId, int livroId, String nomeLivro, int quantidade) {
        this.emprestimoId = emprestimoId;
        this.livroId = livroId;
        this.nomeLivro=nomeLivro;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEmprestimoId() {
        return this.emprestimoId;
    }

    public void setEmprestimoId(int emprestimoId) {
        this.emprestimoId = emprestimoId;
    }

    public int getLivroId() {
        return this.livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public String getNomeLivro() {
        return this.nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


}