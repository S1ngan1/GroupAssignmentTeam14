package GroupAssignment.Vehicle;

import GroupAssignment.Container.Container;
import GroupAssignment.Port.Port;
import GroupAssignment.ScreenDisplay.SystemAdmin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Vehicle {
    private static String ContainerFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Container.txt";
    private static String portFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Port.txt";
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

    public boolean loadContainer() {
        // If container totalWeight + container weight <= carryingCapacity
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the container to load");
        System.out.print("Container ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();

        String searchID = Integer.toString(ID); // Replace with the ID you want to search for
        boolean found = false;

        // Read data from the text file
        ArrayList<String> lines = new ArrayList<>();
        boolean firstLine = true; // Flag to track the first line (header)

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
                    if (id.equals(searchID) && totalWeight + container.getWeight() <= carryingCapacity) {
                        carrier.add(container); // Add the found container to the list
                        found = true;
                        System.out.println("Container added ");
                        totalWeight += weight;
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
            ArrayList<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                if (!line.startsWith(searchID + ",")) {
                    updatedLines.add(line);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ContainerFilePath))) {
                for (String line : updatedLines) {
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

        return true; // Return true if the container is successfully loaded
    }


    //unloading the containers if there is one. Avoiding duplicate ID
    public boolean unloadContainer() {
        // Telling the ID of the container to be removed
        Scanner scanner = new Scanner(System.in);
        System.out.print("Unloading container ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();

        String searchID = Integer.toString(ID);
        boolean successfullyUnloaded = false;

        // Check if the carrier is not empty
        if (!carrier.isEmpty()) {
            Iterator<Container> iterator = carrier.iterator();
            while (iterator.hasNext()) {
                Container container = iterator.next();
                if (container.getId().equals(searchID)) {
                    unloadedContainer = container;
                    totalWeight -= container.getWeight();
                    iterator.remove(); // Remove the container
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


    public Port createPortFromID(String currentPort) {

        try (BufferedReader reader = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 6 && parts[0].equals(currentPort)) {
                    // Parse the data from the line
                    String id = parts[0];
                    String name = parts[1];
                    double longitude = Double.parseDouble(parts[2]);
                    double latitude = Double.parseDouble(parts[3]);
                    int storageCapacity = Integer.parseInt(parts[4]);
                    boolean landingAbility = Boolean.parseBoolean(parts[5]);

                    // Create and return a new Port instance
                    Port port = new Port(id, name, longitude, latitude, storageCapacity, landingAbility);

                    // Initialize the ContainerHangar as an empty ArrayList
                    port.setContainerHangar(new ArrayList<Container>());

                    return port;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the portID is not found, return null or handle the error accordingly
        return null;
    }


    //Determining whether the vehicle can ship the containers to another port or not
    // Move the vehicle only if it is verified for transportation
    // I just made it moved to another port freely with no constraint. Update expected

    public boolean moveVehicle(Scanner scanner) {
        System.out.print("Move to port number: ");
        int destinationPort = scanner.nextInt();
        scanner.nextLine();

        for (Container container : carrier) {
            if (container.getWeight() + SystemAdmin.portList.get(destinationPort - 1).getCurrentStoring() <= SystemAdmin.portList.get(destinationPort - 1).getStoringCapacity()) {
                setCurrentPort(SystemAdmin.portList.get(destinationPort - 1).getP_ID());
                System.out.println("Vehicle moved to port "+destinationPort);
                return true;
            }
        }
        System.out.println("Cannot transport the vehicle to the designated port");
        return false;
    }
    //Display the general number of container
    public String displayGeneralNumberOfContainer(){
        return "Number of container: " + carrier.size();
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
