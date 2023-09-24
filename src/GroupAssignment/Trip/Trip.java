package GroupAssignment.Trip;

import GroupAssignment.FilePaths.FilePaths;
import GroupAssignment.ScreenDisplay.SystemAdmin;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Scanner;

public class Trip {

    private static int tripCounter = 1;

    public static final String TripFilePath = FilePaths.TripFilePath; //show list from day A to day B and list trip in a day

    public static final String ShipFilePath = FilePaths.ShipFilePath;
    public static final String TruckFilePath = FilePaths.TruckFilePath;

    private String trip_id;
    protected static ArrayList<String> TruckNames = new ArrayList<>();
    protected static ArrayList<String> ShipNames = new ArrayList<>();

    public static List<Trip> trips = Trip.readTripsFromFile();


    private String vehicleName;


    private Date departureDate;


    private Date arrivalDate;

    private String dispatchingPort;


    private String destinationPort;

    private String status;

    public Trip() {
        readTruckNames();
        readShipNames();
        /*SystemAdmin.createTrucksTrip();
        SystemAdmin.createShipTrip();*/
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

    public static ArrayList<String> getTruckNames() {
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

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDispatchingPort() {
        return dispatchingPort;
    }

    public void setDispatchingPort(String dispatchingPort) {
        this.dispatchingPort = dispatchingPort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static List<Trip> readTripsFromFile() {
        List<Trip> trips = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(TripFilePath))) {
            String line;
            br.readLine(); // Read and ignore the header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Trip trip = new Trip();
                trip.setTripId(parts[0]);
                trip.setVehicleName(parts[1]);
                trip.setDepartureDate(dateFormat.parse(parts[2]));
                trip.setArrivalDate(dateFormat.parse(parts[3]));
                trip.setDispatchingPort(parts[4]);
                trip.setDestinationPort(parts[5]);
                trip.setStatus(parts[6]);
                trips.add(trip);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return trips;
    }

    public static List<Trip> listFromDatetoDateforSA(Scanner scanner1, Scanner scanner2) throws ParseException {
        List<Trip> tripsForPort = new ArrayList<>();
        List<Trip> historyDelete = new ArrayList<Trip>();
        Date currentDate = new Date();


        // Prompt the user for fromDate
        System.out.print("Enter fromDate (yyyy-MM-dd): ");
        String fromDateStr = scanner1.nextLine();

        // Prompt the user for toDate
        System.out.print("Enter toDate (yyyy-MM-dd): ");
        String toDateStr = scanner2.nextLine();

        // Parse user input to Date objects
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fromDate = dateFormat.parse(fromDateStr);
        Date toDate = dateFormat.parse(toDateStr);
        // Create a Calendar instance to work with dates
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);

        // Calculate the approximate fromDate by subtracting 3 days
        cal.add(Calendar.DATE, -3);
        Date approximateFromDate = cal.getTime();

        cal.setTime(toDate);

        // Calculate the approximate toDate by adding 3 days
        cal.add(Calendar.DATE, 3);
        Date approximateToDate = cal.getTime();

        for (Trip trip : trips) {
            Date tripDepartureDate = trip.getDepartureDate();
            Date tripArrivalDate = trip.getArrivalDate();
            String departurePort = trip.getDispatchingPort();
            String arrivalPort = trip.getDestinationPort();

            if (!tripDepartureDate.before(approximateFromDate) && !tripArrivalDate.after(approximateToDate)) {
                tripsForPort.add(trip);
                long currentDateMillis = System.currentTimeMillis(); // Get the current time in milliseconds
                long tripArrivalDateMillis = tripArrivalDate.getTime(); // Get the trip arrival time in milliseconds
                long dateDifference = currentDateMillis - tripArrivalDateMillis;
                if (dateDifference > 7 * 24 * 60 * 60 * 1000) { // 7 days in milliseconds
                    historyDelete.add(trip);
                    tripsForPort.remove(trip);
                    return historyDelete;
            }
        }

    }
        return tripsForPort;
    }

    public static List<Trip> listFromDateToDateForPM(int portCheck, Scanner scanner1, Scanner scanner2) throws ParseException {
        List<Trip> tripsForPort = new ArrayList<>();
        List<Trip> historyDelete = new ArrayList<Trip>();
        Date currentDate = new Date();

        // Prompt the user for fromDate
        System.out.print("Enter fromDate (yyyy-MM-dd): ");
        String fromDateStr = scanner1.nextLine();

        // Prompt the user for toDate
        System.out.print("Enter toDate (yyyy-MM-dd): ");
        String toDateStr = scanner2.nextLine();

        // Parse user input to Date objects
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fromDate = dateFormat.parse(fromDateStr);
        Date toDate = dateFormat.parse(toDateStr);
        // Create a Calendar instance to work with dates
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);

        // Calculate the approximate fromDate by subtracting 3 days
        cal.add(Calendar.DATE, -3);
        Date approximateFromDate = cal.getTime();

        cal.setTime(toDate);

        // Calculate the approximate toDate by adding 3 days
        cal.add(Calendar.DATE, 3);
        Date approximateToDate = cal.getTime();

        for (Trip trip : trips) {
            Date tripDepartureDate = trip.getDepartureDate();
            Date tripArrivalDate = trip.getArrivalDate();
            String departurePort = trip.getDispatchingPort();
            String arrivalPort = trip.getDestinationPort();

            if (!tripDepartureDate.before(approximateFromDate) && !tripArrivalDate.after(approximateToDate) &&
                    (departurePort.equals(" port" + portCheck) || arrivalPort.equals(" port" + portCheck))) {
                tripsForPort.add(trip);
                long currentDateMillis = System.currentTimeMillis(); // Get the current time in milliseconds
                long tripArrivalDateMillis = tripArrivalDate.getTime(); // Get the trip arrival time in milliseconds
                long dateDifference = currentDateMillis - tripArrivalDateMillis;
                if (dateDifference > 7 * 24 * 60 * 60 * 1000) { // 7 days in milliseconds
                    historyDelete.add(trip);
                    tripsForPort.remove(trip);
                    return historyDelete;
                }
            }
        }

        return tripsForPort;
    }
}