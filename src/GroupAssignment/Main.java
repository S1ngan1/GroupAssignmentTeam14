package GroupAssignment;

import Container.Container;
import Port.Port;
import ScreenDisplay.SystemAdmin;
import Vehicle.Vehicle;
import Vehicle.Ship;
//import Vehicle.Truck;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        SystemAdmin admin = new SystemAdmin();

        Port port1 = new Port("p_1", "CamRanhPort", 109.161440, 11.926220, 10000, true);
        Port port2 = new Port("p_2", "NhaTrangPort", 159.168282, -30.2, 12500, true);

        Vehicle truck1 = new Vehicle("T_01", "Ford F-150", 200D, 72D, 100D, "p_1");
        Vehicle truck2 = new Vehicle("T_02", "Ford F-150", 150D, 72D, 100D, "p_2");

        truck1.loadContainer();
        truck1.unloadContainer(port1);
        truck1.loadContainer();
        truck1.unloadContainer(port1);
        truck1.loadContainer();
        truck1.unloadContainer(port1);

        System.out.println(port1.getContainerHangar().size());
        System.out.println(port2.getContainerHangar().size());

    }
}