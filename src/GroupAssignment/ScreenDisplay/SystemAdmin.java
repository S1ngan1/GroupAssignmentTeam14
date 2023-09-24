package GroupAssignment.ScreenDisplay;

import GroupAssignment.Interface.*;
import GroupAssignment.Port.Port;
import GroupAssignment.Container.Container;
import GroupAssignment.Trip.Trip;
import GroupAssignment.Vehicle.*;
import GroupAssignment.FilePaths.FilePaths;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class SystemAdmin implements AddingForSystemAdmin, RemovingForSystemAdmin, ListingForSystemAdmin, CheckingForSystemAdmin {
    private static int tripCounter = 1;

    public static final String ContainerFilePath = FilePaths.ContainerFilePath;
    public static final String ListingContainerFilePath = FilePaths.ListingContainerFilePath;
    public static final String portFilePath = FilePaths.portFilePath;

    public static final String ShipFilePath = FilePaths.ShipFilePath;
    public static final String TruckFilePath = FilePaths.TruckFilePath;

    public static final String TripFilePath = FilePaths.TripFilePath;
    public static final String Port1Trip = FilePaths.Port1Trip;
    public static final String Port2Trip = FilePaths.Port2Trip;
    public static final String Port3Trip = FilePaths.Port3Trip;
    public static final String Port4Trip = FilePaths.Port4Trip;
    public static final String Port5Trip = FilePaths.Port5Trip;


    private static double totalFuelUsedInADay;
    private Port port;

    private Container container;

    private Vehicle vehicle;

    public static ArrayList<Port> portList = loadPorts();

    public static HashMap<String, Vehicle> vehicleList = loadVehicles();

    public ArrayList<Port> getPortList() {
        return portList;
    }

    public static ArrayList<String> shipNames = Trip.getShipNames();
    public static ArrayList<String> truckNames = Trip.getTruckNames();

    public void setPortList(ArrayList<Port> portList) {
        this.portList = portList;
    }

    public HashMap<String, Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(HashMap<String, Vehicle> vehicleList) {
        SystemAdmin.vehicleList = vehicleList;
    }

    //LOAD AND SAVE METHODS
    private static ArrayList<Port> loadPorts() {
        ArrayList<Port> ports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            boolean firstLine = true; // Add this flag to skip the first line
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line (header)
                }
                String[] parts = line.split(", ");
                String port_ID = parts[0];
                String port_name = parts[1];
                double X = Double.parseDouble(parts[2]);
                double Y = Double.parseDouble(parts[3]);
                double storingCapacity = Double.parseDouble(parts[4]);
                boolean landingAbility = Boolean.parseBoolean(parts[5]);
                ports.add(new Port(port_ID, port_name, X, Y, storingCapacity, landingAbility));
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., file not found or invalid data)
            e.printStackTrace();
        }
        return ports;
    }

    private static void savePorts(ArrayList<Port> ports) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(portFilePath))) {
            for (Port port : ports) {
                writer.println(port.getP_ID() + ", " + port.getName() + ", " + port.getX() + ", " + port.getY()
                        + ", " + port.getStoringCapacity() + ", " + port.getLandingAbility());
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., unable to write to the file)
            e.printStackTrace();
        }
    }

