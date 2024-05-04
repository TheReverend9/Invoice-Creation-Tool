/**************************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.30.2024
 * Assignment: CIS319 Final Project
 * 
 * This class extends the abstract class UI.
 */
import java.sql.Connection;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainScreenUI extends UI {
    private Connection conn = DB_Conn.connect("KevinBlackmon.db");
    private Scanner userInput = new Scanner(System.in);
    private int userChoice;

    
    public void Menu() {
        //Generate menu screen for user input
        while(true) {
            try {
                userChoice = 0;
                System.out.println("==================  Main Menu ==================");
                System.out.println("\n(1) Generate Tenant Invoice");
                System.out.println("(2) Add New Tenant");
                System.out.println("(3) Add New Invoice");
                System.out.println("(4) List of Current Invoices");
                System.out.println("(5) List of Current Tenants");
                System.out.println("(0) EXIT");
                System.out.println("\nMENU NUMBER:  ");
                
                userChoice = userInput.nextInt();

                MenuChoice(userChoice);
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                userInput.nextLine();
                continue;
            }
        }
    }

    public void Exit() {
        //Generate Exit Screen
        ClearScreen();
        System.out.println("=============================================");
        System.out.println("------------EXITING Application--------------");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
    }

    public void IdleScreen() {
        //Generate Splash screen for Applicaiton
        System.out.println("=============================================");
        System.out.println("--------Invoice Creation Application---------");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
    }

    public void MenuChoice(int userChoice) {

        Scanner userInput = new Scanner(System.in);
        ClearScreen();
        if (userChoice == 1) { 
            //GENERATE A INVOICE AND SAVE TO TEXT FILE
            int id;
            System.out.println("================== Generate Invoice ==================");
            while (true) {
                try {
                    System.out.println("\nEnter Invoice ID [-1 to Exit]: ");
                    System.out.println("(Enter 0 if unsure)");
                    id = userInput.nextInt();
                    if (id == 0) {
                        ArrayList<Invoice> invoices = new ArrayList<Invoice>();
                        invoices = InvoiceDB.getAllInvoices(conn);

                        for (Invoice i : invoices) {
                            System.out.println(i.generateInfo());
                            System.out.println();
                        }
                        continue;
                    } else if (id == -1) {
                        break;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    userInput.nextLine();
                    continue;
                }
            }

            

            Invoice i = InvoiceDB.getInvoice(conn, id);

            Tenant t = TenantDB.getTenant(conn, i.getTenantID());

            String file = i.getID() + "_InvoiceFile";
            try {
                PrintWriter invoiceWriter = new PrintWriter(file);
                invoiceWriter.println("Full Name:\t\t" + t.getName());
                invoiceWriter.println(i.generateInfo());
                invoiceWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            ClearScreen();
            System.out.println("Invoice Created for ID: " + i.getID());
            System.out.println("Tenant Name: " + t.getName());
            Menu();

        } else if ( userChoice == 2) {
            //Display Add Tenant Menu and get user input
            System.out.println("==================  Add Tenant ==================");
            System.out.println("\nTenant Name: ");
            String name = userInput.nextLine();
            System.out.println("Tenant Phone: ");
            String phone = userInput.nextLine();
            System.out.println("Tenant Email: ");
            String email = userInput.nextLine();

            Tenant t = new Tenant(name, phone, email);

            TenantDB.addTenant(conn, t);
            ClearScreen();
            System.out.println("\n !!! Tenant Addedd !!! \n");
            Menu();
        } else if ( userChoice == 3) {
            //Display Add Invoice Menu and get user input
            System.out.println("==================  Add Invoice ==================");
            System.out.println("\nTenant ID: ");
            int tenantID = userInput.nextInt();
            System.out.println("Electric Price: ");
            double electric = userInput.nextDouble();
            System.out.println("Rent Price: ");
            double rent = userInput.nextDouble();
            System.out.println("Water Price: ");
            double water = userInput.nextDouble();
            System.out.println("Is Lot Style Primitive? [y/n] ");
            String style = userInput.next();
            boolean primitive = false;

            if ( style == "y") {
                primitive = true;
            } else {
                primitive = false;
            }

            Invoice i = new Invoice(tenantID, electric, rent, water, primitive);

            InvoiceDB.addInvoice(conn, i);
            i.setID(InvoiceDB.GetLastID(conn));
            ClearScreen();

            System.out.println(i.generateInfo());
            Menu();
        } else if( userChoice == 4) {
            //LIST CURRENT INVOICES
            ArrayList<Invoice> invoices = new ArrayList<Invoice>();
            invoices = InvoiceDB.getAllInvoices(conn);

            for (Invoice i : invoices) {
                System.out.println(i.generateInfo());
                System.out.println();
            }
            Menu();
        } else if( userChoice == 5) {
            //LIST CURRENT TENANTS
            ArrayList<Tenant> tenants = new ArrayList<Tenant>();
            tenants = TenantDB.getAllTenants(conn);

            for (Tenant t : tenants) {
                System.out.println(t.generateInfo());
                System.out.println();
            }
            Menu();
        } else {
            Exit(); 
            userInput.close();
            System.exit(0);
        }

    }
}
