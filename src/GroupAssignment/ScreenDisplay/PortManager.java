package GroupAssignment.ScreenDisplay;

import GroupAssignment.FilePaths.FilePaths;
import GroupAssignment.Port.Port;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PortManager {
    public static final String ContainerFilePath = FilePaths.ContainerFilePath;
    public static final String ListingContainerFilePath = FilePaths.ListingContainerFilePath;
    public static final String portFilePath = FilePaths.portFilePath;

    public static final String ShipFilePath = FilePaths.ShipFilePath;
    public static final String TruckFilePath = FilePaths.TruckFilePath;

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

}
