package GroupAssignment;


import java.util.Scanner;

import GroupAssignment.ScreenDisplay.Account;
import ScreenDisplay.SystemAdmin;
//import Vehicle.Truck;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Login as a System Admin");
            System.out.println("2. Login as a Port Manager");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            //1: SA, 2: PM, 3:Exit
            switch (choice) {
                case 1:
                    boolean stillLoggedIn = true;
                    while (stillLoggedIn) {
                        Scanner userScanner = new Scanner(System.in);
                        System.out.print("Enter username: ");
                        String inputUserName = userScanner.nextLine();
                        System.out.print("Enter password: ");
                        String inputPassword = userScanner.nextLine();

                        if (Account.authenticateUser(inputUserName, inputPassword)) {
                            System.out.println("Login successfully.");
                            SystemAdmin admin = new SystemAdmin();
                            stillLoggedIn = false;

                            int adminChoice; // Declare adminChoice outside the loop
                            while (true) {
                                System.out.println("Select an option:");
                                System.out.println("1. Add a new port");
                                System.out.println("2. Add a new vehicle");
                                System.out.println("3. Add a new container");
                                System.out.println("4. Exit");
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
                                        System.out.println("Exiting the admin menu.");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please enter a number from 1 to 4.");
                                }

                                if (adminChoice == 4) {
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




