package com.example.Biblioteca.Model;

public class Livro {
    private int id;
    private String titulo;
    private int quantidade;

    public Livro() {

    }

    public Livro(int id, String titulo, int quantidade) {
        this.id = id;
        this.titulo = titulo;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean estaDisponivel() {
        return quantidade > 0;
    }

    public void emprestar() {
        if (estaDisponivel()) {
            quantidade--;
        } else {
            throw new IllegalStateException("Livro indisponível para empréstimo.");
        }
    }

    public void devolver() {
        quantidade++;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", quantidade='" + quantidade + '\'' +
                '}';
    }


}
