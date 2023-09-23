package GroupAssignment.Vehicle;

import GroupAssignment.Container.Container;
import GroupAssignment.FilePaths.FilePaths;
import GroupAssignment.Port.Port;
import GroupAssignment.ScreenDisplay.SystemAdmin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vehicle {
    private static String ContainerFilePath = FilePaths.ContainerFilePath;
    private static String portFilePath = FilePaths.portFilePath;
    //A vehicle has a name, id, and a carrier which hold a number of containers
    private String name;
    private String id;

    private String type;
    private double carryingCapacity;
    private double currentFuel;
    private double fuelCapacity;

    private double totalWeight; //not an atrribute in the constructor
    private ArrayList<Container> carrier; //not an atrribute in the constructor

    private String currentPort;

    private double totalFuelConsumption;
    private Container unloadedContainer = null;

    public Vehicle(String id, String name, String type, double currentFuel, double fuelCapacity, double carryingCapacity, String currentPort) {
        //There is no parameter for carrier here. Every new vehicle has an empty carrier which is essentially an ArrayList
        this.id = id;
        this.name = name;
        this.type = type;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.currentPort = currentPort;
        this.totalWeight = 0;
        this.carrier = new ArrayList<Container>();
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

    public String getType() {
        return type;
    }

    public boolean setType(String type) {
        this.type = type;
        return true;
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

    public String getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
    }

    //If a container is found in the container list and the vehicle is still able to carry
    // the piles of container, it will add that container to the vehicle's carrier
    // I've made it so that it will not add the same container twice

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public ArrayList<Container> getCarrier() {
        return carrier;
    }

    //
    public boolean loadContainer() {
        return true;
    }


    //unloading the containers if there is one. Avoiding duplicate ID
    public boolean unloadContainer(){
        return true;
    }

    //Determining whether the vehicle can ship the containers to another port or not
    // Move the vehicle only if it is verified for transportation
    // I just made it moved to another port freely with no constraint. Update expected

    public boolean moveVehicle(Scanner scanner, String vehicleID) {
        return true;
    }
    public boolean refuelingVehicle(){
        return true;
    }
    //Display the general number of container
    public boolean displayGeneralNumberOfContainer(){
        System.out.println("Number of container: " + getCarrier().size());
        return true;
    }
    public boolean displayContainerReport(ArrayList<Container> containers) {
        // Create a map to count the occurrences of each container type
        HashMap<String, Integer> containerCounts = new HashMap<>();

        for (Container container : containers) {
            String containerType = container.getContainerType();
            containerCounts.put(containerType, containerCounts.getOrDefault(containerType, 0) + 1);
        }

        // Print the table header
        System.out.println("========CARRIER TABLE=========");
        System.out.println("-----------------------------------");
        System.out.printf("| %-13s | %-15s |%n", "ContainerType", "Count");
        System.out.println("-----------------------------------");

        // Print the container types and counts
        for (String containerType : containerCounts.keySet()) {
            int count = containerCounts.get(containerType);
            System.out.printf("| %-13s | %-15d |%n", containerType, count);
        }

        // Print the table footer
        System.out.println("-----------------------------------");
        return true;
    }

    protected static int extractPortNumber(String portId) {
        if (portId == null) {
            // Handle the case where portId is null (return a default value or handle as needed)
            return -1; // For example, return -1 to indicate an invalid port number
        }

        // Define a regular expression pattern to match the number after "p_"
        Pattern pattern = Pattern.compile("p_(\\d+)");

        // Create a Matcher object and apply the pattern to the input string
        Matcher matcher = pattern.matcher(portId);

        // Check if the pattern matches
        if (matcher.matches()) {
            // Extract the number group (group 1) from the matched result
            String numberStr = matcher.group(1);

            // Convert the extracted number string to an integer
            return Integer.parseInt(numberStr);
        } else {
            // Return -1 or throw an exception to indicate that the pattern did not match
            throw new IllegalArgumentException("Invalid port ID format: " + portId);
        }
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                " id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                ", type = '"  + type + '\'' +
                ", currentFuel = " + currentFuel +
                ", fuelCapacity = " + fuelCapacity +
                ", carryingCapacity = " + carryingCapacity +
                ", currentPort = " + currentPort +
                ", totalWeight = " + totalWeight +
                '}';
    }
}
