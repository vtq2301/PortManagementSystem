package src.entities.Manager;

import src.entities.Port.Port;
import src.entities.User.Admin;
import src.entities.User.PortManager;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ManagerVehicleForPMInterfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerVehicleForPM implements ManagerVehicleForPMInterfaces {
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";

    @Override
    public void start(PortManager portManager) {
        Port port = portManager.getPort();
        List<Vehicles> vehicleList = new ArrayList<>();
        List<Vehicles> vehiclesList = Port.readPortVehiclesList(portVehiclesListFileName);

        // Filter vehicles for the current port managed by the PortManager
        for (Vehicles vehicles : vehiclesList) {
            if (vehicles.getCurrentPort().getUniqueID().equals(port.getUniqueID())) {
                vehicleList.add(vehicles);
            }
        }

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("----------  Vehicle Admin Menu ----------");
            System.out.println("1. Add vehicle to port"); // Option to add a vehicle to the current port
            System.out.println("2. Remove vehicle"); // Option to remove a vehicle from the current port
            System.out.println("3. Refuel vehicle"); // Option to refuel a vehicle
            System.out.println("4. Return"); // Option to return to the Port Manager menu
            System.out.println("Please choose (1-4): ");
            option = scanner.nextInt();

            if (option == 1) {
                // Add a vehicle to the current port
                Port.addVehicleToPortForPM(port);
            } else if (option == 2) {
                // Remove a vehicle from the current port
                String vehicleToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Vehicles that you want to delete: ");
                vehicleToDelete = scanner1.nextLine();

                // Loop through the filtered vehicle list to find and remove the selected vehicle
                for (Vehicles vehicles : vehicleList) {
                    if (vehicles.getUniqueID().equals(vehicleToDelete)) {
                        Port.removeVehicleFromPort(vehicleToDelete);
                    }
                }
            } else if (option == 3) {
                // Refuel a vehicle
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                double fuel;
                System.out.println("Enter VehicleID that needed to be refueled: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter the amount of fuel: ");
                fuel = scanner1.nextDouble();

                // Loop through the filtered vehicle list to find and refuel the selected vehicle
                for (Vehicles vehicles : vehicleList) {
                    if (vehicles.getUniqueID().equals(vehicleID)) {
                        Vehicles.refuelVehicle(vehicleID, fuel);
                    }
                }
            } else if (option == 4) {
                // Return to the Port Manager menu
                PortManager.start(portManager);
            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option == 4);
    }
}
