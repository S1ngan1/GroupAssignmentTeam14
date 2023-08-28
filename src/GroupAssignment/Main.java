package GroupAssignment;

public class Main {
    public static void main(String[] args) {
        Port port1 = new Port(3976250, "PhucLy", 25.94F,  4.73F, 5000F, true);
        Port port2 = new Port(3976251, "Phuc", 36.95F,  40.23F, 3000F, false);
        Port port3 = new Port(3976251, "Phuc", 36.5F,  40.1F, 4200F, true);
        Container container1 = new Container("Dry storage", 9551, 2.8F);
        System.out.println(port1);
        System.out.println(port2);
        System.out.println(port3.getStoringCapacity());
        System.out.println(container1.getContainerType());
    }
}