package Vehicle;

import Container.Container;
import Port.Port;

import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle {
    //A vehicle has a name, id, and a carrier which hold a number of containers
    private String name;
    private String id;
    private double carryingCapacity;
    private double currentFuel;
    private double fuelCapacity;

    private double totalWeight;
    private ArrayList<Container> carrier;

    private ArrayList<Container> unloadedContainers;
    private Port currentPort;
    public Vehicle(String name, String id, double carryingCapacity, double currentFuel, double fuelCapacity){
        //There is no parameter for carrier here. Every new vehicle has an empty carrier which is essentially an ArrayList
        this.name = name;
        this.id = id;
        this.carryingCapacity = carryingCapacity;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.carrier = new ArrayList<Container>();
        this.unloadedContainers = new ArrayList<Container>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    //Add a new container to the vehicle's carrier
    public boolean loadContainer(){
        if(totalWeight <= carryingCapacity){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Container type: ");
            String containerType = scanner.nextLine();
            System.out.print("Container ID: ");
            int containerID = scanner.nextInt();
            System.out.print("Container weight: ");
            double containerWeight = scanner.nextDouble();

            Container container = new Container(containerType, containerID, containerWeight);

            carrier.add(container);
            return true;
        }
        else{
            System.out.println("Exceed the vehicle carrying capacity!");
            return false;
        }

    }

    //Determining whether the vehicle can ship the containers to another port or not
    public boolean approvalForTransport(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter vehicle ID: ");
        String vID = scanner.nextLine();

        /*The next step is to open and read the truck or ship.txt base on the first letter of the ID
          Then it checks the user input ID with the ID available in the text file, then it will check the capacity bla bla */
        return true;
    }

    // Move the vehicle only if it is verified for transportation
    public int moveVehicle(){
        if (approvalForTransport()){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Move to port number: ");
            int destinationPort = scanner.nextInt();
            scanner.nextLine();

            return destinationPort; //Record the destination port number for further usage
        }
        else{
            System.out.println("Unable to transport the vehicle");
            return 0;
        }
    }

    //Unload a container from the vehicle
    public boolean unloadContainer(){
        if(carrier.size() > 0){
            Container unloadedContainer = carrier.remove(carrier.size()-1);
            unloadedContainers.add(unloadedContainer);
            return true;
        }
        else{
            System.out.println("There is no container to unload");
            return false;
        }

    }
    //Display the general number of container
    public String displayGeneralNumberOfContainer(){
        return "Number of container: " + carrier.size();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                " name = '" + name + '\'' +
                ", id = " + id +
                '}';
    }
}
