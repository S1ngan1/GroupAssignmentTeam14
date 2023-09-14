package ScreenDisplay;

import Port.Port;
import Container.Container;
import Vehicle.Vehicle;

import java.sql.SQLOutput;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;


public class SystemAdmin {
    private String ContainerFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Container.txt";
    private String portFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Port.txt";

    private String ShipFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Ship.txt";
    private String TruckFilePath = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\GroupAssignment\\Database\\Truck.txt";
    private Port port;

    private Container container;

    private Vehicle vehicle;

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
    public boolean addVehicle(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creating a new vehicle");
        System.out.print("Vehicle ID: ");
        String v_ID = scanner.nextLine();
        System.out.print("Vehicle Type: "); //not included in constructor
        String v_type = scanner.nextLine();
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

        vehicle = new Vehicle(v_ID, v_name, v_fuel, v_fc, v_cc, v_port);


        if("Ship".equals(v_type)){
            try {
                // Replace with the path to your existing text file.
                // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
                FileWriter writer = new FileWriter(ShipFilePath, true);

                // Write the ship attributes as plain text
                writer.write("\n"+ vehicle.getId()+", "+ vehicle.getName()+", "+vehicle.getCurrentFuel()+", "+vehicle.getFuelCapacity()+", "+vehicle.getCarryingCapacity()+", "+vehicle.getCurrentPort());

                // Close the FileWriter
                writer.close();

                //Print the success message
                System.out.println("Successfully created a new ship");
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        else if("Truck".equals(v_type)){
            try {
                // Replace with the path to your existing text file.
                // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
                FileWriter writer = new FileWriter(TruckFilePath, true);

                // Write the ship attributes as plain text
                writer.write("\n"+ vehicle.getId()+", "+ vehicle.getName()+", "+vehicle.getCurrentFuel()+", "+vehicle.getFuelCapacity()+", "+vehicle.getCarryingCapacity()+", "+vehicle.getCurrentPort());
                // Close the FileWriter
                writer.close();

                //Print the success message
                System.out.println("Successfully created a new truck");
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            System.out.println("Failed to create vehicle");
        }
        return true;
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
                FileWriter writer = new FileWriter(ContainerFilePath, true);

                // Write the container's attributes as plain text
                writer.write("\n"+ container.getId()+", "+ container.getContainerType()+", "+container.getWeight());

                // Close the FileWriter
                writer.close();

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
    public boolean calculateDistance(){
        Scanner scanner = new Scanner(System.in);

        // Variables to store longitude and latitude
        double longitude1 = 0.0;
        double latitude1 = 0.0;

        double longitude2 = 0.0;
        double latitude2 = 0.0;

        System.out.print("First port: ");
        String firstPort= scanner.nextLine();
        System.out.print("Second port: ");
        String secondPort = scanner.nextLine();


        longitude1 = getLongitude(firstPort, longitude1);
        longitude2 = getLongitude(secondPort, longitude2);
        latitude1 = getLatitude(firstPort, latitude1);
        latitude2 = getLatitude(secondPort, latitude2);

        double distance = Math.sqrt(Math.pow(longitude2 - longitude1, 2) + Math.pow(latitude2 - latitude1, 2));

        System.out.println("Distance between 2 ports: " + distance);

        return true;
    }
    public double getLongitude(String firstPort, double longitude){
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

    public double getLatitude(String firstPort, double latitude){
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
