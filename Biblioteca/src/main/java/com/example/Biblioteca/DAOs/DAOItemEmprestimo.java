package com.example.Biblioteca.DAOs;

import com.example.Biblioteca.Conexao;
import com.example.Biblioteca.Model.ItemEmprestimo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOItemEmprestimo {
    public DAOItemEmprestimo() {
    }

    public void create(ItemEmprestimo itemEmprestimo) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO ItemEmprestimo (emprestimoId, livroId, quantidade) VALUES (?, ?, ?)";

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, itemEmprestimo.getEmprestimoId());
            ps.setInt(2, itemEmprestimo.getLivroId());
            ps.setInt(3, itemEmprestimo.getQuantidade());
            ps.execute();
            System.out.println("Item inserido na tabela ItemEmprestimo: Livro ID " + itemEmprestimo.getLivroId() + ", Quantidade " + itemEmprestimo.getQuantidade());
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao inserir item de emprestimo: " + e.getMessage());
            throw e;
        }
    }

    public List<ItemEmprestimo> getLivrosByEmprestimoId(int emprestimoId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ItemEmprestimo WHERE emprestimoId = ?";
        List<ItemEmprestimo> lista = new ArrayList<>();

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, emprestimoId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemEmprestimo item = new ItemEmprestimo();
                    item.setId(rs.getInt("id"));
                    item.setEmprestimoId(rs.getInt("emprestimoId"));
                    item.setLivroId(rs.getInt("livroId"));
                    item.setQuantidade(rs.getInt("quantidade"));

                    String sqlLivro = "SELECT titulo FROM Livro WHERE id = ?";
                    try (PreparedStatement psLivro = conn.getConexao().prepareStatement(sqlLivro)) {
                        psLivro.setInt(1, item.getLivroId());
                        try (ResultSet rsLivro = psLivro.executeQuery()) {
                            if (rsLivro.next()) {
                                item.setNomeLivro(rsLivro.getString("titulo"));
                            }
                        }
                    }
                    lista.add(item);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar livros de emprestimo: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    public List<ItemEmprestimo> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ItemEmprestimo";
        List<ItemEmprestimo> lista = new ArrayList<>();

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ItemEmprestimo item = new ItemEmprestimo();
                item.setId(rs.getInt("id"));
                item.setEmprestimoId(rs.getInt("emprestimoId"));
                item.setLivroId(rs.getInt("livroId"));
                item.setQuantidade(rs.getInt("quantidade"));
                String sqlLivro = "SELECT titulo FROM Livro WHERE id = ?";
                try (PreparedStatement psLivro = conn.getConexao().prepareStatement(sqlLivro)) {
                    psLivro.setInt(1, item.getLivroId());
                    try (ResultSet rsLivro = psLivro.executeQuery()) {
                        if (rsLivro.next()) {
                            item.setNomeLivro(rsLivro.getString("titulo"));
                        }
                    }
                }
                lista.add(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar todos os itens de empréstimo: " + e.getMessage());
            throw e;
        }

        return lista;
    }
}



