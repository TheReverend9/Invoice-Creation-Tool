/**************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.21.2024
 * Assignment: Final Project
 * 
 * Class for the tenants of the database.
 */

 public class Tenant {
    private int ID;
    private String Name;
    private String Phone;
    private String Email;

    public Tenant(int id, String name, String phone, String email) {
        ID = id;
        Name = name;
        Phone = phone;
        Email = email;
    }

    public Tenant(String name, String phone, String email) {
        Name = name;
        Phone = phone;
        Email = email;
    }

    public Tenant() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String generateInfo() {
        return String.format("Tenant ID:\t\t%s%nFull Name:\t\t%s%nPhone:\t\t\t%s%nEmail:\t\t\t%s",
        ID, Name, Phone, Email);
    }
 }