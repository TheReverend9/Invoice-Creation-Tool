/****************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.21.2024
 * Assignment: Final Project
 * 
 * Class that handles invoice creation operations.
 */

public class Invoice implements Info {
    private int ID;
    private int TenantID;
    private double ElectricPrice;
    private double WaterPrice;
    private double RentPrice;
    private double Total;
    private boolean Primitive;

    public Invoice(int id, double electric, double rent, double water, boolean primitive, int tenantID) {
        ID = id;
        TenantID = tenantID;
        ElectricPrice = electric;
        RentPrice = rent;
        WaterPrice = water;
        Primitive = primitive;
        Total = electric + rent + water;
    }

    public Invoice(int id, double electric, double rent, double water, boolean primitive) {
        TenantID = id;
        ElectricPrice = electric;
        RentPrice = rent;
        WaterPrice = water;
        Primitive = primitive;
        Total = electric + rent + water;
    }

    public Invoice() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public int getTenantID() {
        return TenantID;
    }

    public void setTenantID(int id) {
        TenantID = id;
    }

    public double getElectric() {
        return ElectricPrice;
    }

    public void setElectric(double price) {
        ElectricPrice = price;
    }

    public double getWater() {
        return WaterPrice;
    }

    public void setWater(double price) {
        WaterPrice = price;
    }

    public double getRent() {
        return RentPrice;
    }

    public void setRent(double price) {
        RentPrice = price;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double price) {
        Total = price;
    }

    public String getLotStyle() {
        if (Primitive) {
            return "Primitive Camping Site";
        } else {
            return "RV/Camper Site";
        }
    }

    public void setLotStyle(boolean style) {
        Primitive = style;
    }

    public Boolean getPrimitive() {
        return Primitive;
    }
    
    public String generateInfo() {
        return String.format("Invoice ID:\t\t%s%nTenant ID:\t\t%s%nElectric Price:\t\t%s%nWater Price:\t\t%s%nRent Price:\t\t%s%nTotal:\t\t\t%s%nLot Style:\t\t%s",
        ID, TenantID, ElectricPrice, WaterPrice, RentPrice, Total, getLotStyle());
    }
}
