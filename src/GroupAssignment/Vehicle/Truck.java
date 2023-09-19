package GroupAssignment.Vehicle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

import GroupAssignment.Container.Container;
import GroupAssignment.Port.Port;
import GroupAssignment.ScreenDisplay.SystemAdmin;
import GroupAssignment.Vehicle.Vehicle;

public class Truck extends Vehicle {
    private static final String ContainerFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Container.txt";
    private String type;

    private double totalWeight; //not an atrribute in the constructor
    private ArrayList<Container> carrier; //not an atrribute in the constructor
    private String currentPort; //not included in constructor

    private Container unloadedContainer = null;
    public Truck (String name, String id, String type, double currentFuel, double fuelCapacity, double carryingCapacity, String currentPort ){
        super(name, id, type, carryingCapacity, currentFuel, fuelCapacity, currentPort);
        this.totalWeight = 0;
        this.carrier = new ArrayList<Container>();
        setType(type);
    }

    public String getType() {
        if (setType(this.type)){
            return type;
        }
        return "Enter the proper truck type";
    }

    public boolean setType(String type) {
        String[] availableTypes = {"Basic", "Reefer", "Tanker"};
        boolean matched = false;
        for (String checkType : availableTypes) {
            if (checkType.equalsIgnoreCase(type)) {
                matched = true;
                break;
            }
        }
        // Set the truck type if it is valid
        if (matched) {
            this.type = type;
        }
        return matched;
    }

    @Override
    public boolean loadContainer() {
        // If container totalWeight + container weight <= carryingCapacity
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the container to load");
        System.out.print("Container ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();

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
                    if (id.equals(searchID) && isContainerTypeAllowed(containerType) && totalWeight + weight <= super.getCarryingCapacity()) {
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
        int ID = scanner.nextInt();
        scanner.nextLine();

        String searchID = Integer.toString(ID);
        boolean successfullyUnloaded = false;

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

        return successfullyUnloaded;
    }

    // Helper method to check if the container type is allowed for this truck
    private boolean isContainerTypeAllowed(String containerType) {
        if (type.equals("Basic")) {
            return containerType.equals("Open Top") || containerType.equals("Open Side") || containerType.equals("Dry Storage");
        } else if (type.equals("Reefer")) {
            return containerType.equals("Refrigerated");
        } else if (type.equals("Tanker")) {
            return containerType.equals("Liquid");
        } else {
            return false; // Handle other truck types as needed
        }
    }

    @Override
    public boolean moveVehicle(Scanner scanner) {
        System.out.print("Move to port number: ");
        int destinationPort = scanner.nextInt();
        scanner.nextLine();

        for (Container container : carrier) {
            if (container.getWeight() + SystemAdmin.portList.get(destinationPort - 1).getCurrentStoring() <= SystemAdmin.portList.get(destinationPort - 1).getStoringCapacity() && (SystemAdmin.portList.get(destinationPort-1).getLandingAbility() && SystemAdmin.portList.get(extractPortNumber(super.getCurrentPort())-1).getLandingAbility())){
                setCurrentPort(SystemAdmin.portList.get(destinationPort - 1).getP_ID());
                System.out.println("Vehicle moved to port "+destinationPort);
                return true;
            }
        }
        System.out.println("Cannot transport the vehicle to the designated port");
        return false;
    }

    private static int extractPortNumber(String portId) {
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
        return "Truck{" +
                " id = '" + super.getId() + '\'' +
                ", name = '" + super.getName()+ '\'' +
                ", type= '" + type + '\'' +
                ", currentFuel = " + super.getCurrentFuel() +
                ", fuelCapacity = " + super.getFuelCapacity() +
                ", carryingCapacity = " + super.getCarryingCapacity() +
                ", currentPort = " + super.getCurrentPort() +
                ", totalWeight = " + totalWeight +
                '}';
    }
}


