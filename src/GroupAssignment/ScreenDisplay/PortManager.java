package GroupAssignment.ScreenDisplay;

import GroupAssignment.FilePaths.FilePaths;
import GroupAssignment.Port.Port;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PortManager {
    public static final String ContainerFilePath = FilePaths.ContainerFilePath;
    public static final String ListingContainerFilePath = FilePaths.ListingContainerFilePath;
    public static final String portFilePath = FilePaths.portFilePath;


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
    public boolean removeContainerForPortManager() {
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
        } catch (FileNotFoundException e) {
            System.err.println("File not found. Check the file paths: " + ListingContainerFilePath + " and " + ContainerFilePath);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.err.println("An error occurred while reading or writing the files.");
            e.printStackTrace();
            return false;
        }
    }


}
