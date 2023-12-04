package Desafio.FullStack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/Hotel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1310";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Driver do banco de dados não localizado.");

        } catch (SQLException ex) {
            throw new RuntimeException("Ocorreu um erro ao acessar o banco: " + ex.getMessage());

        }
    }
}
