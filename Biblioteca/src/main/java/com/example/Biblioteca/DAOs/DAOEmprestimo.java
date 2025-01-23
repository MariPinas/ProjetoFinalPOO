package com.example.Biblioteca.DAOs;

import com.example.Biblioteca.Banco.Conexao;
import com.example.Biblioteca.Model.Emprestimo;
import com.example.Biblioteca.Model.ItemEmprestimo;
import com.example.Biblioteca.Model.Livro;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOEmprestimo {
    public DAOEmprestimo() {
    }

    public void criarTabelaEmprestimo() throws SQLException, ClassNotFoundException {
        String sql = "CREATE TABLE IF NOT EXISTS Emprestimo ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "usuario VARCHAR(255) NOT NULL, "
                + "dataEmprestimo DATE NOT NULL)";

        try (Conexao conn = new Conexao(); PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Tabela Emprestimo criada com sucesso ou ja existente.");
        }
    }

    public void create(Emprestimo emprestimoDto, List<ItemEmprestimo> itens) throws SQLException, ClassNotFoundException {
        System.out.println("Creating Emprestimo...");
        String sql = "INSERT INTO Emprestimo (usuario, dataEmprestimo) VALUES (?, ?)";

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emprestimoDto.getUsuario());
            ps.setString(2, emprestimoDto.getDataEmprestimo());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int emprestimoId = generatedKeys.getInt(1); // ID gerado do empréstimo
                System.out.println("Emprestimo ID gerado: " + emprestimoId);

                String sql2 = "INSERT INTO ItemEmprestimo (emprestimoId, livroId, quantidade) VALUES (?, ?, ?)";
                try (PreparedStatement ps2 = conn.getConexao().prepareStatement(sql2)) {
                    for (ItemEmprestimo item : itens) {
                        Livro livro = new DAOLivro().getById(item.getLivroId());
                        System.out.println("Verificando livro: " + livro.getTitulo() + " com quantidade " + item.getQuantidade() + ".");

                        if (livro.getQuantidade() >= item.getQuantidade()) {
                            livro.setQuantidade(livro.getQuantidade() - item.getQuantidade());
                            new DAOLivro().update(livro);

                            // Adicionar item ao batch
                            ps2.setInt(1, emprestimoId);
                            ps2.setInt(2, item.getLivroId());
                            ps2.setInt(3, item.getQuantidade());
                            ps2.addBatch();
                            System.out.println("Item adicionado ao batch: Livro ID " + item.getLivroId() + ", Quantidade: " + item.getQuantidade());
                        } else {
                            throw new IllegalStateException("Quantidade insuficiente para o livro: " + livro.getTitulo());
                        }
                    }

                    int[] rowsAffected = ps2.executeBatch();
                    System.out.println("Itens de empréstimo inseridos com sucesso! Linhas afetadas: " + rowsAffected.length);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao criar empréstimo: " + e.getMessage());
            throw e;
        }
    }



    public void delete(int emprestimoId) throws SQLException, ClassNotFoundException {
        String sqlDeleteItems = "DELETE FROM ItemEmprestimo WHERE emprestimoId = ?";
        String sqlDeleteEmprestimo = "DELETE FROM Emprestimo WHERE id = ?";

        try (Conexao conn = new Conexao()) {
            DAOItemEmprestimo daoItemEmprestimo = new DAOItemEmprestimo();
            List<ItemEmprestimo> itens = daoItemEmprestimo.getLivrosByEmprestimoId(emprestimoId);

            //devolve livros
            DAOLivro daoLivro = new DAOLivro();
            for (ItemEmprestimo item : itens) {
                Livro livro = daoLivro.getById(item.getLivroId());
                livro.setQuantidade(livro.getQuantidade() + item.getQuantidade());
                daoLivro.update(livro);
            }
            //deletar os itens
            try (PreparedStatement psItems = conn.getConexao().prepareStatement(sqlDeleteItems)) {
                psItems.setInt(1, emprestimoId);
                psItems.executeUpdate();
            }
            //delete o emprestimo
            try (PreparedStatement psEmprestimo = conn.getConexao().prepareStatement(sqlDeleteEmprestimo)) {
                psEmprestimo.setInt(1, emprestimoId);
                psEmprestimo.executeUpdate();
            }

            System.out.println("Emprestimo e itens removidos com sucesso!");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao deletar empréstimo: " + e.getMessage());
            throw e;
        }
    }


    public List<Emprestimo> getAllEmprestimos() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Emprestimo";
        List<Emprestimo> listaEmprestimos = new ArrayList<>();

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setUsuario(rs.getString("usuario"));
                emprestimo.setDataEmprestimo(rs.getString("dataEmprestimo"));

                //pega o id do emprestimo para ver quais itens sao deste emprestimo
                List<ItemEmprestimo> itens = new DAOItemEmprestimo().getLivrosByEmprestimoId(emprestimo.getId());
                emprestimo.setItens(itens);  //dai seta os itens em emprestimo

                listaEmprestimos.add(emprestimo);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erro ao buscar emprestimos: " + e.getMessage());
            throw e;
        }

        return listaEmprestimos;
    }

    public Emprestimo getEmprestimoById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Emprestimo WHERE id = ?";
        Emprestimo emprestimoDto = null;

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    emprestimoDto = new Emprestimo();
                    emprestimoDto.setId(rs.getInt("id"));
                    emprestimoDto.setUsuario(rs.getString("usuario"));
                    emprestimoDto.setDataEmprestimo(rs.getString("dataEmprestimo"));

                    List<ItemEmprestimo> itens = new DAOItemEmprestimo().getLivrosByEmprestimoId(emprestimoDto.getId());
                    emprestimoDto.setItens(itens);  //dai seta os itens em emprestimo
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao buscar emprestimo de ID: " + e.getMessage());
            throw e;
        }

        return emprestimoDto;
    }

    public void update(Emprestimo emprDto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Emprestimo SET usuario = ?, dataEmprestimo = ? WHERE id = ?";

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setString(1, emprDto.getUsuario());
            ps.setDate(2, Date.valueOf(emprDto.getDataEmprestimo()));
            ps.setInt(3, emprDto.getId());

            DAOItemEmprestimo daoItemEmprestimo = new DAOItemEmprestimo();
            List<ItemEmprestimo> itens = daoItemEmprestimo.getLivrosByEmprestimoId(emprDto.getId());

            for (ItemEmprestimo item : emprDto.getItens()) {
                ItemEmprestimo itemAntigo = daoItemEmprestimo.getById(item.getId());
                Livro livro = new DAOLivro().getById(item.getLivroId());

                if (itemAntigo.getQuantidade() > item.getQuantidade()) {
                    int diff = itemAntigo.getQuantidade() - item.getQuantidade();
                    if (livro.getQuantidade() >= diff) {
                        livro.setQuantidade(livro.getQuantidade() + diff);
                        new DAOLivro().update(livro);
                    } else {
                        throw new IllegalStateException("Quantidade insuficiente para o livro: " + livro.getTitulo());
                    }
                } else if (itemAntigo.getQuantidade() < item.getQuantidade()) {
                    int diff = item.getQuantidade() - itemAntigo.getQuantidade();
                    livro.setQuantidade(livro.getQuantidade() - diff);
                    new DAOLivro().update(livro);
                }
                daoItemEmprestimo.updateItens(item);
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Empréstimo e itens atualizados com sucesso!");
            } else {
                System.out.println("ID de empréstimo não encontrado.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao atualizar empréstimo ou itens: " + e.getMessage());
            throw e;
        }
    }


    public ItemEmprestimo getItensEmprestimo(int id, int emprestimoId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM itememprestimo WHERE id = ? AND emprestimoid = ?";

        try (Conexao conn = new Conexao();
             PreparedStatement ps = conn.getConexao().prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, emprestimoId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                ItemEmprestimo item = new ItemEmprestimo();
                item.setId(rs.getInt("id"));
                item.setEmprestimoId(rs.getInt("emprestimoId"));
                item.setLivroId(rs.getInt("livroId"));
                item.setQuantidade(rs.getInt("quantidade"));
                return item;
            }
            return null;
        }catch (ClassNotFoundException | SQLException e) {
                System.err.println("Erro ao atualizar item emprestimo: " + e.getMessage());
                throw e;
            }


    }

}
