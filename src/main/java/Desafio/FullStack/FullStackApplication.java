package Desafio.FullStack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class FullStackApplication {

	public static void main(String[] args) throws SQLException {

		ConexaoBD conexao = new ConexaoBD();
		conexao.getConnection();

		SpringApplication.run(FullStackApplication.class, args);

	}

}
