/**************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.29.2024
 * Assignment: CIS319 Final Project
 * 
 * This class handles the database interactions for the Tenants table. 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TenantDB {
    public static boolean createTable(Connection conn) {
        //SQL to create a new table
        String query = 
            "CREATE TABLE IF NOT EXISTS Tenants (\n" 
            + "   TenantID integer PRIMARY KEY\n"
            + "   ,Name varchar(255)\n"
            + "   ,Phone varchar(10)\n"
            + "   ,Email varchar(255))";


        try {
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            return true;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void addTenant(Connection conn, Tenant t) {
        String query = "INSERT INTO Tenants(Name, Phone, Email) VALUES(?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, t.getName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getEmail());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateTenant(Connection conn, Tenant t) {
        String query = "UPDATE Tenants SET Name=?, Phone=?, Email=? WHERE TenantID=?";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, t.getName());
            pst.setString(2, t.getPhone());
            pst.setString(3, t.getEmail());
            pst.setInt(4, t.getID());
            pst.executeUpdate(); 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTenant(Connection conn, int id) {
        String query = "DELETE from Tenants WHERE TenantID=?";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Tenant> getAllTenants(Connection conn) {
        ArrayList<Tenant> Tenants = new ArrayList<Tenant>();
        String query = "SELECT * FROM Tenants";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Tenant t = new Tenant(rs.getInt("TenantID"), rs.getString("Name"),
                    rs.getString("Phone"), rs.getString("Email"));
                Tenants.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Tenants;
    }

    public static Tenant getTenant(Connection conn, int id) {
        Tenant t = new Tenant();

        String query = "SELECT * FROM Tenants WHERE TenantID=?";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                t.setID(rs.getInt("TenantID"));
                t.setName(rs.getString("Name"));
                t.setPhone(rs.getString("Phone"));
                t.setEmail(rs.getString("Email"));
            } else {
                t.setID(rs.getInt("TenantID"));
                t.setName("Tenant");
                t.setPhone("Exist");
                t.setEmail("Please Try Again");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return t;
    }
}
