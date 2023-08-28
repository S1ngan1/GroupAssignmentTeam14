package GroupAssignment;

public class Main {
    public static void main(String[] args) {
        Port port1 = new Port(3976250, "PhucLy", 25.94F,  4.73F, 5000F, true);
        Port port2 = new Port(3976251, "Phuc", 36.95F,  40.23F, 3000F, false);
        Port port3 = new Port(3976251, "Phuc", 36.5F,  40.1F, 4200F, true);

        Container container1 = new Container("open top", 9551, 2.8F);
        Container container2 = new Container("wet stoRaGe", 951, 4.6F);

        Vehicle vehicle1 = new Vehicle("Ford F-150", 101);
        Vehicle vehicle2 = new Vehicle("Chevrolet Colorado ZR2", 102);
        Vehicle vehicle3 = new Vehicle("Toyota Tacoma TRD Pro", 103);

        System.out.println(vehicle1);
        vehicle1.loadContainer();
        vehicle1.loadContainer();
        vehicle1.loadContainer();
        vehicle1.loadContainer();
        vehicle1.unloadContainer();
        System.out.println(vehicle1.displayGeneralNumberOfContainer());

        /*System.out.println(port1);
        System.out.println(port2);
        System.out.println(port3.getStoringCapacity());
        System.out.println(container1);
        System.out.println(container2);*/
    }
}