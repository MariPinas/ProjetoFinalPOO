package com.example.Biblioteca.DAOs;

import com.example.Biblioteca.Conexao;
import com.example.Biblioteca.Model.Emprestimo;
import com.example.Biblioteca.Model.ItemEmprestimo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOEmprestimo {
    public DAOEmprestimo() {
    }
// falta decrementar em livros apos emprestimo
    public void create(Emprestimo emprestimoDto, List<ItemEmprestimo> itens) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Emprestimo (usuario, dataEmprestimo) VALUES (?, ?)";
        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emprestimoDto.getUsuario());
            ps.setString(2, emprestimoDto.getDataEmprestimo());
            ps.execute();

            // Obtendo o ID do ultimo emprestimo inserido
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int emprestimoId = generatedKeys.getInt(1);

                //add item no emprestimo, ele vai ate a tabela itememprestimo e add nela
                for (ItemEmprestimo item : itens) {
                    String sqlItem = "INSERT INTO ItemEmprestimo (emprestimo_id, livro_id, quantidade) VALUES (?, ?, ?)";
                    try (PreparedStatement psItem = conn.getConexao().prepareStatement(sqlItem)) {
                        psItem.setInt(1, emprestimoId);
                        psItem.setInt(2, item.getLivroId());
                        psItem.setInt(3, item.getQuantidade());
                        psItem.execute();
                    }
                }
            }
            System.out.println("Emprestimo com livros inserido com sucesso!");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao inserir emprestimo com livros: " + e.getMessage());
            throw e;
        }
    }


    public void delete(Emprestimo emprestimoDto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Emprestimo WHERE id = ?";
        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, emprestimoDto.getId());
            ps.execute();
            System.out.println("Emprestimo deletado com sucesso!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao deletar emprestimo: " + e.getMessage());
            throw e;
        }
    }

    public List<Emprestimo> getAllEmprestimos() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Emprestimo";
        List<Emprestimo> lista = new ArrayList<>();

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Emprestimo emprestimoDto = new Emprestimo();
                emprestimoDto.setId(rs.getInt("id"));
                emprestimoDto.setUsuario(rs.getString("usuario"));
                emprestimoDto.setDataEmprestimo(rs.getString("dataEmprestimo"));
                lista.add(emprestimoDto);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar emprestimos: " + e.getMessage());
            throw e;
        }

        return lista;
    }

    public Emprestimo getEmprestimoById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Emprestimo WHERE id = ?";
        Emprestimo emprestimoDto = null;

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emprestimoDto = new Emprestimo();
                    emprestimoDto.setId(rs.getInt("id"));
                    emprestimoDto.setUsuario(rs.getString("usuario"));
                    emprestimoDto.setDataEmprestimo(rs.getString("dataEmprestimo"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar emprestimo de ID: " + e.getMessage());
            throw e;
        }

        return emprestimoDto;
    }

    public void update(Emprestimo livroDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Emprestimo SET usuario = ?, dataEmprestimo = ? WHERE id = ?"; // onde tem ? entra os parametros na query
        Emprestimo emprestimoDto = null;

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setString(1, emprestimoDto.getUsuario());
            ps.setString(2, emprestimoDto.getDataEmprestimo());
            ps.setInt(3, emprestimoDto.getId());

            int rowsAffected = ps.executeUpdate(); // quantas linhas foram afetadas com o update

            if (rowsAffected > 0) { // se tiver mais que 0 foi por que atualizou
                System.out.println("Emprestimo atualizado com sucesso!");
            } else {
                System.out.println("ID de Emprestimo nao encontrado.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao atualizar emprestimo: " + e.getMessage());
            throw e;
        }
    }
}
