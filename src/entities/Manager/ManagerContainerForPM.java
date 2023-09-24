package src.entities.Manager;

import src.entities.Containers.Containers;
import src.entities.Port.Port;
import src.entities.User.PortManager;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ManagerContainerForPMInterfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Define a ManagerContainerForPM class that implements ManagerContainerForPMInterfaces
public class ManagerContainerForPM implements ManagerContainerForPMInterfaces {
    // Define a constant for the file name
    static final String portContainersListFileName = "data/PortContainersList.txt";

    // Implement the start method required by the ManagerContainerForPMInterfaces interface
    @Override
    public void start(PortManager portManager) {
        // Get the Port associated with the PortManager
        Port port = portManager.getPort();

        // Get the list of Containers in the specified Port
        List<Containers> containersList = Containers.getContainersInPortList(portContainersListFileName, port.getUniqueID());

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            // Display the menu options
            System.out.println("1. Add container to port");
            System.out.println("2. Add container to vehicle");
            System.out.println("3. Remove container from port");
            System.out.println("4. Calculate required fuel for a container");
            System.out.println("5. Return");
            System.out.println("Please choose (1-5): ");
            option = scanner.nextInt();

            // Handle user's choice based on the selected option
            if (option == 1) {
                // Add a container to the Port
                Containers.inputContainerToPortForPM(port);
            } else if (option == 2) {
                // Add a container to a vehicle
                Containers.inputContainerToVehicle();
            } else if (option == 3) {
                // Remove a container from the Port
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                for (Containers containers : containersList) {
                    if (containers.getUniqueID().equals(containerToDelete)) {
                        Port.removeContainerFromPort(containerToDelete);
                    }
                }
            } else if (option == 4) {
                // Calculate required fuel for a container
                String containerID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID: ");
                containerID = scanner1.nextLine();
                for (Containers containers1 : containersList) {
                    if (containerID.equals(containers1.getUniqueID())) {
                        System.out.println("Fuel for ship per Km: " + containers1.requiredShipFuelConsumption());
                        System.out.println("Fuel for truck per Km: " + containers1.requiredTruckFuelConsumption());
                    }
                }
            } else if (option == 5) {
                // Return to the previous menu
            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option != 5);
    }
}
