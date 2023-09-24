package src.entities.User;

import src.entities.Manager.*;
import src.entities.Port.Port;
import src.entities.Port.Trip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    static final String portManagerFileName = "data/PortManager.txt";
    public Admin(String userID, String username, String password, String role){
        super(userID, username, password, "Admin");
    }

    public static void assignPortManager(String portID,PortManager portManager){
        Port currentPort = Port.getPortById(portID);
        if (currentPort == null){
            System.out.println("Port doesn't exist");
        } else if (!PortManager.checkForPortIDInPortManager(currentPort)) {
            System.out.println("Port already has a manager.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(portManagerFileName, true))) {
                writer.write(portID + ",");
                writer.write(portManager.getUserID() + ",");
                writer.write(portManager.getUsername() + ",");
                writer.write(portManager.getPassword() + ",");
                writer.write(portManager.getRole());
                writer.newLine();
                System.out.println("Port Manager data has been written to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeManager(String managerIdToDelete){
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portManagerFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[4].equals("Port Manager")) {
                    String managerID = parts[1];
                    if (!managerID.equals(managerIdToDelete)) {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);  // Keep non-Port Manager lines
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portManagerFileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Port Manager with ID " + managerIdToDelete + " has been deleted.");
    }
    public static void writeMoveVehicleToPort(String portFile, String vehicleID, String newPortID) {
        List<String> portLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2 && parts[2].equals(vehicleID)) {
                    line = line.replaceFirst(parts[0], newPortID);
                }
                portLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portFile))) {
            for (String line : portLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Vehicle with ID " + vehicleID + " has been transferred to port " + newPortID);
    }
    public static List<PortManager> getAllPortManager() {
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

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Ports");
            System.out.println("2. Vehicles");
            System.out.println("3. Containers");
            System.out.println("4. Port Managers");
            System.out.println("5. Trips");
            System.out.println("6. View stats");
            System.out.println("7. Calculate fuel used in a day");
            System.out.println("8. Log out");
            System.out.print("Choose an action (1-8): ");
            option = scanner.nextInt();
            if (option == 1) {
                ManagerPort managerPort = new ManagerPort();
                managerPort.start();
            }
            else if (option == 2) {
                ManagerVehicle managerVehicles = new ManagerVehicle();
                managerVehicles.start();
            } else if (option == 3) {
                ManagerContainer managerContainer = new ManagerContainer();
                managerContainer.start();
            } else if (option == 4) {
                ManagerPortManager managerPortManager = new ManagerPortManager();
                managerPortManager.start();
            } else if (option == 5) {
                ManagerTrip managerTrip = new ManagerTrip();
                managerTrip.start();
            } else if (option==6) {
                ManagerViewStats managerViewStats = new ManagerViewStats();
                managerViewStats.start();
            } else if (option == 7) {
                String givenDay = "Mon Sep 18 00:00:00 ICT 2023";
                System.out.println(Trip.calculateTotalFuelConsumption(givenDay));
            } else if (option == 8) {

            } else {
                System.out.println("Invalid input! You should enter 1 or 2!");
            }
        } while (option != 7);
    }
}
