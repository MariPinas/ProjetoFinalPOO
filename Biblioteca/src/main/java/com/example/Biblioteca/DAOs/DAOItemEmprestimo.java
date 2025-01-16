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

        public List<ItemEmprestimo> getLivrosByEmprestimoId(int emprestimoId) throws SQLException, ClassNotFoundException {
            String sql = "SELECT * FROM ItemEmprestimo WHERE emprestimo_id = ?";
            List<ItemEmprestimo> lista = new ArrayList<>();

            try (Conexao conn = new Conexao();
                 PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

                ps.setInt(1, emprestimoId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ItemEmprestimo item = new ItemEmprestimo();
                        item.setId(rs.getInt("id"));
                        item.setEmprestimoId(rs.getInt("emprestimo_id"));
                        item.setLivroId(rs.getInt("livro_id"));
                        item.setQuantidade(rs.getInt("quantidade"));
                        lista.add(item);
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("Erro ao buscar livros de emprestimo: " + e.getMessage());
                throw e;
            }

            return lista;
        }

        public void updateItemEmprestimo(ItemEmprestimo itemEmprestimoDto) throws SQLException, ClassNotFoundException {
            String sql = "UPDATE ItemEmprestimo SET quantidade = ? WHERE emprestimo_id = ? AND livro_id = ?";

            try (Conexao conn = new Conexao();
                 PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

                ps.setInt(1, itemEmprestimoDto.getQuantidade());
                ps.setInt(2, itemEmprestimoDto.getEmprestimoId());
                ps.setInt(3, itemEmprestimoDto.getLivroId());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Item de emprestimo atualizado com sucesso!");
                } else {
                    System.out.println("Emprestimo ou Livro nao encontrado para atualizacao.");
                }
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("Erro ao atualizar item de emprestimo: " + e.getMessage());
                throw e;
            }
        }
    }