/*
    private static ArrayList<String> readShipNamesFromFile(String filePath) {
        ArrayList<String> shipNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header
                }

                // Split the line by comma (assuming it's CSV-like)
                String[] parts = line.split(", ");

                // Check if there are enough parts and the ship name is not null
                if (parts.length >= 2 && parts[1] != null && !parts[1].isEmpty()) {
                    // Add the ship name to the ArrayList
                    shipNames.add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shipNames;
    }

    public static ArrayList<String> readTruckNamesFromFile(String filePath) {
        ArrayList<String> truckNames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header
                }

                // Split the line by comma (assuming it's CSV-like)
                String[] parts = line.split(", ");

                // Check if there are enough parts and the truck name is not null
                if (parts.length >= 2 && parts[1] != null && !parts[1].isEmpty()) {
                    // Add the truck name to the ArrayList
                    truckNames.add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return truckNames;
    }
*/

    private static HashMap<String, Vehicle> loadVehicles() {
        HashMap<String, Vehicle> vehicles = new HashMap<>();

        // Load ship data
        loadVehicleData(ShipFilePath, vehicles);

        // Load truck data
        loadVehicleData(TruckFilePath, vehicles);

        return vehicles;
    }

    // Load vehicle data from a data file and populate the HashMap
    private static void loadVehicleData(String filePath, HashMap<String, Vehicle> vehicles) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // Flag to skip the header row
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header row
                }

                String[] parts = line.split(",\\s*");
                if (parts.length == 7) {
                    String vehicleID = parts[0].trim();
                    String vehicleName = parts[1].trim();
                    String vehicleType = parts[2].trim();
                    double currentFuel = Double.parseDouble(parts[3].trim());
                    double fuelCapacity = Double.parseDouble(parts[4].trim());
                    double carryingCapacity = Double.parseDouble(parts[5].trim());
                    String currentPort = parts[6].trim();

                    // Check if currentPort is null or empty and handle accordingly
                    if (currentPort == null || currentPort.isEmpty()) {
                        // Handle the case where currentPort is missing or empty
                        // You can set a default value or handle it as needed for your application
                        // For example, you can set it to "UNKNOWN_PORT"
                        currentPort = "UNKNOWN_PORT";
                    }

                    if (vehicleType.equalsIgnoreCase("ship")) {
                        Ship ship = new Ship(vehicleID, vehicleName, vehicleType, currentFuel, fuelCapacity, carryingCapacity, currentPort);
                        vehicles.put(vehicleID, ship);
                    } else if (vehicleType.equalsIgnoreCase("Tanker") || vehicleType.equalsIgnoreCase("Reefer") || vehicleType.equalsIgnoreCase("Basic"))  {
                        Truck truck = new Truck(vehicleID, vehicleName, vehicleType, currentFuel, fuelCapacity, carryingCapacity, currentPort);
                        vehicles.put(vehicleID, truck);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Save vehicles to the data files
    private static void saveVehicles(HashMap<String, Vehicle> vehicles) {
        // Separate vehicles into ship and truck data
        HashMap<String, Vehicle> ships = new HashMap<>();
        HashMap<String, Vehicle> trucks = new HashMap<>();

        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle instanceof Ship) {
                ships.put(vehicle.getId(), vehicle);
            } else if (vehicle instanceof Truck) {
                trucks.put(vehicle.getId(), vehicle);
            }
        }

        // Save ship data
        saveVehicleData(ShipFilePath, ships);

        // Save truck data
        saveVehicleData(TruckFilePath, trucks);
    }

    // Save vehicle data to a data file
    private static void saveVehicleData(String filePath, HashMap<String, Vehicle> vehicles) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Vehicle vehicle : vehicles.values()) {
                writer.println(vehicle.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //ADDING METHODS
    public boolean addPort(){
        // Let the user types in the ports information
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new port");
        System.out.print("Port ID: ");
        String port_ID = scanner.nextLine();
        System.out.print("Port name: ");
        String port_name = scanner.nextLine();
        System.out.print("Longitude: ");
        double X = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Latitude: ");
        double Y = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Storing capacity: ");
        double storingCapacity = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Landing ability: ");
        boolean landingAbility = scanner.nextBoolean();

        //Create an instance of a port with the provided ports information as attributes
        port = new Port(port_ID, port_name, X, Y, storingCapacity, landingAbility);
        portList.add(port);


        //Write the ports information into port.txt
        try {
             // Replace with the path to your existing text file.
            // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
            FileWriter writer = new FileWriter(portFilePath, true);

            // Write the port's attributes as plain text
            writer.write("\n" + port.getP_ID()+", "+ port.getName()+", "+port.getX()+", "+ port.getY()+", "+ port.getStoringCapacity()+", "+ port.getLandingAbility());

            // Close the FileWriter
            writer.close();

            //Print the success message
            System.out.println("Successfully created a new port");
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new vehicle");
        System.out.print("Vehicle ID (e.g., sh_number or tr_number): ");
        String v_ID = scanner.nextLine();
        System.out.print("Vehicle Type (Ship or Truck): ");
        String v_type = scanner.nextLine();

        // Validate the input format using a regular expression
        if (!Pattern.matches("^(sh|tr)_\\d+$", v_ID)) {
            System.err.println("Invalid input format for the vehicle ID. Please use 'sh_number' for ships or 'tr_number' for trucks.");
            return false;
        }

        // Check if the ID and type match
        if (v_ID.startsWith("sh") && !"Ship".equalsIgnoreCase(v_type)) {
            System.err.println("Failed to create a vehicle. ID indicates a ship, but type is not 'Ship'.");
            return false;
        } else if (v_ID.startsWith("tr") && !"Truck".equalsIgnoreCase(v_type)) {
            System.err.println("Failed to create a vehicle. ID indicates a truck, but type is not 'Truck'.");
            return false;
        }

        System.out.print("Vehicle Name: ");
        String v_name = scanner.nextLine();
        System.out.print("Current Fuel: ");
        double v_fuel = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Fuel Capacity: ");
        double v_fc = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Carrying Capacity: ");
        double v_cc = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Current Port (null if sailaway): ");
        String v_port = scanner.nextLine();

        if ("Ship".equalsIgnoreCase(v_type)) {
            Ship ship = new Ship(v_ID, v_name, v_type, v_fuel, v_fc, v_cc, v_port);
            try {
                // Replace with the path to your existing text file.
                // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
                FileWriter writer = new FileWriter(ShipFilePath, true);

                // Write the ship attributes as plain text
                writer.write("\n" + ship.getId() + ", " + ship.getName() + ", " + "Ship"+ ", "+ ship.getCurrentFuel() + ", " + ship.getFuelCapacity() + ", " + ship.getCarryingCapacity() + ", " + ship.getCurrentPort());

                // Close the FileWriter
                writer.close();

                // Print the success message
                System.out.println("Successfully created a new ship");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to create a new ship.");
                return false;
            }
        } else if ("Truck".equalsIgnoreCase(v_type)) {
            System.out.print("Truck Type (Basic, Reefer, Tanker): ");
            String truckType = scanner.nextLine();

            Truck truck = new Truck(v_ID, v_name, truckType, v_fuel, v_fc, v_cc, v_port);

            // Validate the truck type using the setTruckType method
            if (!truck.setType(truckType)) {
                System.err.println("Invalid truck type. Please specify 'Basic', 'Reefer', or 'Tanker'.");
                return false;
            }

            try {
                // Replace with the path to your existing text file.
                // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
                FileWriter writer = new FileWriter(TruckFilePath, true);

                // Write the truck attributes as plain text
                writer.write("\n" + truck.getId() + ", " + truck.getName() + ", " + truck.getType() + ", " + truck.getCarryingCapacity()+ ", " + truck.getCurrentFuel()+ ", " + truck.getFuelCapacity()+ ", " + truck.getCurrentPort());

                // Close the FileWriter
                writer.close();

                // Print the success message
                System.out.println("Successfully created a new truck");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to create a new truck.");
                return false;
            }
        } else {
            System.err.println("Invalid vehicle type. Please specify 'Ship' or 'Truck'.");
            return false;
        }
    }


    public boolean addContainer(){
        // Add container information
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new container");
        System.out.print("Container ID: ");
        String c_ID = scanner.nextLine();
        System.out.print("Container Type: ");
        String c_type = scanner.nextLine();
        System.out.print("Weight: ");
        double c_weight = scanner.nextDouble();
        scanner.nextLine();

        container = new Container(c_ID, c_type, c_weight);

        if(container.setContainerType(c_type)){
            try {
                // Replace with the path to your existing text file.
                // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
                FileWriter writer1 = new FileWriter(ContainerFilePath, true);

                // Write the container's attributes as plain text
                writer1.write("\n"+ container.getId()+", "+ container.getContainerType()+", "+container.getWeight());

                // Close the FileWriter
                writer1.close();


                FileWriter writer2 = new FileWriter(ListingContainerFilePath, true);

                // Write the container's attributes as plain text
                writer2.write("\n"+ container.getId()+", "+ container.getContainerType()+", "+container.getWeight());

                // Close the FileWriter
                writer2.close();

                //Print the success message
                System.out.println("Successfully created a new container");
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        else {
            System.out.println("Failed to create a container");
        }
        return true;
    }

    public static void createShipsTrip() {
        Scanner scanner = new Scanner(System.in);
        String shipId = null;

        boolean isShipIdValid = false;
        while (!isShipIdValid) {
            System.out.print("Enter Ship ID (sh_number): ");
            shipId = scanner.nextLine();

            isShipIdValid = isShipIdValid(shipId);

            if (!isShipIdValid) {
                System.out.println("Invalid ship ID. Please enter a valid ship ID.");
            }
        }

        String shipName = null;
        boolean isValidShipName = false;

        while (!isValidShipName) {
            System.out.print("Enter Ship Name: ");
            shipName = scanner.nextLine();

            boolean isShipNameMatched = isShipNameMatched(shipId, shipName);

            // Check if the entered truck name exists in TruckNames
            if (isShipNameMatched && shipNames.contains(" " + shipName)) {
                isValidShipName = true;
            } else {
                System.out.println("Ship is not suitable. Please enter a valid ship name.");
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate = null;
        Date arrivalDate = null;

        try {
            System.out.print("Enter Departure Date (yyyy-MM-dd): ");
            departureDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter Arrival Date (yyyy-MM-dd): ");
            arrivalDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String departurePort = null;
        String arrivalPort = null;
        boolean arePortsValid = false;

        while (!arePortsValid) {
            System.out.print("Enter Departure Port (e.g., p_1): ");
            departurePort = scanner.nextLine();

            System.out.print("Enter Arrival Port (e.g., p_4): ");
            arrivalPort = scanner.nextLine();

            if (departurePort.equals(arrivalPort)) {
                System.out.println("Departure and Arrival Ports must be different.");
            } else {
                // Determine the landing ability based on departure and arrival ports
                boolean canLand = determineLandingAbilityForShip(departurePort, arrivalPort);

                if (canLand) {
                    arePortsValid = true; // Ports are valid, exit the loop
                } else {
                    System.out.println("Unable to create the trip. Landing ability mismatch. Please re-enter ports.");
                }
            }
        }

        String shipStatus = null;
        boolean isValidShipStatus = false;

        while (!isValidShipStatus) {
            System.out.print("Enter Ship Status (Ongoing/Completed/Getting Goods): ");
            shipStatus = scanner.nextLine();

            // Check if the entered status is valid (you can add more checks as needed)
            if (shipStatus.equalsIgnoreCase("Ongoing") ||
                    shipStatus.equalsIgnoreCase("Completed") ||
                    shipStatus.equalsIgnoreCase("Getting Goods")) {
                isValidShipStatus = true;
            } else {
                System.out.println("Invalid Ship Status. Please enter a valid status.");
            }
        }
        String tripId = "T" + tripCounter++;
        // Create a trip entry string
        String tripEntry = String.format("%s, %s, %s, %s, %s, %s", tripId, shipName, dateFormat.format(departureDate),
                departurePort, arrivalPort, shipStatus);

        // Write the trip entry to the departure port-specific file
        appendTripEntryToFile(tripEntry, getPortTripFilePath(departurePort));

        // Write the trip entry to the arrival port-specific file
        appendTripEntryToFile(tripEntry, getPortTripFilePath(arrivalPort));

        // Write the trip entry to the main trip file
        appendTripEntryToFile(tripEntry, TripFilePath);

        System.out.println("Ship trip created successfully!");
    }

    public static void createTrucksTrip() {
        Scanner scanner = new Scanner(System.in);
        String truckId = null;

        boolean isTruckIdValid = false;
        while (!isTruckIdValid) {
            System.out.print("Enter Truck ID (tr_number): ");
            truckId = scanner.nextLine();

            isTruckIdValid = isTruckIdValid(truckId);

            if (!isTruckIdValid) {
                System.out.println("Invalid Truck ID. Please enter a valid truck ID.");
            }
        }

        String truckName = null;
        boolean isValidTruckName = false;

        while (!isValidTruckName) {
            System.out.print("Enter Truck Name: ");
            truckName = scanner.nextLine();

            boolean isTruckNameMatched = isTruckNameMatched(truckId, truckName);

            // Check if the entered truck name exists in TruckNames
            if (isTruckNameMatched && truckNames.contains(" " + truckName)) {
                isValidTruckName = true;
            } else {
                System.out.println("Truck is not suitable. Please enter a valid truck name.");
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date departureDate = null;
        Date arrivalDate = null;

        try {
            System.out.print("Enter Departure Date (yyyy-MM-dd): ");
            departureDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter Arrival Date (yyyy-MM-dd): ");
            arrivalDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String departurePort = null;
        String arrivalPort = null;
        boolean arePortsValid = false;

        while (!arePortsValid) {
            System.out.print("Enter Departure Port (e.g., p_1): ");
            departurePort = scanner.nextLine();

            System.out.print("Enter Arrival Port (e.g., p_4): ");
            arrivalPort = scanner.nextLine();

            if (departurePort.equals(arrivalPort)) {
                System.out.println("Departure and Arrival Ports must be different.");
            } else {
                // Determine the landing ability based on departure and arrival ports
                boolean canLand = determineLandingAbilityForTruck(departurePort, arrivalPort);

                if (canLand) {
                    arePortsValid = true; // Ports are valid, exit the loop
                } else {
                    System.out.println("Unable to create the trip. Landing ability mismatch. Please re-enter ports.");
                }
            }
        }

        String truckStatus = null;
        boolean isValidTruckStatus = false;

        while (!isValidTruckStatus) {
            System.out.print("Enter Truck Status (Ongoing/Completed/Getting Goods): ");
            truckStatus = scanner.nextLine();

            // Check if the entered status is valid (you can add more checks as needed)
            if (truckStatus.equalsIgnoreCase("Ongoing") ||
                    truckStatus.equalsIgnoreCase("Completed") ||
                    truckStatus.equalsIgnoreCase("Getting Goods")) {
                isValidTruckStatus = true;
            } else {
                System.out.println("Invalid Truck Status. Please enter a valid status.");
            }
        }

        // Create a trip entry string
        String tripEntry = String.format("T1, %s, %s, %s, %s, %s", truckName, dateFormat.format(departureDate),
                departurePort, arrivalPort, truckStatus);

        // Write the trip entry to the departure port-specific file
        appendTripEntryToFile(tripEntry, getPortTripFilePath(departurePort));

        // Write the trip entry to the arrival port-specific file
        appendTripEntryToFile(tripEntry, getPortTripFilePath(arrivalPort));

        // Write the trip entry to the main trip file
        appendTripEntryToFile(tripEntry, TripFilePath);

        System.out.println("Truck trip created successfully!");
    }


    private static String getPortTripFilePath(String port) {
        // Generate the port-specific trip file path based on the port ID
        switch (port) {
            case "p_1":
                return Port1Trip;
            case "p_2":
                return Port2Trip;
            case "p_3":
                return Port3Trip;
            case "p_4":
                return Port4Trip;
            case "p_5":
                return Port5Trip;
            default:
                return "";
        }
    }

    private static void appendTripEntryToFile(String tripEntry, String filePath) {
        // Append the trip entry to the specified file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(tripEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTruckIdValid(String truckId) {
        // Read the truck data from your file and check if the provided truck ID exists
        try (BufferedReader reader = new BufferedReader(new FileReader(TruckFilePath))) {
            String line;
            boolean skipFirstLine = true; // Skip the header line
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] truckData = line.split(",");
                String id = truckData[0].trim();
                if (id.equals(truckId)) {
                    return true; // Found a match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Truck ID not found
    }
    private static boolean isShipIdValid(String shipId) {
        // Read the truck data from your file and check if the provided truck ID exists
        try (BufferedReader reader = new BufferedReader(new FileReader(ShipFilePath))) {
            String line;
            boolean skipFirstLine = true; // Skip the header line
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] shipData = line.split(",");
                String id = shipData[0].trim();
                if (id.equals(shipId)) {
                    return true; // Found a match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Ship ID not found
    }

    // Helper method to check if the provided truck name matches the truck name associated with the truck ID
    private static boolean isTruckNameMatched(String truckId, String truckName) {
        // Read the truck data from your file and check if the provided truck name matches the truck name associated with the truck ID
        try (BufferedReader reader = new BufferedReader(new FileReader(TruckFilePath))) {
            String line;
            boolean skipFirstLine = true; // Skip the header line
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] truckData = line.split(",");
                String id = truckData[0].trim();
                String name = truckData[1].trim();
                if (id.equals(truckId) && name.equals(truckName)) {
                    return true; // Found a match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Truck name doesn't match the truck ID
    }

    private static boolean isShipNameMatched(String shipId, String shipName) {
        // Read the truck data from your file and check if the provided truck name matches the truck name associated with the truck ID
        try (BufferedReader reader = new BufferedReader(new FileReader(ShipFilePath))) {
            String line;
            boolean skipFirstLine = true; // Skip the header line
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] truckData = line.split(",");
                String id = truckData[0].trim();
                String name = truckData[1].trim();
                if (id.equals(shipId) && name.equals(shipName)) {
                    return true; // Found a match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Ship name doesn't match the truck ID
    }
    private static boolean determineLandingAbilityForShip(String departurePort, String arrivalPort) {
        // Define landing abilities for your ports
        HashMap<String, Boolean> landingAbilities = new HashMap<>();
        //WILL CHANGE SO THAT IT GET THE INFORMATION FROM THE PORT FILES INSTEAD OF CREATING THEM MANUALLY
        landingAbilities.put("p_1", true);
        landingAbilities.put("p_2", true);
        landingAbilities.put("p_3", false);
        landingAbilities.put("p_4", true);
        landingAbilities.put("p_5", true);

        // Get the landing abilities for the specified ports
        Boolean departureLandingAbility = landingAbilities.get(departurePort);
        Boolean arrivalLandingAbility = landingAbilities.get(arrivalPort);

        // Compare the landing abilities of the two ports
        return departureLandingAbility != null && arrivalLandingAbility != null && !departureLandingAbility.equals(arrivalLandingAbility);
    }

    private static boolean determineLandingAbilityForTruck(String departurePort, String arrivalPort) {
        // Define landing abilities for your ports
        HashMap<String, Boolean> landingAbilities = new HashMap<>();
        //WILL CHANGE SO THAT IT GET THE INFORMATION FROM THE PORT FILES INSTEAD OF CREATING THEM MANUALLY
        landingAbilities.put("p_1", true);
        landingAbilities.put("p_2", true);
        landingAbilities.put("p_3", false);
        landingAbilities.put("p_4", true);
        landingAbilities.put("p_5", true);

        // Get the landing abilities for the specified ports
        Boolean departureLandingAbility = landingAbilities.get(departurePort);
        Boolean arrivalLandingAbility = landingAbilities.get(arrivalPort);

        // Compare the landing abilities of the two ports
        return departureLandingAbility != null && arrivalLandingAbility != null && departureLandingAbility.equals(arrivalLandingAbility);
    }
    // ... Other class members ..

    //REMOVING PURPOSE
    public boolean removePort(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the port_ID to delete (e.g., p_number): ");
        String portIDToDelete = scanner.nextLine();

        // Validate the input format using a regular expression
        if (!Pattern.matches("^p_\\d+$", portIDToDelete)) {
            System.err.println("Invalid input format. Please use the format 'p_number' (e.g., p_1).");
            return false;
        }

        try {
            // Read the file into memory while excluding the lines with the specified port_ID
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(portFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length > 0 && !parts[0].equals(portIDToDelete)) {
                    lines.add(line);
                }
            }
            reader.close();

            // Write the remaining lines back to the file, effectively deleting the specified port
            FileWriter writer = new FileWriter(portFilePath, false);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

            System.out.println("Port with port_ID " + portIDToDelete + " deleted successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to delete the port.");
            return false;
        }
    }
    public boolean removeContainer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ContainerID to delete: ");
        String containerIDToDelete = scanner.nextLine();

        // Validate the input format using a regular expression
        if (!Pattern.matches("^\\d+$", containerIDToDelete)) {
            System.err.println("Invalid input format. Please use only digits for ContainerID.");
            return false;
        }

        try {
            // Read the file into memory while excluding the lines with the specified ContainerID
            ArrayList<String> linesForListingPurpose = new ArrayList<>();
            ArrayList<String> linesForContainer = new ArrayList<>();
            BufferedReader readerForListingPurpose = new BufferedReader(new FileReader(ListingContainerFilePath));
            BufferedReader readerForContainer = new BufferedReader(new FileReader(ContainerFilePath));

            String lineForListingPurpose;
            String lineForContainer;

            boolean containerFound = false; // Flag to track if the container exists

            while ((lineForListingPurpose = readerForListingPurpose.readLine()) != null) {
                String[] parts = lineForListingPurpose.split(", ");
                if (parts.length > 0 && !parts[0].equals(containerIDToDelete)) {
                    linesForListingPurpose.add(lineForListingPurpose);
                } else {
                    containerFound = true;
                }
            }

            while ((lineForContainer = readerForContainer.readLine()) != null) {
                String[] parts = lineForContainer.split(", ");
                if (parts.length > 0 && !parts[0].equals(containerIDToDelete)) {
                    linesForContainer.add(lineForContainer);
                }
            }

            readerForListingPurpose.close();
            readerForContainer.close();

            // If the container was not found, show an error message and return false
            if (!containerFound) {
                System.err.println("Container with ContainerID " + containerIDToDelete + " not found.");
                return false;
            }

            // Write the remaining lines back to the respective files, effectively deleting the specified container
            FileWriter writerForListingPurpose = new FileWriter(ListingContainerFilePath, false);
            FileWriter writerForContainer = new FileWriter(ContainerFilePath, false);

            for (String updatedLine : linesForListingPurpose) {
                writerForListingPurpose.write(updatedLine + "\n");
            }

            for (String updatedLine : linesForContainer) {
                writerForContainer.write(updatedLine + "\n");
            }

            writerForListingPurpose.close();
            writerForContainer.close();

            System.out.println("Container with ContainerID " + containerIDToDelete + " deleted successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to delete the container.");
            return false;
        }
    }

    public boolean removeVehicle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the vehicle ID to delete: ");
        String vehicleIDToDelete = scanner.nextLine();

        // Validate the input format using a regular expression
        if (!Pattern.matches("^(sh|tr)_\\d+$", vehicleIDToDelete)) {
            System.err.println("Invalid input format. Please use the format 'sh_number' or 'tr_number'.");
            return false;
        }

        String fileName = null;

        if (vehicleIDToDelete.startsWith("sh_")) {
            fileName = ShipFilePath;
        } else if (vehicleIDToDelete.startsWith("tr_")) {
            fileName = TruckFilePath;
        } else {
            System.err.println("Invalid vehicle type. Please use 'sh' for ship or 'tr' for truck.");
            return false;
        }

        try {
            // Read the file into memory while excluding the lines with the specified vehicle ID
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            boolean vehicleFound = false; // Flag to track if the vehicle exists

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length > 0 && !parts[0].equals(vehicleIDToDelete)) {
                    lines.add(line);
                } else {
                    vehicleFound = true;
                }
            }
            reader.close();

            // If the vehicle was not found, show an error message and return false
            if (!vehicleFound) {
                System.err.println("Vehicle with ID " + vehicleIDToDelete + " not found.");
                return false;
            }

            // Write the remaining lines back to the file, effectively deleting the specified vehicle
            FileWriter writer = new FileWriter(fileName, false);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

            System.out.println("Vehicle with ID " + vehicleIDToDelete + " deleted successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to delete the vehicle.");
            return false;
        }
    }

    //LISTING METHODS
    public boolean listPort() {
        try {
            // Open the file for reading.
            BufferedReader reader = new BufferedReader(new FileReader(portFilePath));

            // Define column widths for formatting.
            int idWidth = 7;
            int nameWidth = 17;
            int longitudeWidth = 14;
            int latitudeWidth = 13;
            int capacityWidth = 16;
            int landingAbilityWidth = 15;

            // Print the header row.
            System.out.println("===========================================LIST OF PORTS=============================================");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+longitudeWidth+"s | %-"+latitudeWidth+"s | %-"+capacityWidth+"s | %-"+landingAbilityWidth+"s |\n", "port_ID", "port_name", "longitude", "latitude", "storageCapacity", "landingAbility");
            System.out.println("-----------------------------------------------------------------------------------------------------");

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line
                }

                String[] parts = line.split(", ");

                if (parts.length == 6) {
                    String portID = parts[0];
                    String portName = parts[1];
                    String longitude = parts[2];
                    String latitude = parts[3];
                    String storageCapacity = parts[4];
                    String landingAbility = parts[5];

                    // Print each row of port information.
                    System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+longitudeWidth+"s | %-"+latitudeWidth+"s | %-"+capacityWidth+"s | %-"+landingAbilityWidth+"s |\n", portID, portName, longitude, latitude, storageCapacity, landingAbility);
                }
            }

            // Print the footer.
            System.out.println("-----------------------------------------------------------------------------------------------------");

            // Close the file.
            reader.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean listVehicle() {
        try {
            // Display the header
            System.out.println("==============================================LIST OF VEHICLES====================================================");
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.println("| vehicleID |       vehicleName        | vehicleType | currentFuel | fuelCapacity | carryingCapacity | currentPort |");
            System.out.println("------------------------------------------------------------------------------------------------------------------");

            // Read and display ship data from "ship.txt"
            BufferedReader shipReader = new BufferedReader(new FileReader(ShipFilePath));
            String shipLine;
            boolean skipShipHeader = true; // Flag to skip the header
            while ((shipLine = shipReader.readLine()) != null) {
                if (skipShipHeader) {
                    skipShipHeader = false;
                    continue; // Skip the header line
                }
                String[] shipParts = shipLine.split(", ");
                if (shipParts.length >= 7) {
                    String shipID = shipParts[0];
                    String shipName = shipParts[1];
                    String shipType = shipParts[2];
                    String currentFuel = shipParts[3];
                    String fuelCapacity = shipParts[4];
                    String carryingCapacity = shipParts[5];
                    String currentPort = shipParts[6];

                    System.out.printf("| %-9s | %-24s | %-9s | %-11s | %-12s | %-16s | %-11s |%n",
                            shipID, shipName, shipType, currentFuel, fuelCapacity, carryingCapacity, currentPort);
                }
            }
            shipReader.close();

            // Read and display truck data from "truck.txt"
            BufferedReader truckReader = new BufferedReader(new FileReader(TruckFilePath));
            String truckLine;
            boolean skipTruckHeader = true; // Flag to skip the header
            while ((truckLine = truckReader.readLine()) != null) {
                if (skipTruckHeader) {
                    skipTruckHeader = false;
                    continue; // Skip the header line
                }
                String[] truckParts = truckLine.split(", ");
                if (truckParts.length >= 7) {
                    String truckID = truckParts[0];
                    String truckName = truckParts[1];
                    String truckType = truckParts[2];
                    String currentFuel = truckParts[3];
                    String fuelCapacity = truckParts[4];
                    String carryingCapacity = truckParts[5];
                    String currentPort = truckParts[6];

                    System.out.printf("| %-9s | %-24s | %-9s | %-11s | %-12s | %-16s | %-11s |%n",
                            truckID, truckName, truckType, currentFuel, fuelCapacity, carryingCapacity, currentPort);
                }
            }
            truckReader.close();

            // Display the bottom horizontal line
            System.out.println("------------------------------------------------------------------------------------------------------------------");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to list vehicles.");
            return false;
        }
    }
    public boolean listContainer() {
        try {

            // Open the file for reading.
            BufferedReader reader = new BufferedReader(new FileReader(ListingContainerFilePath));

            // Define column widths for formatting.10

            int idWidth = 12;
            int typeWidth = 15;
            int weightWidth = 9;

            // Print the header row.
            System.out.println("=============LIST OF CONTAINERS===============");
            System.out.println();
            System.out.println("----------------------------------------------");
            System.out.printf("| %-"+idWidth+"s | %-"+typeWidth+"s | %-"+weightWidth+"s |\n", "ContainerID", "ContainerType", "Weight");
            System.out.println("----------------------------------------------");

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line
                }

                String[] parts = line.split(", ");

                if (parts.length == 3) {
                    String containerID = parts[0];
                    String containerType = parts[1];
                    String weight = parts[2];

                    // Print each row of container information.
                    System.out.printf("| %-"+idWidth+"s | %-"+typeWidth+"s | %-"+weightWidth+"s |\n", containerID, containerType, weight);
                }
            }

            // Print the footer.
            System.out.println("----------------------------------------------");

            // Close the file.
            reader.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void displayTotalContainerWeightByType(String filePath) {
        HashMap<String, Double> containerTypeToTotalWeight = new HashMap<>();

        // Read data from the specified file and calculate total weight for each type
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String containerType = parts[1];
                    double weight = Double.parseDouble(parts[2]);

                    // Update total weight for the container type
                    containerTypeToTotalWeight.put(
                            containerType,
                            containerTypeToTotalWeight.getOrDefault(containerType, 0.0) + weight
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the formatted output
        System.out.println("===WEIGHT OF EACH CONTAINER TYPE===");
        System.out.println("-----------------------------------");
        System.out.println("| ContainerType   | Total Weight  |");
        System.out.println("-----------------------------------");

        for (HashMap.Entry<String, Double> entry : containerTypeToTotalWeight.entrySet()) {
            String containerType = entry.getKey();
            double totalWeight = entry.getValue();
            System.out.printf("| %-15s | %-13.2f |\n", containerType, totalWeight);
        }

        System.out.println("----------------------------------------------");
    }

    @Override
    public boolean listTripInAGivenDay() {
        return false;
    }

    @Override
    public boolean listTripBetweenTwoDays() {
        return false;
    }

    public boolean checkPort(int portChoice){
        try (BufferedReader reader = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 6) {
                    String portID = parts[0];
                    if (portID.equals("p_" + portChoice)) {
                        String portName = parts[1];
                        double longitude = Double.parseDouble(parts[2]);
                        double latitude = Double.parseDouble(parts[3]);
                        int storageCapacity = Integer.parseInt(parts[4]);
                        boolean landingAbility = Boolean.parseBoolean(parts[5]);

                        Port port = new Port(portID, portName, longitude, latitude, storageCapacity, landingAbility);
                        return true; // Exit the program after finding the port
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data from the file.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkVehicle(int number, String vehicleType) {
        String filePath = "";

        if (vehicleType.equalsIgnoreCase("ship")) {
            filePath = ShipFilePath;
        } else if (vehicleType.equalsIgnoreCase("truck")) {
            filePath = TruckFilePath;
        } else {
            System.err.println("Invalid vehicle type.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");

                if (vehicleType.equalsIgnoreCase("ship") && parts.length == 7) {
                    // Handle ship data
                    if (parts[0].trim().equals("sh_" + number)) {
                        String shipID = parts[0].trim();
                        String shipName = parts[1].trim();
                        String shipType = parts[2].trim();
                        double currentFuel = Double.parseDouble(parts[3].trim());
                        double fuelCapacity = Double.parseDouble(parts[4].trim());
                        double carryingCapacity = Double.parseDouble(parts[5].trim());
                        String currentPort = parts[6].trim();

                        // Create a Ship object if needed
                        Ship ship = new Ship(shipID, shipName, shipType, currentFuel, fuelCapacity, carryingCapacity, currentPort);

                        // You can do something with the ship object if found
                        return true;
                    }
                } else if (vehicleType.equalsIgnoreCase("truck") && parts.length == 7) {
                    // Handle truck data
                    if (parts[0].trim().equals("tr_" + number)) {
                        String truckID = parts[0].trim();
                        String truckName = parts[1].trim();
                        String truckType = parts[2].trim();
                        double currentFuel = Double.parseDouble(parts[3].trim());
                        double fuelCapacity = Double.parseDouble(parts[4].trim());
                        double carryingCapacity = Double.parseDouble(parts[5].trim());
                        String currentPort = parts[6].trim();

                        // Create a Truck object if needed
                        Truck truck = new Truck(truckID, truckName, truckType, currentFuel, fuelCapacity, carryingCapacity, currentPort);

                        // You can do something with the truck object if found
                        return true;
                    }
                }
            }

            // Handle case where the vehicle is not found
            System.err.println("Vehicle with ID " + number + " not found.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data from the file.");
            e.printStackTrace();
            return false;
        }
    }
    public static double calculateDistanceWithParameter(String firstPort, String secondPort){
        double longitude1 = 0.0;
        double latitude1 = 0.0;

        double longitude2 = 0.0;
        double latitude2 = 0.0;

        longitude1 = getLongitude(firstPort, longitude1);
        longitude2 = getLongitude(secondPort, longitude2);
        latitude1 = getLatitude(firstPort, latitude1);
        latitude2 = getLatitude(secondPort, latitude2);

        double distance = Math.sqrt(Math.pow(longitude2 - longitude1, 2) + Math.pow(latitude2 - latitude1, 2));

        return distance;
    }
    public static boolean calculateDistance(){
        Scanner scanner = new Scanner(System.in);

        // Variables to store longitude and latitude
        double longitude1 = 0.0;
        double latitude1 = 0.0;

        double longitude2 = 0.0;
        double latitude2 = 0.0;

        System.out.print("First port (p_number): ");
        String firstPort= scanner.nextLine();
        System.out.print("Second port (p_number): ");
        String secondPort = scanner.nextLine();


        longitude1 = getLongitude(firstPort, longitude1);
        longitude2 = getLongitude(secondPort, longitude2);
        latitude1 = getLatitude(firstPort, latitude1);
        latitude2 = getLatitude(secondPort, latitude2);

        double distance = Math.sqrt(Math.pow(longitude2 - longitude1, 2) + Math.pow(latitude2 - latitude1, 2));

        System.out.println("Distance between 2 ports: " + distance);

        return true;
    }
    private static double getLongitude(String firstPort, double longitude){
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].equals(firstPort.trim())) {
                    // Found a matching port_ID
                    try {
                        longitude = Double.parseDouble(parts[2]);
                        //latitude = Double.parseDouble(parts[3]);
                        found = true;
                        break;  // Exit the loop after finding the match
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing longitude");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Check if the values were found
        if (found) {
            return longitude;
        }
        else {
            System.out.println("Port_ID not found or invalid data.");
        }

        return longitude;
    }

    private static double getLatitude(String firstPort, double latitude){
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(portFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].equals(firstPort.trim())) {
                    // Found a matching port_ID
                    try {
                        //longitude = Double.parseDouble(parts[2]);
                        latitude = Double.parseDouble(parts[3]);
                        found = true;
                        break;  // Exit the loop after finding the match
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing longitude");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Check if the values were found
        if (found) {
            return latitude;
        }
        else {
            System.out.println("Port_ID not found or invalid data.");
        }
        return latitude;
    }
}
