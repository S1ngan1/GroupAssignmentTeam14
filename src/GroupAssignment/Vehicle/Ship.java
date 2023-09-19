package GroupAssignment.Vehicle;
import GroupAssignment.Vehicle.Vehicle;

public class Ship extends Vehicle {

    private double totalWeight;

    public Ship(String id, String name, String type, double currentFuel, double fuelCapacity, double carryingCapacity, String currentPort) {
        super(id, name, type, currentFuel, fuelCapacity, carryingCapacity, currentPort);
        this.totalWeight = 0;
    }

    @Override
    public String toString() {
        return "Ship{" +
                " id = '" + super.getId() + '\'' +
                ", name = '" + super.getName()+ '\'' +
                ", type= '" + "ship" + '\'' +
                ", currentFuel = " + super.getCurrentFuel() +
                ", fuelCapacity = " + super.getFuelCapacity() +
                ", carryingCapacity = " + super.getCarryingCapacity() +
                ", currentPort = " + super.getCurrentPort() +
                ", totalWeight = " + getTotalWeight() +
                '}';
    }
}
