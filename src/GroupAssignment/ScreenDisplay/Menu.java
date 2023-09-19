package GroupAssignment.ScreenDisplay;

import java.util.*;
import java.io.*;
import java.util.Scanner;

import GroupAssignment.ScreenDisplay.Account;
import GroupAssignment.ScreenDisplay.Menu;
import GroupAssignment.ScreenDisplay.SystemAdmin;
import GroupAssignment.Port.Port;
import GroupAssignment.Vehicle.Vehicle;
import GroupAssignment.Vehicle.Ship;
import GroupAssignment.Vehicle.Truck;

import java.io.IOException;
import java.text.ParseException;
public class Menu {


    public void mainMenu() throws IOException, InterruptedException, ParseException {
        System.out.println("================================");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println("Group: Group 14");
        System.out.println("S3976250, Ly Minh Phuc");
        System.out.println("S3966954, Tran Hoang Khiem");
        System.out.println("S3957050, Nguyen Tu Quoc Thai");
    }
    public void startMenu(){
        Scanner scanner = new Scanner(System.in);

        //First thing a user see. 2 options to log in and an exit button
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Login as a System Admin");
            System.out.println("2. Login as a Port Manager");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            //1: SystemAdmin, 2: PortManager, 3:Exit
            switch (choice) {
                //Logged in as a System Admin
                case 1:
                    //Enter the username and password, then the Account.authenticateUser() method will check the file of
                    //existed account to see if you have input the correct username and password. If not then you will have to retype
                    boolean stillLoggedIn = true;
                    while (stillLoggedIn) {
                        Scanner userScanner = new Scanner(System.in);
                        System.out.print("Enter username: ");
                        String inputUserName = userScanner.nextLine();
                        System.out.print("Enter password: ");
                        String inputPassword = userScanner.nextLine();

                        if (Account.authenticateUser(inputUserName, inputPassword)) {
                            System.out.println("Login successfully.");
                            //Create a new SystemAdmin instance on the server
                            SystemAdmin admin = new SystemAdmin();
                            ArrayList<Port> currentPortList = SystemAdmin.portList;
                            HashMap<String, Vehicle> currentVehicleList = SystemAdmin.vehicleList;
                            stillLoggedIn = false;

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
                                System.out.println("10. List all the trip from day A to day B or in any given day");
                                System.out.println("11. Calculate distance between 2 ports");
                                System.out.println("12. Managing activities inside the system");
                                System.out.println("13. Back to the main system");
                                System.out.println("14. Exit");
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
                                        break;
                                    case 11:
                                        admin.calculateDistance();
                                        break;
                                    case 12:
                                        int navigateChoice;
                                        while(true) {
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

                                                    if(admin.checkPort(portChoice)){
                                                        int portNChoice;
                                                        while(true){
                                                            System.out.println("=======WELCOME TO PORT "+portChoice+"========");
                                                            System.out.println("Select an option:");
                                                            System.out.println("1. List all the container: ");
                                                            System.out.println("2. List all the vehicle: ");
                                                            System.out.println("3. Change the attribute: ");
                                                            System.out.println("4. Back to the previous page ");
                                                            System.out.println("5. Exit ");
                                                            portNChoice = scanner.nextInt();

                                                            switch(portNChoice){
                                                                case 1:
                                                                    break;
                                                                case 2:
                                                                    break;
                                                                case 3:
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
                                                    }
                                                    else{
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

                                                    if(admin.checkVehicle(vehicleChoice, vehicleType)){
                                                        int vehicleNChoice;
                                                        while(true) {
                                                            System.out.println("=======WELCOME TO "+vehicleType.toUpperCase() +" "+ vehicleChoice + " DASHB0ARD========");
                                                            System.out.println("Select an option:");
                                                            System.out.println("1. Load a container");
                                                            System.out.println("2. Unload a container");
                                                            System.out.println("3. Move to another port");
                                                            System.out.println("4. Refuel");
                                                            System.out.println("5. Back to the previous page");
                                                            System.out.println("6. Exit");
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

                                                            switch(vehicleNChoice){
                                                                case 1:
                                                                    // Retrieve the value associated with the key from the HashMap
                                                                    currentVehicleList.get(key).loadContainer();
                                                                    System.out.println(currentVehicleList.get(key));
                                                                    break;
                                                                case 2:
                                                                    currentVehicleList.get(key).unloadContainer();
                                                                    System.out.println(currentVehicleList.get(key));

                                                                    break;
                                                                case 3:
                                                                    break;
                                                                case 4:
                                                                    break;
                                                                case 5: //Back to the previous page
                                                                    break;
                                                                case 6:
                                                                    System.out.println("Exiting the program.");
                                                                    scanner.close();
                                                                    System.exit(0);
                                                                    break;
                                                                default:
                                                                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                                                            }
                                                            if (vehicleNChoice == 5){
                                                                break; // Exit the inner loop when the admin chooses to exit
                                                            }
                                                        }
                                                    }
                                                    else{
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
                                    case 13: //Back to the previous page
                                        break;
                                    case 14:
                                        System.out.println("Exiting the program.");
                                        scanner.close();
                                        System.exit(0);
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please enter a number from 1 to 14.");
                                }

                                if (adminChoice == 13) {
                                    break; // Exit the inner loop when the admin chooses to exit
                                }
                            }
                        }
                        else {
                            System.out.println("Login failed, please try again");
                        }
                    }
                    break;

                case 2:
                    boolean notLoggedIn = true;
                    while (notLoggedIn) {
                        Scanner userScanner = new Scanner(System.in);
                        System.out.print("Enter username: ");
                        String inputUserName = userScanner.nextLine();
                        System.out.print("Enter password: ");
                        String inputPassword = userScanner.nextLine();

                        if (Account.authenticateUser(inputUserName, inputPassword)) {
                            System.out.println("Login successfully.");
                            SystemAdmin admin = new SystemAdmin();
                            notLoggedIn = false;
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 3.");
            }
        }
    }
}
