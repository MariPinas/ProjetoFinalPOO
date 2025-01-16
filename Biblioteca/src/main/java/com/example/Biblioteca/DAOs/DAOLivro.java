package com.example.Biblioteca.DAOs;

import com.example.Biblioteca.Conexao;
import com.example.Biblioteca.Model.Livro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOLivro {
    public DAOLivro() {
    }

    public void create(Livro livroDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Livro (titulo, quantidade) VALUES (?, ?)";
        try (Conexao conn = new Conexao(); //try-with-resources faz fechar os recursos sozinho, conexao deve implementar o AutoClosable e dar override no close
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setString(1, livroDto.getTitulo());
            ps.setInt(2, livroDto.getQuantidade());
            ps.execute();
            System.out.println("Livro inserido com sucesso!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
            throw e;
        }
    }

    public void delete(Livro livroDto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Livro WHERE id = ?";
        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, livroDto.getId());
            ps.execute();
            System.out.println("Livro deletado com sucesso!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao deletar livro: " + e.getMessage());
            throw e;
        }
    }

    public List<Livro> getAllLivros() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Livro";
        List<Livro> lista = new ArrayList<>();

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Livro livroDto = new Livro();
                livroDto.setId(rs.getInt("id"));
                livroDto.setTitulo(rs.getString("titulo"));
                livroDto.setQuantidade(rs.getInt("quantidade"));
                lista.add(livroDto);
            }

            lista.forEach(livro -> {
                System.out.println("Livro ID: " + livro.getId());
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Quantidade: " + livro.getQuantidade());
            });

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
            throw e;
        }

        return lista;
    }

    public Livro getById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Livro WHERE id = ?";
        Livro livroDto = null;

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    livroDto = new Livro();
                    livroDto.setId(rs.getInt("id"));
                    livroDto.setTitulo(rs.getString("titulo"));
                    livroDto.setQuantidade(rs.getInt("quantidade"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
            throw e;
        }

        return livroDto;
    }

    public void update(Livro livroDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Livro SET titulo = ?, quantidade = ? WHERE id = ?"; // onde tem ? entra os parametros na query

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setString(1, livroDto.getTitulo());
            ps.setInt(2, livroDto.getQuantidade());
            ps.setInt(3, livroDto.getId());

            int rowsAffected = ps.executeUpdate(); // quantas linhas foram afetadas com o update

            if (rowsAffected > 0) { // se tiver mais que 0 foi por que atualizou
                System.out.println("Livro atualizado com sucesso!");
            } else {
                System.out.println("ID de Livro nao encontrado.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
            throw e;
        }
    }

}
