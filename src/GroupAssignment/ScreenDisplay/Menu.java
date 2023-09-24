//Additional step for role: 0: SystemAdmin, 1: PortManager

package GroupAssignment.ScreenDisplay;

import java.util.*;
import java.io.*;
import java.util.Scanner;

import GroupAssignment.FilePaths.FilePaths;
import GroupAssignment.Port.Port;
import GroupAssignment.Trip.Trip;
import GroupAssignment.Vehicle.*;

import java.io.IOException;
import java.text.ParseException;
public class Menu {
    static ArrayList<Port> currentPortList = SystemAdmin.portList;

    public static void startMenu() throws IOException, InterruptedException, ParseException {
        System.out.println("================================");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println("Group: Group 14");
        System.out.println("S3976250, Ly Minh Phuc");
        System.out.println("S3966954, Tran Hoang Khiem");
        System.out.println("S3957050, Nguyen Tu Quoc Thai");
        Scanner scanner = new Scanner(System.in);

        boolean isAuthenticated = false;
        System.out.println("------------------LOGIN PAGE-------------------");

        while (!isAuthenticated) {
            //First thing a user see. 2 options to log in and an exit button

            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            Account authenticatedAccount = authenticateUser(username, password);

            if (authenticatedAccount != null) {
                int role = authenticatedAccount.getRole();
                if (role == 0) {
                    System.out.println("Login successfully.");
                    //Create a new SystemAdmin instance on the server
                    SystemAdmin admin = new SystemAdmin();
                    Trip trip = new Trip();
                    HashMap<String, Vehicle> currentVehicleList = SystemAdmin.vehicleList;

                    try {
                        int adminChoice; // Declare adminChoice outside the loop
                        //System Admin Dashboard
                        while (true) {
                            System.out.println("=======Welcome to CONTAINER PORT MANAGEMENT SYSTEM========");
                            System.out.println("Select an option:");
                            System.out.println("1. Add a new port");
                            System.out.println("2. Add a new vehicle");
                            System.out.println("3. Add a new container");
                            System.out.println("4. List all the port");
                            System.out.println("5. List all the container");
                            System.out.println("6. List all the vehicle");
                            System.out.println("7. Remove a port: ");
                            System.out.println("8. Remove a vehicle: ");
                            System.out.println("9. Remove a container: ");
                            System.out.println("10. Add a new trip for ship");
                            System.out.println("11. Add a new trip for truck");
                            System.out.println("12. List all the trips from day A to day B");
                            System.out.println("13. Calculate distance between 2 ports");
                            System.out.println("14. Calculate how much weight of each type of all containers");
                            System.out.println("15. Managing activities inside the system");
                            System.out.println("16. Back to the main system");
                            System.out.println("17. Exit");
                            adminChoice = scanner.nextInt(); // Read the choice here

                            switch (adminChoice) {
                                case 1:
                                    admin.addPort();
                                    break;
                                case 2:
                                    admin.addVehicle();
                                    break;
                                case 3:
                                    admin.addContainer();
                                    break;
                                case 4:
                                    admin.listPort();
                                    break;
                                case 5:
                                    admin.listContainer();
                                    break;
                                case 6:
                                    admin.listVehicle();
                                    break;
                                case 7:
                                    admin.removePort();
                                    break;
                                case 8:
                                    admin.removeVehicle();
                                    break;
                                case 9:
                                    admin.removeContainer();
                                    break;
                                case 10:
                                    SystemAdmin.createShipsTrip();
                                    break;
                                case 11:
                                    SystemAdmin.createTrucksTrip();
                                    break;
                                case 12:
                                    Scanner scanner1 = new Scanner(System.in);
                                    Scanner scanner2 = new Scanner(System.in);
                                    List<Trip> tripsForPort = Trip.listFromDatetoDateforSA(scanner1, scanner2);
                                    for (Trip tripss : tripsForPort) {
                                        System.out.println("Trip ID: " + tripss.getTripId());
                                        System.out.println("Departure Port: " + tripss.getDispatchingPort());
                                        System.out.println("Arrival Port: " + tripss.getDestinationPort());
                                        System.out.println("Departure Date: " + tripss.getDepartureDate());
                                        System.out.println("Arrival Date: " + tripss.getArrivalDate());
                                        // Print other trip details as needed
                                        System.out.println();
                                    }
                                    break;
                                case 13:
                                    admin.calculateDistance();
                                    break;
                                case 14:
                                    SystemAdmin.displayTotalContainerWeightByType(SystemAdmin.ContainerFilePath);
                                    break;
                                case 15:
                                    int navigateChoice;
                                    while (true) {
                                        System.out.println("=======SYSTEM NAVIGATING========");
                                        System.out.println("Select an option:");
                                        System.out.println("1. Port checking: ");
                                        System.out.println("2. Vehicle checking: ");
                                        System.out.println("3. Container checking ");
                                        System.out.println("4. Back to the previous page");
                                        System.out.println("5. Exit");
                                        navigateChoice = scanner.nextInt();

                                        switch (navigateChoice) {
                                            case 1:
                                                int portChoice;
                                                System.out.print("Select the port you want to check: ");
                                                portChoice = scanner.nextInt();
                                                scanner.nextLine();

                                                if (admin.checkPort(portChoice)) {
                                                    int portNChoice;
                                                    while (true) {
                                                        System.out.println("=======WELCOME TO PORT " + portChoice + "========");
                                                        System.out.println("Select an option:");
                                                        System.out.println("1. List all the container: ");
                                                        System.out.println("2. List all the vehicle: ");
                                                        System.out.println("3. Checking port status: ");
                                                        System.out.println("4. Back to the previous page ");
                                                        System.out.println("5. Exit ");
                                                        portNChoice = scanner.nextInt();

                                                        switch (portNChoice) {
                                                            case 1:
                                                                currentPortList.get(portChoice - 1).listContainerInPort();
                                                                break;
                                                            case 2:
                                                                currentPortList.get(portChoice - 1).listVehicleInPort();
                                                                break;
                                                            case 3:
                                                                System.out.println(currentPortList.get(portChoice - 1));
                                                                break;
                                                            case 4: //Back to the previous page
                                                                break;
                                                            case 5:
                                                                System.out.println("Exiting the program.");
                                                                scanner.close();
                                                                System.exit(0);
                                                                break;
                                                            default:
                                                                System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                                                        }
                                                        if (portNChoice == 4) {
                                                            break; // Exit the inner loop when the admin chooses to exit
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Port is not available");
                                                }
                                                break;

                                            case 2:
                                                int vehicleChoice;
                                                String vehicleType;
                                                System.out.print("Select the vehicle you want to check: ");
                                                vehicleChoice = scanner.nextInt();
                                                scanner.nextLine();
                                                System.out.print("Vehicle type: ");
                                                vehicleType = scanner.nextLine();

                                                if (admin.checkVehicle(vehicleChoice, vehicleType)) {
                                                    int vehicleNChoice;
                                                    while (true) {
                                                        System.out.println("=======WELCOME TO " + vehicleType.toUpperCase() + " " + vehicleChoice + " DASHB0ARD========");
                                                        System.out.println("Select an option:");
                                                        System.out.println("1. Load a container");
                                                        System.out.println("2. Unload a container");
                                                        System.out.println("3. Move to another port");
                                                        System.out.println("4. Refuel");
                                                        System.out.println("5. Checking vehicle status");
                                                        System.out.println("6. Showing number of container");
                                                        System.out.println("7. Showing number of container (each type)");
                                                        System.out.println("8. Back to the previous page");
                                                        System.out.println("9. Exit");
                                                        vehicleNChoice = scanner.nextInt();

                                                        String prefix;
                                                        if (vehicleChoice >= 300) {
                                                            prefix = "sh_";
                                                        } else if (vehicleChoice >= 100) {
                                                            prefix = "tr_";
                                                        } else {
                                                            System.out.println("Invalid input.");
                                                            return;
                                                        }

                                                        // Create the key by concatenating the prefix and user input
                                                        String key = prefix + vehicleChoice;

                                                        switch (vehicleNChoice) {
                                                            case 1:
                                                                // Retrieve the value associated with the key from the HashMap
                                                                currentVehicleList.get(key).loadContainer();
                                                                break;
                                                            case 2:
                                                                currentVehicleList.get(key).unloadContainer();
                                                                break;
                                                            case 3:
                                                                currentVehicleList.get(key).moveVehicle(scanner, currentVehicleList.get(key).getId());
                                                                break;
                                                            case 4:
                                                                currentVehicleList.get(key).refuelingVehicle();
                                                                break;
                                                            case 5:
                                                                System.out.println(currentVehicleList.get(key));
                                                                break;
                                                            case 6: //Back to the previous page
                                                                currentVehicleList.get(key).displayGeneralNumberOfContainer();
                                                                break;
                                                            case 7:
                                                                currentVehicleList.get(key).displayContainerReport(currentVehicleList.get(key).getCarrier());
                                                                break;
                                                            case 8:
                                                                break;
                                                            case 9:
                                                                System.out.println("Exiting the program.");
                                                                scanner.close();
                                                                System.exit(0);
                                                                break;
                                                            default:
                                                                System.out.println("Invalid choice. Please enter a number from 1 to 9.");
                                                        }
                                                        if (vehicleNChoice == 8) {
                                                            break; // Exit the inner loop when the admin chooses to exit
                                                        }
                                                    }
                                                } else {
                                                    System.out.println("Vehicle is not available");
                                                }
                                                break;
                                            case 3:
                                                break;
                                            case 4:  //back to the previous page
                                                break;
                                            case 5:
                                                System.out.println("Exiting the program.");
                                                scanner.close();
                                                System.exit(0);
                                                break;
                                            default:
                                                System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                                        }
                                        if (navigateChoice == 4) {
                                            break; // Exit the inner loop when the admin chooses to exit
                                        }
                                    }
                                    break;
                                case 16: //Back to the previous page
                                    break;
                                case 17:
                                    System.out.println("Exiting the program.");
                                    scanner.close();
                                    System.exit(0);
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please enter a number from 1 to 15.");
                            }

                            if (adminChoice == 16) {
                                break; // Exit the inner loop when the admin chooses to exit
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Invalid input format. Please enter a valid integer for the menu choice.");
                        // Handle the mismatch exception here, such as asking the user to try again.
                        scanner.nextLine(); // Consume the invalid input
                    }
                } else if (role == 1) {

                    PortManager portManager = new PortManager();
                    int portChoice = 0;
                    try {

                        System.out.print("Select the port you want to check: ");
                        portChoice = scanner.nextInt();
                        scanner.nextLine();

                        // Rest of your code for handling portChoice...
                    } catch (InputMismatchException e) {
                        System.err.println("Invalid input format. Please enter a valid integer for the port choice.");
                        // Handle the mismatch exception here, such as asking the user to try again.
                    }

                    if (portManager.checkPort(portChoice)) {
                        try {

                            int portNChoice;
                            while (true) {
                                System.out.println("=======WELCOME TO PORT " + portChoice + "========");
                                System.out.println("Select an option:");
                                System.out.println("1. List all the container: ");
                                System.out.println("2. List all the vehicle: ");
                                System.out.println("3. Remove a container: ");
                                System.out.println("4. Checking port status: ");
                                System.out.println("5. List all the trips from day A to day B");
                                System.out.println("6. Back to the previous page ");
                                System.out.println("7. Exit ");
                                portNChoice = scanner.nextInt();

                                switch (portNChoice) {
                                    case 1:
                                        break;
                                    case 2:
                                        currentPortList.get(portChoice - 1).listVehicleInPort();
                                        break;
                                    case 3:
                                        portManager.removeContainerForPortManager();
                                        break;
                                    case 4:
                                        System.out.println(currentPortList.get(portChoice - 1));
                                        break;
                                    case 5:
                                        Scanner scanner1 = new Scanner(System.in);
                                        Scanner scanner2 = new Scanner(System.in);
                                        List<Trip> tripsForPort = Trip.listFromDateToDateForPM(portChoice, scanner1, scanner2);
                                        for (Trip trip : tripsForPort) {
                                            System.out.println("Trip ID: " + trip.getTripId());
                                            System.out.println("Departure Port: " + trip.getDispatchingPort());
                                            System.out.println("Arrival Port: " + trip.getDestinationPort());
                                            System.out.println("Departure Date: " + trip.getDepartureDate());
                                            System.out.println("Arrival Date: " + trip.getArrivalDate());
                                            // Print other trip details as needed
                                            System.out.println();
                                        }
                                        break;
                                    case 6: //Back to the previous page
                                        break;
                                    case 7:
                                        System.out.println("Exiting the program.");
                                        scanner.close();
                                        System.exit(0);
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please enter a number from 1 to 7.");
                                }
                                if (portNChoice == 6) {
                                    break; // Exit the inner loop when the admin chooses to exit
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Invalid input format. Please enter a valid integer for the menu choice.");
                            // Handle the mismatch exception here, such as asking the user to try again.
                            scanner.nextLine(); // Consume the invalid input
                        }
                    } else {
                        System.out.println("Invalid role.");
                    }
                } else {
                    System.out.println("Authentication failed. Invalid username or password.");
                }
            }
        }
    }

    //ADDITIONAL MENU METHODS
    private static Account authenticateUser(String username, String password) {
        List<Account> accountList = readAccountsFromFile(FilePaths.accountFilePath);

        for (Account account : accountList) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account; // Authentication successful
            }
        }
        return null; // Authentication failed
    }

    private static ArrayList<Account> readAccountsFromFile(String fileName) {
        ArrayList<Account> accountList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true; // Flag to identify the first line (header)
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }

                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String roleString = parts[2]; // Read role as a string

                    // Check if the roleString is numeric before parsing it
                    if (isNumeric(roleString)) {
                        int role = Integer.parseInt(roleString);
                        Account account = new Account(username, password, role);
                        accountList.add(account);
                    } else {
                        System.err.println("Invalid role value: " + roleString);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accountList;
    }


    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

