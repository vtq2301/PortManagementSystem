package src.entities.User;

import src.entities.Containers.Containers;
import src.entities.Manager.ManagerContainerForPM;
import src.entities.Manager.ManagerVehicleForPM;
import src.entities.Port.*;
import src.entities.Vehicles.Vehicles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Define a PortManager class that extends the User class
public class PortManager extends User {
    // Define constants for file names
    static final String portManagerFileName = "data/PortManager.txt";
    static final String portContainersListFileName = "data/PortContainersList.txt";
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";

    // Private member variable for the associated Port
    private Port port;

    // Constructor to initialize PortManager with user details and associated Port
    public PortManager(String userID, String username, String password, String role, Port port) {
        super(userID, username, password, "Port Manager");
        this.port = port;
    }

    // Getter method to retrieve the associated Port
    public Port getPort() {
        return port;
    }

    // Setter method to set the associated Port
    public void setPort(Port port) {
        this.port = port;
    }

    // Method to retrieve a list of all PortManagers from a file
    public static List<PortManager> getAllPortManagers() {
        List<PortManager> portManagers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portManagerFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals("Port Manager")) {
                    String portID = parts[0];
                    String userID = parts[1];
                    String username = parts[2];
                    String password = parts[3];
                    String role = parts[4];
                    PortManager portManager = new PortManager(userID, username, password, role, Port.getPortById(portID));
                    portManagers.add(portManager);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portManagers;
    }

    // Method to check if a given Port is associated with any PortManager
    public static boolean checkForPortIDInPortManager(Port port) {
        List<PortManager> portManagerList = getAllPortManagers();
        for (PortManager portManager : portManagerList) {
            Port port1 = portManager.getPort();
            if (port1.getUniqueID().equals(port.getUniqueID())) {
                return false;
            }
        }
        return true;
    }

    // Method to start the main menu for a PortManager
    public static void start(PortManager portManager) {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Vehicles");
            System.out.println("2. Containers");
            System.out.println("3. View stats");
            System.out.println("4. Log out");
            System.out.print("Choose an action (1-4): ");
            option = scanner.nextInt();
            if (option == 1) {
                ManagerVehicleForPM managerVehicleForPM = new ManagerVehicleForPM();
                managerVehicleForPM.start(portManager);
            } else if (option == 2) {
                ManagerContainerForPM managerContainerForPM = new ManagerContainerForPM();
                managerContainerForPM.start(portManager);
            } else if (option == 3) {
                Scanner scanner1 = new Scanner(System.in);
                int option1;
                do {
                    System.out.println("1. View all containers");
                    System.out.println("2. View all vehicles");
                    System.out.println("3. Return");
                    System.out.println("Please choose (1-11): ");
                    option = scanner.nextInt();
                    if (option == 1) {
                        // Get a list of containers in the port
                        List<Containers> containersList = Containers.getContainersInPortList(portContainersListFileName, portManager.getPort().getUniqueID());
                    } else if (option == 2) {
                        // Get a list of vehicles in the port
                        List<Vehicles> vehicleList = new ArrayList<>();
                        List<Vehicles> vehiclesList = Port.readPortVehiclesList(portVehiclesListFileName);
                        for (Vehicles vehicles : vehiclesList) {
                            if (vehicles.getCurrentPort().getUniqueID().equals(portManager.getPort().getUniqueID())) {
                                vehicleList.add(vehicles);
                            }
                        }
                        for (Vehicles vehicles : vehiclesList) {
                            System.out.println(vehicles.getUniqueID());
                        }
                    } else if (option == 3) {
                        // Return to the previous menu
                    } else {
                        System.out.println("Please enter a valid number");
                    }
                } while (option != 3);
            } else if (option == 4) {
                // Log out
            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option != 7);
    }
}
