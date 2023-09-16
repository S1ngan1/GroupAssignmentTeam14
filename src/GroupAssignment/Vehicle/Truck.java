package GroupAssignment.Vehicle;

import java.util.ArrayList;
import GroupAssignment.Container.Container;
import GroupAssignment.Port.Port;
import GroupAssignment.Vehicle.Vehicle;

public class Truck extends Vehicle {
    private String truckType;
    private double totalWeight; //not an atrribute in the constructor
    private ArrayList<Container> carrier; //not an atrribute in the constructor
    private String currentPort; //not included in constructor
    public Truck (String name, String id, String truckType, double carryingCapacity, double currentFuel, double fuelCapacity, String currentPort ){
        super(name, id, carryingCapacity, currentFuel, fuelCapacity, currentPort);
        this.truckType = truckType;
        this.totalWeight = 0;
        this.carrier = new ArrayList<Container>();
    }

    public String getTruckType() {
        if (setTruckType(this.truckType)){
            return truckType;
        }
        return "Enter the proper truck type";
    }

    public boolean setTruckType(String truckType) {
        String[] availableTruckType = {"Basic", "Reefer", "Tanker"};
        boolean matched = false;
        for (String type : availableTruckType){
            if (type.toLowerCase().equals(truckType.toLowerCase())){
                matched = true;
            }
        }
        //set the container type if it is valid
        if (matched){
            this.truckType = truckType;
            return true;
        }
        return false;
    }

}


