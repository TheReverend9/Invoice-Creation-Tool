/****************************************************************************************
 * Name: Kevin Blackmon
 * Date: 4.30.2024
 * Assignment: CIS319 Final Project
 * 
 * This abstract class handles the user's interface to interact with the program.
 */

public abstract class UI {
    public abstract void Menu();
    public abstract void IdleScreen();

    public void ClearScreen() {
        System.out.print("\33[H\033[2J");
        System.out.flush();
    }
}
