package com.example.Biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao implements AutoCloseable{
        private Connection conn;

        public Conexao() {
        }

        private void conectar() throws ClassNotFoundException {
            System.out.println("Conectando ao banco...");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("conn:" + String.valueOf(this.conn));
                this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "1234");
                if (this.conn != null) {
                    System.out.println("Conexão realizada!");
                    System.out.println("conn:" + String.valueOf(this.conn));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public Connection getConexao() throws ClassNotFoundException {
            this.conectar();
            return this.conn;
        }

    @Override
    public void close() throws SQLException {
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
