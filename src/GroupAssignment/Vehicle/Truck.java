package Vehicle;

import java.util.ArrayList;
import Container.Container;
import Port.Port;
import Vehicle.Vehicle;

public class Truck extends Vehicle {
    private String truckType;
    private double totalWeight; //not an atrribute in the constructor
    private ArrayList<Container> carrier; //not an atrribute in the constructor
    private Port currentPort; //not included in constructor
    public Truck (String name, String id, double carryingCapacity, double currentFuel, double fuelCapacity, String truckType){
        super(name, id, carryingCapacity, currentFuel, fuelCapacity);
        this.truckType = truckType;
        this.totalWeight = 0;
        this.carrier = new ArrayList<Container>();
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

}


