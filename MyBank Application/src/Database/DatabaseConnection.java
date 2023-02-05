package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	public Connection connection;
	public Statement statement;
	private final String url = "jdbc:mysql:///MyBank_db", user = "root", password = "password";

	public DatabaseConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
