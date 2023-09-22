package GroupAssignment.Trip;

import GroupAssignment.FilePaths.FilePaths;
import java.io.*;
import java.util.ArrayList;

public class Trip {

    public static final String TripFilePath = FilePaths.TripFilePath; //show list from day A to day B and list trip in a day

    public static final String ShipFilePath = FilePaths.ShipFilePath;
    public static final String TruckFilePath = FilePaths.TruckFilePath;

    private String trip_id;
    private static ArrayList<String> TruckNames = new ArrayList<>();
    private static ArrayList<String> ShipNames = new ArrayList<>();

    public Trip(String trip_id) {
        this.trip_id = trip_id;
        readTruckNames();
        readShipNames();
    }

    private void readTruckNames() {

        boolean skipFirstLine = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(TruckFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] vehicleData = line.split(",");
                TruckNames.add(vehicleData[1]);

            }
        } catch (IOException e) {
            // Handle IOException
        }
    }


    private void readShipNames() {
        boolean skipFirstLine = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(ShipFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] vehicleData = line.split(",");
                ShipNames.add(vehicleData[1]);

            }
        } catch (IOException e) {
            // Handle IOException
        }
    }



    public String getTripId() {
        return trip_id;
    }

    public void setTripId(String trip_id) {
        this.trip_id = trip_id;
    }

    public  static ArrayList<String> getTruckNames() {
        return TruckNames;
    }

    public void setTruckNames(ArrayList<String> TruckNames) {
        Trip.TruckNames = TruckNames;
    }

    public static ArrayList<String> getShipNames() {
        return ShipNames;
    }

    public void setShipNames(ArrayList<String> ShipNames) {
        Trip.ShipNames = ShipNames;
    }
}