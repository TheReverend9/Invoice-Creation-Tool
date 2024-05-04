/************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.28.2024
 * Assignment: CIS319 Final Project
 * 
 * This class handles the database interactions for the Invoice table in the database.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InvoiceDB {
    public static boolean createTable(Connection conn) {
        //SQL statement for creating a new table
        String query = 
            "CREATE TABLE IF NOT EXISTS Invoices (\n"
            + "  InvoiceID integer PRIMARY KEY\n"
            + "  ,Electric REAL\n"
            + "  ,Rent REAL\n"
            + "  ,Water REAL\n"
            + "  ,LotStyle BOOLEAN\n"
            + "  ,TenantID integer\n"
            + "  ,CONSTRAINT fk_tenants\n"
            + "    FOREIGN KEY (TenantID)\n"
            + "    REFERENCES Tenants(TenantID))";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void addInvoice(Connection conn, Invoice i) {
        String query = "INSERT INTO Invoices(Electric, Rent, Water, LotStyle, TenantID)"
            + " VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDouble(1, i.getElectric());
            pst.setDouble(2, i.getRent());
            pst.setDouble(3, i.getWater());
            pst.setBoolean(4, i.getPrimitive());
            pst.setInt(5, i.getTenantID());
            pst.executeUpdate();

            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void updateInvoice(Connection conn, Invoice i) {
        String query = "UPDATE Invoices SET Electric=?, Rent=?, Water=?, LotStyle=?, TenantID=? WHERE InvoiceID=?";
            
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDouble(1, i.getElectric());
            pst.setDouble(2, i.getRent());
            pst.setDouble(3, i.getWater());
            pst.setBoolean(4, i.getPrimitive());
            pst.setInt(5, i.getTenantID());
            pst.setInt(6, i.getID());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteInvoice(Connection conn, int id) {
        String query = "DELETE from Invoices WHERE InvoiceID=?";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Invoice> getAllInvoices(Connection conn) {
        ArrayList<Invoice> Invoices = new ArrayList<Invoice>();
        String query = "SELECT * FROM Invoices";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Invoice i = new Invoice(rs.getInt("InvoiceID"), rs.getDouble("Electric"),
                    rs.getDouble("Rent"), rs.getDouble("Water"), rs.getBoolean("LotStyle"),
                    rs.getInt("TenantID"));
                Invoices.add(i);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Invoices;
    }

    public static Invoice getInvoice(Connection conn, int id) {
        Invoice i = new Invoice();

        String query = "SELECT * FROM Invoices WHERE InvoiceID=?";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                i.setID(rs.getInt("InvoiceID"));
                i.setTenantID(rs.getInt("TenantID"));
                i.setElectric(rs.getDouble("Electric"));
                i.setRent(rs.getDouble("Rent"));
                i.setWater(rs.getDouble("Water"));
                i.setTotal(rs.getDouble("Electric") + rs.getDouble("Water") + rs.getDouble("Rent"));
                i.setLotStyle(rs.getBoolean("LotStyle"));
            } else {
                i.setID(id);
                i.setTenantID(99);
                i.setElectric(999.99);
                i.setRent(999.99);
                i.setWater(999.99);
                i.setTotal(999.99);
                i.setLotStyle(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return i;
    }

    public static int GetLastID(Connection conn) {
        String query = "SELECT MAX(InvoiceID) as Max FROM Invoices";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs.getInt("Max");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
