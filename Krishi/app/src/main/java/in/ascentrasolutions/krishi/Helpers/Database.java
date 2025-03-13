package in.ascentrasolutions.krishi.Helpers;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://37.27.108.55:3306/your_database_name";
    private static final String USERNAME = "Ojuiyokz_Ashok";
    private static final String PASSWORD = "Ashokkumar21";

    // Connect to the Razorhost database
    public static Connection connectToDatabase() {
        try {
            // Load the MySQL JDBC driver (optional in newer versions of JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to fetch data from the database
    public void fetchData() {
        Connection connection = connectToDatabase();
        if (connection != null) {
            try {
                // Create a Statement object
                Statement stmt = connection.createStatement();

                // SQL Query to fetch data
                String query = "SELECT * FROM table1";
                ResultSet rs = stmt.executeQuery(query);

                // Iterate over the result set and process the data
                while (rs.next()) {
                    String columnData = rs.getString("name");
                    //System.out.println(columnData);

                    Log.e("runn", columnData);
                }

                // Close the ResultSet, Statement, and Connection objects
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
