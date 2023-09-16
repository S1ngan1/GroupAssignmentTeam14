package GroupAssignment;

import GroupAssignment.ScreenDisplay.Menu;
import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        Menu menu = new Menu();
        menu.mainMenu();
        System.out.println();
        menu.startMenu();
    }
}




