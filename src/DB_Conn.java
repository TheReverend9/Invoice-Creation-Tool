/***************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.29.2024
 * Assignment: CIS319 Final Project
 * 
 * DB_Conn handles the connection to the database.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Conn {
    public static Connection connect(String database) {
        String url = "jdbc:sqlite:" + database;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
