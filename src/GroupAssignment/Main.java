package GroupAssignment;

import GroupAssignment.ScreenDisplay.Menu;
import GroupAssignment.ScreenDisplay.SystemAdmin;
import GroupAssignment.Port.Port;
import GroupAssignment.Vehicle.Ship;
import GroupAssignment.Vehicle.Truck;
import GroupAssignment.Vehicle.Vehicle;
import GroupAssignment.Container.Container;
import java.util.Scanner;
import java.util.HashMap;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        /*Menu menu = new Menu();
        menu.mainMenu();
        System.out.println();
        menu.startMenu();*/
        Scanner scanner = new Scanner(System.in);
        Truck truck1 = new Truck("tr_101", "Ford_F-150", "Basic", 66, 23, 690, "p_1");
        Ship ship2 = new Ship("sh_102", "Chevrolet_Colorado_ZR2", "ship", 70, 21.1, 685, "p_5");


        SystemAdmin.portList.get(1).setCurrentStoring(12499);

        ship2.loadContainer();
        for(Container container : ship2.getCarrier()){
            System.out.println(container);
        }
        ship2.moveVehicle(scanner);
        System.out.println(ship2);
    }
}




