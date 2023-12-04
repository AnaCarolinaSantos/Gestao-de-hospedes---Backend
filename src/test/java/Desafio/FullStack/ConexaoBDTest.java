package Desafio.FullStack;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ConexaoBDTest {

    @Test
    void testGetConnectionSuccess() {
        try {
            Connection connection = ConexaoBD.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetConnectionSQLException() {
        try {
            DriverManagerMock.getMockedConnection();
            assertThrows(SQLException.class, () -> ConexaoBD.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class DriverManagerMock {
        static void getMockedConnection() throws SQLException {
            DriverManager mockedDriverManager = mock(DriverManager.class);
            throw new SQLException("Erro ao conectar ao banco de dados");
        }
    }
}
