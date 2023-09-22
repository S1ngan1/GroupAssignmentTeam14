//fuelLost(gallons) = (ContainerWeight * Fuelconsumption for each type of container placed on different types of vehicles (weight/km * km ) * numberOfContainer
package GroupAssignment.Container;

import java.util.HashMap;

public class Container {
    private String id;
    private String containerType;
    private double weight;
    private static HashMap<String, Double> ship_Container = new HashMap<String, Double>();
    private static HashMap<String, Double> truck_Container = new HashMap<String, Double>();

    public Container(String id, String containerType, double weight){
        this.id = id;
        this.containerType = containerType;
        this.weight = weight;

    }

    //get the appropriate container type
    public String getContainerType() {
        if (setContainerType(this.containerType)){
            return containerType;
        }
        return "Enter the proper container type";
    }

    //Provide the available container types and force users to choose one
    public boolean setContainerType(String containerType) {
        String[] availableContainerType = {"Dry storage", "Open top", "Open side", "Refrigerated", "Liquid"};
        boolean matched = false;
        for (String type : availableContainerType){
            if (type.toLowerCase().equals(containerType.toLowerCase())){
                matched = true;
            }
        }
    //set the container type if it is valid
        if (matched){
            this.containerType = containerType;
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static HashMap<String, Double> getShip_Container() {
        ship_Container.put("Dry Storage", 3.5);
        ship_Container.put("Open Top", 2.8);
        ship_Container.put("Open Side", 2.7);
        ship_Container.put("Refrigerated", 4.5);
        ship_Container.put("Liquid", 4.8);
        return ship_Container;
    }

    public static void setShip_Container(HashMap<String, Double> ship_Container) {
        Container.ship_Container = ship_Container;
    }

    public static HashMap<String, Double> getTruck_Container() {
        truck_Container.put("Dry Storage", 4.6);
        truck_Container.put("Open Top", 3.2);
        truck_Container.put("Open Side", 3.2);
        truck_Container.put("Refrigerated", 5.4);
        truck_Container.put("Liquid", 5.3);
        return truck_Container;
    }

    public static void setTruck_Container(HashMap<String, Double> truck_Container) {
        Container.truck_Container = truck_Container;
    }

    @Override
    public String toString() {
        if (!setContainerType(this.containerType)){
            return "No such container exist";
        }
        return "Container{" +
                " id = " + id +
                ", containerType = '" + containerType.toLowerCase() + '\'' +
                ", weight = " + weight +
                '}';
    }
}
