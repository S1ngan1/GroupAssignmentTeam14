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
        Scanner scanner = new Scanner(System.in);
        /*Menu.mainMenu();
        Menu.startMenu();*/
        System.out.println(SystemAdmin.vehicleList.get("sh_302"));
        SystemAdmin.vehicleList.get("sh_302").loadContainer();

        SystemAdmin.vehicleList.get("sh_302").displayGeneralNumberOfContainer();
        SystemAdmin.vehicleList.get("sh_302").moveVehicle(scanner);
        System.out.println(SystemAdmin.vehicleList.get("sh_302"));

    }
}




