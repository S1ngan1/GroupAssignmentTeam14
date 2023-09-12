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

        Vehicle truck1 = new Vehicle("T_01", "Ford F-150", 200D, 72D, 100D, "1");
        Vehicle truck2 = new Vehicle("T_02", "Ford F-150", 150D, 72D, 100D, "2");

        admin.calculateDistance();
    }
}