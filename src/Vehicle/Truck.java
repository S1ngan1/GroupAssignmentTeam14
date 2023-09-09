package Vehicle;

import java.util.ArrayList;
import Container.Container;

public class Truck extends Vehicle {
    private String truckType;
    private ArrayList<Container> carrier;
    public Truck (String name, String id, String truckType){
        super(name, id);
        this.truckType = truckType;
        this.carrier = new ArrayList<Container>();
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }
    @Override
    public String toString() {
        return "Truck {" +
                " name = '" + super.getName() + '\'' +
                ", id = " + super.getId() + '\'' +
                ", truckType = " + truckType +
                '}';
    }
}


