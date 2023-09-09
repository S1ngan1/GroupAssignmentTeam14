package GroupAssignment;

import Container.Container;
import Port.Port;
import Vehicle.Vehicle;
import Vehicle.Truck;

public class Main {
    public static void main(String[] args) {
        Port port1 = new Port(3976250, "PhucLy", 25.94F,  4.73F, 5000F, true);
        Port port2 = new Port(3976251, "Phuc", 36.95F,  40.23F, 3000F, false);
        Port port3 = new Port(3976251, "Phuc", 36.5F,  40.1F, 4200F, true);

        Container container1 = new Container("open top", 9551, 2.8F);
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
        System.out.println(truck2.displayGeneralNumberOfContainer());

    }
}