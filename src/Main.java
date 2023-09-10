package GroupAssignment;

import Container.Container;
import Port.Port;
import ScreenDisplay.SystemAdmin;
import Vehicle.Vehicle;
import Vehicle.Truck;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        SystemAdmin admin = new SystemAdmin();

        admin.addPort();

       /*Container container1 = new Container("open top", 9551, 2.8F);
        Container container2 = new Container("wet stoRaGe", 951, 4.6F);

        Truck truck1 = new Truck("Ford F-150", "101", "Tanker Truck");
        Truck truck2 = new Truck("Ford F-150", "102", "Refree Truck");
        System.out.println(truck1);
        System.out.println(truck2);
        truck1.loadContainer();
        truck1.loadContainer();
        truck2.loadContainer();
        truck1.unloadContainer();

        System.out.println(truck1.displayGeneralNumberOfContainer());
        System.out.println(truck2.displayGeneralNumberOfContainer());*/

    }
}