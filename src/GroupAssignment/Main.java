package GroupAssignment;

import Container.Container;
import Port.Port;
import ScreenDisplay.SystemAdmin;
import Vehicle.Vehicle;
//import Vehicle.Truck;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        SystemAdmin admin = new SystemAdmin();

        Vehicle truck1 = new Vehicle("T_01", "Ford F-150", 200D, 72D, 100D);
        truck1.loadContainer();
        truck1.unloadContainer();
        truck1.loadContainer();
        truck1.unloadContainer();

    }
}