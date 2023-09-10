package ScreenDisplay;

import Port.Port;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;

public class SystemAdmin {
    private Port port;

    public boolean addPort(){
        // Let the user types in the ports information
        Scanner scanner = new Scanner(System.in);
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
            String fileName = "C:\\Users\\Admin\\IdeaProjects\\GroupProjectTeam14\\src\\Database\\Port.txt"; // Replace with the path to your existing text file.
            // Open the file in append mode by creating a FileOutputStream with the 'true' parameter
            FileWriter writer = new FileWriter(fileName, true);

            // Write the port's attributes as plain text
            writer.write("\n" + port.getP_ID()+", "+ port.getName()+", "+port.getX()+", "+ port.getY()+", "+ port.getStoringCapacity()+", "+ port.getLandingAbility());

            // Close the FileWriter
            writer.close();

            //Print the success message
            System.out.println("Object saved to " + fileName);
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
