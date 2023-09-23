package GroupAssignment.Port;

import GroupAssignment.Container.Container;
import GroupAssignment.Vehicle.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

public class Port implements Serializable {
    private String port_ID;
    private String port_name;
    private double X;
    private double Y;
    private double storingCapacity;
    private boolean landingAbility;
    private double currentStoring;
    public ArrayList<Vehicle> vehicleHangar;

    private ArrayList<Container> containerHangar;

    //private ArrayList<GroupAssignment.Trip> tripRecord;

    public Port(String port_ID, String port_name, double X, double Y, double storingCapacity, boolean landingAbility){
        this.port_ID = port_ID;
        this.port_name = port_name;
        this.X = X;
        this.Y = Y;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.vehicleHangar = new ArrayList<Vehicle>();
        this.containerHangar = new ArrayList<Container>();
    }

    public String getP_ID() {
        return port_ID;
    }

    public void setP_ID(int p_ID) {
        this.port_ID = port_ID;
    }

    public String getName() {
        return port_name;
    }

    public void setName(String name) {
        this.port_name = port_name;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(double storingCapacity) {
        if (storingCapacity > 0){
            this.storingCapacity = storingCapacity;
        }
    }

    public boolean getLandingAbility() {
        return landingAbility;
    }

    public double getCurrentStoring() {
        return currentStoring;
    }

    public void setCurrentStoring(double currentStoring) {
        this.currentStoring = currentStoring;
    }

    public ArrayList<Vehicle> getVehicleHangar() {
        return vehicleHangar;
    }

    public void setVehicleHangar(ArrayList<Vehicle> vehicleHangar) {
        this.vehicleHangar = vehicleHangar;
    }

    public ArrayList<Container> getContainerHangar() {
        return containerHangar;
    }

    public void setContainerHangar(ArrayList<Container> containerHangar) {
        this.containerHangar = containerHangar;
    }

    /*public ArrayList<GroupAssignment.Trip> getTripRecord() {
        return tripRecord;
    }

    public void setTripRecord(ArrayList<GroupAssignment.Trip> tripRecord) {
        this.tripRecord = tripRecord;
    }*/

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }


    public boolean vehicleDepature(){
        return true;
    }
    public void listVehicleInPort() {
        // Check if the ArrayList is empty
        if (vehicleHangar.isEmpty()) {
            System.out.println("There are no vehicles to be displayed");
            return;
        }

        // Print the header
        System.out.println("========================================LIST OF VEHICLES IN THE PORT==============================================");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("| vehicleID |       vehicleName        | vehicleType | currentFuel | fuelCapacity | carryingCapacity | currentPort |");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        // Iterate through the ArrayList and print each vehicle's information
        for (Vehicle vehicle : vehicleHangar) {
            System.out.printf("| %-10s| %-25s| %-12s| %-11.2f | %-12.2f | %-18.2f| %-11s|\n",
                    vehicle.getId(),
                    vehicle.getName(),
                    vehicle.getType(),
                    vehicle.getCurrentFuel(),
                    vehicle.getFuelCapacity(),
                    vehicle.getCarryingCapacity(),
                    vehicle.getCurrentPort());
        }

        // Print the footer
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }


    public void listContainerInPort() {
        // Check if the ArrayList is empty
        if (containerHangar.isEmpty()) {
            System.out.println("There are no containers to be displayed.");
            return;
        }

        // Print the header
        System.out.println("===========LIST OF CONTAINERS IN THE PORT===============");
        System.out.println("-------------------------------------------------------");
        System.out.println("| containerID |    containerType    | containerWeight |");
        System.out.println("-------------------------------------------------------");

        // Iterate through the ArrayList and print each container's information
        for (Container container : containerHangar) {
            System.out.printf("| %-11s | %-19s | %-15f |\n",
                    container.getId(),
                    container.getContainerType(),
                    container.getWeight());
        }

        // Print the footer
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }

    public boolean moveContainerToPortStorage(Container container){
        containerHangar.add(container);
        return true;
    }

    public boolean removeContainer(){
        return true;
    }

    public boolean vehicleDepart(){
        return true;
    }

    public boolean Container(){
        return true;
    }

    @Override
    public String toString() {
        return "Port{" +
                "port_ID = " + port_ID +
                ", port_name = '" + port_name + '\'' +
                ", X = " + X +
                ", Y = " + Y +
                ", storingCapacity = " + storingCapacity +
                ", landingAbility = " + landingAbility +
                '}';
    }
}
