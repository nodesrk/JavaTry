package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.StudentModel;

public class DatabaseController {
	private final String url = "jdbc:mysql://localhost:3306/college_app";
	private final String user = "root";
	private final String password = "";
	private Connection connection;

	public DatabaseController() {
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void connect() throws SQLException {
		connection = DriverManager.getConnection(url, user, password);
	}

	public void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	public void save(StudentModel student) throws SQLException {
		String sql = "INSERT INTO student_info (username, first_name, last_name, email, dob, gender, phone_number, subject, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, student.getUsername());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setString(4, student.getEmail());
			statement.setString(5, student.getDob());
			statement.setString(6, student.getGender());
			statement.setString(7, student.getPhone());
			statement.setString(8, student.getSubject());
			statement.setString(9, student.getPassword());

			statement.executeUpdate();
		}
	}

	public int getStudentLoginInfo(String username, String password) {
		try {
			String query = "SELECT * FROM student_info WHERE username = ? AND password = ?";
			try (PreparedStatement st = connection.prepareStatement(query)) {
				st.setString(1, username);
				st.setString(2, password);
				try (ResultSet rs = st.executeQuery()) {
					if (rs.next()) {
						// User name and password match in the database
						return 1;
					} else {
						// No matching record found
						return 0;
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		}
	}

}
