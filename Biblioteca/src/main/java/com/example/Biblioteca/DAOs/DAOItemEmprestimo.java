package com.example.Biblioteca.DAOs;

import com.example.Biblioteca.Banco.Conexao;
import com.example.Biblioteca.Model.ItemEmprestimo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOItemEmprestimo {
    public DAOItemEmprestimo() {
    }

    public void criarTabelaItemEmprestimo() throws SQLException, ClassNotFoundException {
        String sql = "CREATE TABLE IF NOT EXISTS ItemEmprestimo ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "emprestimoId INT NOT NULL, "
                + "livroId INT NOT NULL, "
                + "quantidade INT NOT NULL, "
                + "FOREIGN KEY (emprestimoId) REFERENCES Emprestimo(id) ON DELETE CASCADE, "
                + "FOREIGN KEY (livroId) REFERENCES Livro(id) ON DELETE CASCADE)";

        try (Conexao conn = new Conexao(); PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Tabela ItemEmprestimo criada com sucesso ou já existente.");
        }
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
        List<ItemEmprestimo> itens = new ArrayList<>();
        String sql = "SELECT ie.id, ie.emprestimoId, ie.livroId, ie.quantidade, l.titulo " +
                "FROM ItemEmprestimo ie " +
                "JOIN Livro l ON ie.livroId = l.id " +
                "WHERE ie.emprestimoId = ?"; //dando join de itememprestimo com livro no banco para pegar o nome do livro

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, emprestimoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItemEmprestimo item = new ItemEmprestimo();
                item.setId(rs.getInt("id"));
                item.setEmprestimoId(rs.getInt("emprestimoId"));
                item.setLivroId(rs.getInt("livroId"));
                item.setQuantidade(rs.getInt("quantidade"));


                String titulo = rs.getString("titulo");
                item.setNomeLivro(titulo);  // Supondo que o ItemEmprestimo tenha um método setTitulo

                itens.add(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar itens do empréstimo: " + e.getMessage());
            throw e;
        }

        return itens;
    }


    public ItemEmprestimo getById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ItemEmprestimo WHERE id = ?";
        ItemEmprestimo item = null;

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new ItemEmprestimo();
                    item.setId(rs.getInt("id"));
                    item.setEmprestimoId(rs.getInt("emprestimoId"));
                    item.setLivroId(rs.getInt("livroId"));
                    item.setQuantidade(rs.getInt("quantidade"));

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar item de empréstimo: " + e.getMessage());
            throw e;
        }

        return item;
    }


    public List<ItemEmprestimo> getItemByEmprestimoId(int emprestimoId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ie.id, ie.emprestimoId, ie.livroId, ie.quantidade, l.titulo " +
                "FROM ItemEmprestimo ie " +
                "JOIN Livro l ON ie.livroId = l.id " +
                "WHERE ie.emprestimoId = ?";
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
                    item.setNomeLivro(rs.getString("titulo"));
                    lista.add(item);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar itens de empréstimo: " + e.getMessage());
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
            System.err.println("Erro ao buscar todos os itens de emprestimo: " + e.getMessage());
            throw e;
        }

        return lista;
    }

    public void updateItens(ItemEmprestimo itemEmprestimo) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE ItemEmprestimo SET livroId = ?, quantidade = ? WHERE id = ?";

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, itemEmprestimo.getLivroId());
            ps.setInt(2, itemEmprestimo.getQuantidade());
            ps.setInt(3, itemEmprestimo.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item de empréstimo atualizado com sucesso!");
            } else {
                System.out.println("ID do item de empréstimo não encontrado.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao atualizar itens do empréstimo: " + e.getMessage());
            throw e;
        }
    }

}



