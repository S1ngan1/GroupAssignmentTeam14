package GroupAssignment.Vehicle;
import GroupAssignment.Container.Container;
import GroupAssignment.FilePaths.FilePaths;
import GroupAssignment.ScreenDisplay.SystemAdmin;
import java.util.InputMismatchException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Ship extends Vehicle {

    private static final String ContainerFilePath = FilePaths.ContainerFilePath;
    private double totalFuelConsumption;
    private double totalWeight = 0;

    private double currentPortStoringCapacity = SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getStoringCapacity();

    private ArrayList<Container> carrier = new ArrayList<Container>(); //not an atrribute in the constructor

    private Container unloadedContainer = null;

    private double shipInitialFuelConsumption = 1; //Ship consumes 1 gallons per km
    public Ship(String id, String name, String type, double currentFuel, double fuelCapacity, double carryingCapacity, String currentPort) {
        super(id, name, type, currentFuel, fuelCapacity, carryingCapacity, currentPort);
    }

    public double getShipInitialFuelConsumption() {
        return shipInitialFuelConsumption;
    }
    @Override
    public double getTotalWeight() {
        return totalWeight;
    }
    @Override
    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    @Override
    public ArrayList<Container> getCarrier() {
        return carrier;
    }

    public void setCarrier(ArrayList<Container> carrier) {
        this.carrier = carrier;
    }

    @Override
    public boolean loadContainer() {
        // If container totalWeight + container weight <= carryingCapacity
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the container to load");
        System.out.print("Container ID: ");
        int ID;

        try {
            ID = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            System.err.println("Invalid input format. Please enter a valid integer for Container ID.");
            return false; // Return false in case of an exception
        }

        String searchID = Integer.toString(ID); // Replace with the ID you want to search for
        // Read data from the text file
        ArrayList<String> lines = new ArrayList<>();
        boolean firstLine = true; // Flag to track the first line (header)
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ContainerFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    // Keep the first line (header)
                    lines.add(line);
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",\\s+"); // Use regex to split by comma and optional spaces
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String containerType = parts[1];
                    double weight = Double.parseDouble(parts[2]);

                    Container container = new Container(id, containerType, weight);

                    // Check if the current container matches the search ID & the vehicle is still able to carry the next container
                    if (id.equals(searchID) && totalWeight + weight <= getCarryingCapacity()) {
                        carrier.add(container); // Add the found container to the list
                        if(SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getP_ID() != null){
                            SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getContainerHangar().remove(container);
                        }

                        found = true;
                        System.out.println("Container added ");
                        totalWeight += weight;
                        currentPortStoringCapacity -= weight;
                        shipInitialFuelConsumption += Container.getShip_Container().get(container.getContainerType());
                    } else {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ContainerFilePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false; // Return false if writing fails
            }
        } else {
            System.out.println("Failed to add container");
            return false; // Return false if the container is not found or cannot be added
        }

        return found; // Return true if the container is successfully loaded
    }
    @Override
    public boolean unloadContainer() {
        // Telling the ID of the container to be removed
        Scanner scanner = new Scanner(System.in);
        System.out.print("Unloading container ID: ");
        int ID;

        try {
            ID = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } catch (InputMismatchException e) {
            System.err.println("Invalid input format. Please enter a valid integer for Container ID.");
            return false; // Return false in case of an exception
        }

        String searchID = Integer.toString(ID); // Replace with the ID you want to search for
        boolean successfullyUnloaded = false;

        // Check if the carrier is not empty
        if (!carrier.isEmpty()) {
            Iterator<Container> iterator = carrier.iterator();
            while (iterator.hasNext()) {
                Container container = iterator.next();
                if (container.getId().equals(searchID)) {
                    unloadedContainer = container;
                    if(SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getP_ID() != null){
                        SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getContainerHangar().add(container);
                    }
                    totalWeight -= container.getWeight(); //THE TOTAL WEIGHT OF THE CARRIER DECREASES EVERYTIME THERE IS A NEW CONTAINER UNLOADED
                    currentPortStoringCapacity += container.getWeight();
                    shipInitialFuelConsumption -= Container.getShip_Container().get(container.getContainerType()); //THE TOTAL FUEL CONSUMPTION DECREASES
                    iterator.remove();
                    successfullyUnloaded = true;
                    break;
                }
            }

            // Check if a container was found and unloaded
            if (unloadedContainer != null) {
                if (Math.abs(totalWeight) < 1e-10) {
                    totalWeight = 0.0;
                }
                // Write the container information into container.txt
                try {
                    // Open the file in append mode by creating a FileWriter with the 'true' parameter
                    FileWriter writer = new FileWriter(ContainerFilePath, true);

                    // Write the container's attributes as plain text
                    writer.write("\n" + unloadedContainer.getId() + ", " + unloadedContainer.getContainerType() + ", " + unloadedContainer.getWeight());

                    // Close the FileWriter
                    writer.close();

                    System.out.println("Container unloaded.");
                } catch (IOException e) {
                    e.printStackTrace();
                    successfullyUnloaded = false;
                }
            } else {
                System.out.println("Container with ID " + searchID + " not found in the carrier.");
            }
        } else {
            System.out.println("There are no containers to be unloaded.");
        }

        return successfullyUnloaded;
    }
    @Override
    public boolean moveVehicle(Scanner scanner, String vehicleID) {
        boolean ableToUnload = true;
        System.out.print("Move to port number (enter number only): ");

        int destinationPort;

        try {
            destinationPort = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Invalid input format. Please enter a valid integer for the destination port.");
            return false; // Return false in case of an exception
        }


        for (Container container : carrier) {
            //WE HAVE 3 CONDITIONS FOR A TRANSPORTATION OF SHIP TO EXIST:
            //1: THE VEHICLE MUST BE ABLE TO UNLOAD AT LEAST 1 CONTAINER AT THE ARRIVAL PORT
            //2: THE LANDING ABILITY BETWEEN TWO PORTS MUST BE DIFFERENT
            //3: THE CURRENT FUEL OF THE SHIP MUST BE ABOVE 0


            if (container.getWeight() + SystemAdmin.portList.get(destinationPort - 1).getCurrentStoring() > SystemAdmin.portList.get(destinationPort - 1).getStoringCapacity() && getCurrentFuel() <= 0 && (!SystemAdmin.portList.get(destinationPort-1).getLandingAbility() || SystemAdmin.portList.get(extractPortNumber(super.getCurrentPort())-1).getLandingAbility())) {
                ableToUnload = false;
            }
        }

        if(!ableToUnload){
            System.out.println("Cannot transport the vehicle to the designated port");
            return false;
        }
        else{
            setCurrentFuel(super.getCurrentFuel() - (SystemAdmin.calculateDistanceWithParameter(SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getP_ID(), SystemAdmin.portList.get(destinationPort - 1).getP_ID()) * shipInitialFuelConsumption));
            System.out.println("Vehicle moved to port "+destinationPort);
            SystemAdmin.portList.get(destinationPort - 1).getVehicleHangar().add(SystemAdmin.vehicleList.get(vehicleID));
            SystemAdmin.portList.get(extractPortNumber(getCurrentPort())-1).getVehicleHangar().remove(SystemAdmin.vehicleList.get(vehicleID));
            setCurrentPort(SystemAdmin.portList.get(destinationPort - 1).getP_ID());
            return true;
        }
    }
    @Override
    public boolean refuelingVehicle(){
        setCurrentFuel(getFuelCapacity());
        return true;
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
