import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    // Method to establish a connection to the MySQL database
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver (Required for database connection)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/career_db"; // Database URL
            String user = "root";  // MySQL username
            String password = "root";  // MySQL password

            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Successfully connected to the database!");
        } catch (ClassNotFoundException e) {
            System.out.println("⚠️ Error: MySQL Driver not found.");
        } catch (SQLException e) {
            System.out.println("⚠️ Error: Unable to connect to the database.");
        }
        return conn; // Return the connection object
    }

    // Main method to test if the connection works (Not used in the actual project)
    public static void main(String[] args) {
        getConnection(); // Test the database connection
    }
}
