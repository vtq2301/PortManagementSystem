package src.entities.Manager;
import src.entities.Containers.*;
import src.entities.Port.Port;
import src.entities.User.Admin;
import src.entities.User.PortManager;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ManagerPortInterfaces;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ManagerPort implements ManagerPortInterfaces{
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    private static String vehicleContainersFileName = "data/VehicleContainersList.txt";
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("----------  Port Admin Menu ----------");
            System.out.println("1. Add ports"); //Done
            System.out.println("2. Remove ports"); //Done
            System.out.println("3. Assign port manager"); //Done
            System.out.println("4. Add vehicle to port"); //Done
            System.out.println("5. Remove vehicle from port"); //Done
            System.out.println("6. Add container to port"); //Done
            System.out.println("7. Remove container from port"); //Done
            System.out.println("8. Calculate port distance"); //Done
            System.out.println("9. Load container from port to vehicle"); //Done
            System.out.println("10. Load container from vehicle to port"); //Done
            System.out.println("11. Return"); //Done
            System.out.print("Choose an action (1-11): ");
            option = scanner.nextInt();
            if (option == 1) {
                Port.addPort();
            }
            else if (option == 2) {
                Scanner scanner1 = new Scanner(System.in);
                String portToDelete;
                System.out.println("Enter the portID that you want to delete:");
                portToDelete = scanner1.nextLine();
                Port.removePort(portToDelete);
            }
            else if (option == 3) {
                String portID;
                String userID;
                String username;
                String password;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port that you want the manager to be assigned:");
                portID = scanner1.nextLine();
                System.out.println("UserId of the manager: ");
                userID = scanner1.nextLine();
                System.out.println("Username of the manager: ");
                username = scanner1.nextLine();
                System.out.println("Password of the manager: ");
                password = scanner1.nextLine();
                PortManager newPortManager = new PortManager(userID, username, password, "Port Manager", Port.getPortById(portID));
                Admin.assignPortManager(portID, newPortManager);
            }
            else if (option == 4) {
                Port.addVehicleToPort();
            }
            else if (option == 5) {
                Scanner scanner1 = new Scanner(System.in);
                String vehicleToDelete;
                System.out.println("Vehicles that you want to delete: ");
                vehicleToDelete = scanner1.nextLine();
                Port.removeVehicleFromPort(vehicleToDelete);
            }
            else if (option == 6) {
                String portID;
                String containerID;
                double containerWeight;
                int containerType;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port: ");
                portID = scanner1.nextLine();
                System.out.println("Enter container ID: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter container weight: ");
                containerWeight = scanner1.nextDouble();
                System.out.println("----------  Container Type ----------");
                System.out.println("1. Dry Storage");
                System.out.println("2. Open Top");
                System.out.println("3. Open Side");
                System.out.println("4. Refrigerated");
                System.out.println("5. Liquid");
                System.out.print("Please choose from 1-5: ");
                containerType = scanner1.nextInt();
                Port findPort = Port.getPortById(portID);
                if (findPort == null) {
                    System.out.println("Port doesn't exist.");
                } else {
                    if (containerType == 1){
                        DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                                "Dry storage", findPort, 3.5, 4.6);
                        Port.addContainerToPort(portID, newDryStorage);
                    } else if (containerType == 2) {
                        OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                                "Open top", findPort, 2.8, 3.2);
                        Port.addContainerToPort(portID, newOpenTop);
                    } else if (containerType == 3) {
                        OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                                "Open side", findPort, 2.7, 3.2);
                        Port.addContainerToPort(portID, newOpenSide);
                    } else if (containerType == 4) {
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                                "Refrigerated", findPort, 4.5, 5.4);
                        Port.addContainerToPort(portID, newRefrigerated);
                    } else if (containerType == 5) {
                        Liquid newLiquid = new Liquid(containerWeight, containerID,
                                "Liquid", findPort, 4.8, 5.3);
                        Port.addContainerToPort(portID, newLiquid);
                    } else {
                        System.out.println("Option not valid");
                    }
                }
            }
            else if (option == 7) {
                String containerToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Container that you want to delete: ");
                containerToDelete = scanner1.nextLine();
                Port.removeContainerFromPort(containerToDelete);
            } else if (option == 8) {
                String port1;
                String port2;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter IDs of first port");
                port1 = scanner1.nextLine();
                System.out.println("Enter IDs of second port");
                port2 = scanner1.nextLine();
                Port startPort = Port.getPortById(port1);
                Port endPort = Port.getPortById(port2);
                System.out.println(startPort.calculateDistance(endPort));
            } else if (option == 9) {
                String containerID;
                String vehicleID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID that needed to be moved: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter vehicleID: ");
                vehicleID = scanner1.nextLine();
                moveContainerFromPortToVehicle(containerID, vehicleID);
            } else if (option == 10) {
                String containerID;
                String portID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter containerID that needed to be moved: ");
                containerID = scanner1.nextLine();
                System.out.println("Enter portID: ");
                portID = scanner1.nextLine();
                moveContainerFromVehicleToPort(containerID, portID);
            } else if (option == 11) {
                Admin.start();
            } else {
                System.out.println("Please input a valid number");
            }
        } while (option!=14);
    }

    @Override
    public void moveContainerFromPortToVehicle(String containerUniqueID, String vehicleUniqueID) {
        Containers containerToFind = Containers.getContainerByID(containerUniqueID);
        Vehicles vehiclesToFind = Vehicles.getVehicleByID(vehicleUniqueID); // make a function to find the vehicle and then create a new container with vehicle not port
        if (containerToFind == null){
            System.out.println("Container doesn't exist");
        } else if (vehiclesToFind == null) {
            System.out.println("Vehicle doesn't exist");
        } else {
            double containerWeight = containerToFind.getContainerWeight();
            String uniqueID = containerToFind.getUniqueID().substring(1);
            String containerType = containerToFind.getContainerType();
            System.out.println(containerType);
            System.out.println(vehiclesToFind.canAddContainers(containerToFind));
            System.out.println(vehiclesToFind.overweight(containerToFind));
            if (containerType.equals("Open side") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)){
                OpenSide newOpenSide = new OpenSide(containerWeight, uniqueID, containerType, vehiclesToFind, 2.7, 3.2);
                writeContainerFromPortToVehicle(vehicleContainersFileName, vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newOpenSide);
                Port.removeContainerFromPort(containerUniqueID);
                Vehicles.updateVehicleWeight(vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                Port.updatePortWeight(containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
//                System.out.println(vehicleUniqueID);
            } else if (containerType.equals("Open top") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                OpenTop newOpenTop = new OpenTop(containerWeight, uniqueID, containerType, vehiclesToFind, 2.8, 3.2);
                writeContainerFromPortToVehicle(vehicleContainersFileName, vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newOpenTop);
                Port.removeContainerFromPort(containerUniqueID);
                Vehicles.updateVehicleWeight(vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                Port.updatePortWeight(containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            } else if (containerType.equals("Liquid") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                Liquid newLiquid = new Liquid(containerWeight, uniqueID, containerType, vehiclesToFind, 4.8, 5.3);
                writeContainerFromPortToVehicle(vehicleContainersFileName, vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newLiquid);
                Port.removeContainerFromPort(containerUniqueID);
                Vehicles.updateVehicleWeight(vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                Port.updatePortWeight(containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            } else if (containerType.equals("Dry storage") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                DryStorage newDryStorage = new DryStorage(containerWeight, uniqueID, containerType, vehiclesToFind, 3.5, 4.6);
                writeContainerFromPortToVehicle(vehicleContainersFileName, vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newDryStorage);
                Port.removeContainerFromPort(containerUniqueID);
                Vehicles.updateVehicleWeight(vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                Port.updatePortWeight(containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            } else if (containerType.equals("Refrigerated") && vehiclesToFind.canAddContainers(containerToFind) && !vehiclesToFind.overweight(containerToFind)) {
                Refrigerated newRefrigerated = new Refrigerated(containerWeight, uniqueID, containerType, vehiclesToFind, 4.5, 5.4);
                writeContainerFromPortToVehicle(vehicleContainersFileName, vehicleUniqueID, containerToFind.getCurrentPort().getUniqueID(),newRefrigerated);
                Port.removeContainerFromPort(containerUniqueID);
                Vehicles.updateVehicleWeight(vehicleUniqueID, vehiclesToFind.getCurrentCarryingCapacity()+containerWeight);
                Port.updatePortWeight(containerToFind.getCurrentPort().getUniqueID(), containerToFind.getCurrentPort().getCurrentCapacity()-containerWeight);
            }
        }
    }

    @Override
    public void writeContainerFromPortToVehicle(String filePath, String vehicleID, String portID, Containers container) {
        List<Containers> containersList = Containers.readContainerDataFromFile(vehicleContainersFileName);
        List<Vehicles> vehiclesList = Port.readPortVehiclesList(portVehiclesListFileName);
        boolean repeated = false;
        boolean vehicleInPort = false;
        for (Containers containers:containersList){
            if (containers.getUniqueID().equals(container.getUniqueID())){
                repeated = true;
            }
        }
        for (Vehicles vehicles:vehiclesList){
            if (vehicles.getCurrentPort().getUniqueID().equals(portID)){
                vehicleInPort = true;
            }
        }
        System.out.println(repeated);
        if (repeated){
            System.out.println("Can't add due to duplicated container IDs");
        } else if (!vehicleInPort){
            System.out.println("Can't add due to vehicle is not currently in port");
        }else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(vehicleID + ",");
                writer.write(container.getUniqueID() + ",");
                writer.write(container.getContainerType() + ",");
                writer.write(container.getContainerWeight() + ",");
                writer.write(container.getFuelConsumptionShip() + ",");
                writer.write(container.getFuelConsumptionTruck()+"");
                writer.newLine();
                Vehicles.updateVehicleWeight(container.getCurrentVehicle().getUniqueID(), container.getCurrentVehicle().getCurrentCarryingCapacity()+container.getContainerWeight());
                System.out.println("Container data has been appended to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void moveContainerFromVehicleToPort(String containerUniqueID, String portUniqueID) {
        Containers containersToFind = Containers.getContainerByIDInVehicle(vehicleContainersFileName, containerUniqueID);
        Port portToFind = Port.getPortById(portUniqueID);
        if (containersToFind == null){
            System.out.println("Container doesn't exist");
        } else if (portToFind == null) {
            System.out.println("Port doesn't exist");
        } else if (!containersToFind.getCurrentVehicle().getCurrentPort().getUniqueID().equals(portToFind.getUniqueID())) {
            System.out.println("The vehicle that contains the container is not in the port");
        } else {
            Vehicles vehicles = Vehicles.getVehicleByID(containersToFind.getCurrentVehicle().getUniqueID().substring(2));
            System.out.println(vehicles.getUniqueID()+vehicles.getName() + vehicles.getCurrentCarryingCapacity());
            double containerWeight = containersToFind.getContainerWeight();
            String uniqueID = containersToFind.getUniqueID().substring(1);
            String containerType = containersToFind.getContainerType();
            if (containerType.equals("Dry storage") && !portToFind.isOverWeight(containersToFind)){
                DryStorage newDryStorage = new DryStorage(containerWeight, uniqueID, "Dry storage", Port.getPortById(portUniqueID), 3.5, 4.6);
                Port.addContainerToPort(portUniqueID, newDryStorage);
                Port.removeContainerFromPort(containersToFind.getUniqueID());
                Port.updatePortWeight(portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                Vehicles.updateVehicleWeight(vehicles.getUniqueID().substring(2), vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Open top") && !portToFind.isOverWeight(containersToFind)) {
                OpenTop newOpenTop = new OpenTop(containerWeight, uniqueID, "Open top", Port.getPortById(portUniqueID), 2.8, 3.2);
                Port.addContainerToPort(portUniqueID, newOpenTop);
                Port.removeContainerFromPort(containersToFind.getUniqueID());
                Port.updatePortWeight(portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                Vehicles.updateVehicleWeight(vehicles.getUniqueID().substring(2), vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Open side") && !portToFind.isOverWeight(containersToFind)) {
                OpenSide newOpenSide = new OpenSide(containerWeight, uniqueID, "Open side", Port.getPortById(portUniqueID), 2.7, 3.2);
                Port.addContainerToPort( portUniqueID, newOpenSide);
                Port.removeContainerFromPort(containersToFind.getUniqueID());
                Port.updatePortWeight(portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                Vehicles.updateVehicleWeight(vehicles.getUniqueID().substring(2), vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Refrigerated") && !portToFind.isOverWeight(containersToFind)) {
                Refrigerated newRefrigerated = new Refrigerated(containerWeight, uniqueID, "Refrigerated", Port.getPortById(portUniqueID), 4.5, 5.4);
                Port.addContainerToPort(portUniqueID, newRefrigerated);
                Port.removeContainerFromPort(containersToFind.getUniqueID());
                Port.updatePortWeight(portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                Vehicles.updateVehicleWeight(vehicles.getUniqueID().substring(2), vehicles.getCurrentCarryingCapacity()-containerWeight);
            } else if (containerType.equals("Liquid") && !portToFind.isOverWeight(containersToFind)) {
                Liquid newLiquid = new Liquid(containerWeight, uniqueID, "Liquid", Port.getPortById(portUniqueID), 4.8, 5.3);
                Port.addContainerToPort(portUniqueID, newLiquid);
                Port.removeContainerFromPort(containersToFind.getUniqueID());
                Port.updatePortWeight(portUniqueID, portToFind.getCurrentCapacity()+containerWeight);
                Vehicles.updateVehicleWeight(vehicles.getUniqueID().substring(2), vehicles.getCurrentCarryingCapacity()-containerWeight);
            }
        }
    }
}
