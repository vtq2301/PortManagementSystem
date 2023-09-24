package src.entities.Manager;
import src.entities.Containers.Containers;
import src.entities.Port.*;
import src.entities.User.Admin;
import src.entities.Vehicles.*;
import src.interfaces.ManagerContainerInterfaces;

import java.util.List;
import java.util.Scanner;

public class ManagerContainer implements ManagerContainerInterfaces{
    static final String portContainersListFileName = "data/PortContainersList.txt";
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. Add container to port");
            System.out.println("2. Add container to vehicle");
            System.out.println("3. Remove container from port");
            System.out.println("4. Remove container from vehicle");
            System.out.println("5. Calculate required fuel for a container");
            System.out.println("6. Return");
            System.out.println("Please choose (1-8): ");
            option = scanner.nextInt();
            if (option==1){
                Containers.inputContainerToPort();
            } else if (option==2) {
                Containers.inputContainerToVehicle();
            } else if (option==3) {
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                Port.removeContainerFromPort(containerToDelete);
            } else if (option==4) {
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                Vehicles.removeContainerFromVehicle(portContainersListFileName, containerToDelete);
            } else if (option==5) {
                List<Containers> containers = Containers.getAllContainers();
                String containerID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID: ");
                containerID = scanner1.nextLine();
                for (Containers containers1:containers){
                    if (containerID.equals(containers1.getUniqueID())){
                        System.out.println("Fuel for ship per Km: " + containers1.requiredShipFuelConsumption());
                        System.out.println("Fuel for truck per Km: "+ containers1.requiredTruckFuelConsumption());
                    }

                }
            } else if (option==6) {
                Admin.start();
            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option!=6);
    }
}
