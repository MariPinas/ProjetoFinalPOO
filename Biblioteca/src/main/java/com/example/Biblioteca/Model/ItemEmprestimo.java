package com.example.Biblioteca.Model;

public class ItemEmprestimo {
        private int id;
        private int emprestimoId;
        private int livroId;
        private Livro livro;
        private String dataEmprestimo;
        private String dataDevolucao;
        private int quantidade;

    public ItemEmprestimo() {

    }

    public ItemEmprestimo(int emprestimoId, int livroId, Livro livro, String dataEmprestimo, String dataDevolucao, int quantidade) {
        this.emprestimoId = emprestimoId;
        this.livroId = livroId;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(int emprestimoId) {
        this.emprestimoId = emprestimoId;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public Livro getLivro() {
            return livro;
        }

        public void setLivro(Livro livro) {
            this.livro = livro;
        }

        public String getDataEmprestimo() {
            return dataEmprestimo;
        }

        public void setDataEmprestimo(String dataEmprestimo) {
            this.dataEmprestimo = dataEmprestimo;
        }

        public String getDataDevolucao() {
            return dataDevolucao;
        }

        public void setDataDevolucao(String dataDevolucao) {
            this.dataDevolucao = dataDevolucao;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
        public String toString() {
            return "ItemEmprestimo{" +
                    "livro=" + livro +
                    ", dataEmprestimo='" + dataEmprestimo + '\'' +
                    ", dataDevolucao='" + dataDevolucao + '\'' +
                    '}';
        }
    }


