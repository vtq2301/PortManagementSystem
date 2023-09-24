package src.entities.Manager;

import src.Interfaces.ManagerContainerForPMInterfaces;
import src.entities.Containers.Containers;
import src.entities.Port.Port;
import src.entities.User.PortManager;
import src.entities.Vehicles.Vehicles;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerContainerForPM implements ManagerContainerForPMInterfaces {
    static final String portContainersListFileName = "data/PortContainersList.txt";
    @Override
    public void start(PortManager portManager) {
        Port port = portManager.getPort();
        List<Containers> containersList = Containers.getContainersInPortList(portContainersListFileName, port.getUniqueID());
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. Add container to port");
            System.out.println("2. Add container to vehicle");
            System.out.println("3. Remove container from port");
            System.out.println("4. Calculate required fuel for a container");
            System.out.println("5. Return");
            System.out.println("Please choose (1-5): ");
            option = scanner.nextInt();
            if (option==1){
                Containers.inputContainerToPortForPM(port);
            } else if (option==2) {
                Containers.inputContainerToVehicle();
            } else if (option==3) {
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                for (Containers containers:containersList){
                    if (containers.getUniqueID().equals(containerToDelete)){
                        Port.removeContainerFromPort(containerToDelete);
                    }
                }
            } else if (option==4) {
                String containerID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID: ");
                containerID = scanner1.nextLine();
                for (Containers containers1:containersList){
                    if (containerID.equals(containers1.getUniqueID())){
                        System.out.println("Fuel for ship per Km: " + containers1.requiredShipFuelConsumption());
                        System.out.println("Fuel for truck per Km: "+ containers1.requiredTruckFuelConsumption());
                    }

                }
            } else if (option==5) {

            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option!=5);
    }
}
