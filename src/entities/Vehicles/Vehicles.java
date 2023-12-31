package src.entities.Vehicles;
import src.entities.Containers.*;
import src.entities.Port.Port;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public abstract class Vehicles{
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    private String name;
    private String uniqueID;
    private String vehicleType;
    private double currentFuel;
    private double fuelCapacity;
    private double carryingCapacity;
    private double currentCarryingCapacity;
    private Port currentPort;
    private List<Containers> containersList;
    public Vehicles(String name, String uniqueID, String vehicleType, double currentFuel, double carryingCapacity, double currentCarryingCapacity, double fuelCapacity, List<Containers> containersList, Port currentPort) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.vehicleType = vehicleType;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.currentCarryingCapacity = currentCarryingCapacity;
        this.containersList = new ArrayList<>();
        this.currentPort = currentPort;

    }
    public Vehicles() {
        this.uniqueID = "Default";
        this.name = "Default";
        this.vehicleType = "Default";
        this.carryingCapacity = 0;
        this.fuelCapacity = 0;
        this.currentCarryingCapacity = 0;
        this.currentFuel = fuelCapacity;
        this.currentPort = null;
        this.containersList = new ArrayList<>();
    }
    // Getters and Setters of name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters of unique ID
    public String getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    // Getters and Setters of current fuel
    public double getCurrentFuel() {
        return currentFuel;
    }
    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    // Getters and Setters of carrying capacity 
    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    // Getters and Setters of fuel capacity
    public double getFuelCapacity() {
        return fuelCapacity;
    }
    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    // Getters and Setters of current port
    public Port getCurrentPort() {
        return currentPort;
    }
    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public List<Containers> getContainersList() {
        return containersList;
    }

    public void setContainersList(List<Containers> containersList) {
        this.containersList = containersList;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getCurrentCarryingCapacity() {
        return currentCarryingCapacity;
    }

    public void setCurrentCarryingCapacity(double currentCarryingCapacity) {
        this.currentCarryingCapacity = currentCarryingCapacity;
    }


    public boolean canAddContainers(Containers containers){
        return true;
    }
    public void removeContainers(Containers containers){
        containersList.remove(containers);
    }

    public boolean moveToPort(Port port){
        if (port.isLandingAbility() == true){
            return true;
        }
        return false;
    }
    public boolean overweight(Containers containers){
        if (this.currentCarryingCapacity + containers.getContainerWeight()<this.carryingCapacity){
            return false;
        }
        return true;
    }
    public void moveVehicleToPort(Port port){
        if (moveToPort(port) == true){
            this.currentPort.removeVehicle(this);
            port.addVehicles(this);
        }
    }

    public static List<Containers> getContainersInVehicleList(String vehicleID) {
        List<Containers> containersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/VehicleContainersList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equals(vehicleID)) {
                    String containerID = parts[1].substring(2);
                    String containerType = parts[2];
                    double containerWeight = Double.parseDouble(parts[3]);
                    double fuelShip = Double.parseDouble(parts[4]);
                    double fuelTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Open side")){
                        OpenSide openSide = new OpenSide(containerWeight, containerID, containerType, getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(openSide);
                    } else if (containerType.equals("Open top")) {
                        OpenTop openTop = new OpenTop(containerWeight, containerID, containerType, getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(openTop);
                    } else if (containerType.equals("Dry storage")) {
                        DryStorage dryStorage = new DryStorage(containerWeight, containerID, containerType, getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(dryStorage);
                    } else if (containerType.equals("Liquid")) {
                        Liquid liquid = new Liquid(containerWeight, containerID, containerType, getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(liquid);
                    } else if (containerType.equals("Refrigerated")) {
                        Refrigerated refrigerated = new Refrigerated(containerWeight, containerID, containerType, getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(refrigerated);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return containersList;
    }
    public static Vehicles getVehicleByID(String vehicleIDToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(vehicleIDToFind)) {
                    // Create and return the container object
                    String name = parts[1];
                    double currentFuel = Double.parseDouble(parts[3]);
                    double carryingCapacity = Double.parseDouble(parts[4]);
                    double CurrentCarryingCapacity = Double.parseDouble(parts[5]);
                    List<Containers> containersList = new ArrayList<>();
                    double fuelCapacity = Double.parseDouble(parts[6]);
                    String vehicleType = parts[7];
                    if (vehicleType.equals("class Vehicles.TankerTruck")){
                        TankerTruck newTankerTruck = new TankerTruck(name, parts[2],
                                "Tanker Truck", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, Port.getPortById(parts[0]));
                        return newTankerTruck;
                    } else if (vehicleType.equals("class Vehicles.ReeferTruck")) {
                        ReeferTruck newReeferTruck = new ReeferTruck(name, parts[2],
                                "Reefer Truck", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, Port.getPortById(parts[0]));
                        return newReeferTruck;
                    } else if (vehicleType.equals("class Vehicles.BasicTruck")) {
                        BasicTruck newBasicTruck = new BasicTruck(name, parts[2],
                                "Basic Truck", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, Port.getPortById(parts[0]));
                        return newBasicTruck;
                    } else if (vehicleType.equals("class Vehicles.Ship")) {
                        Ship newShip = new Ship(name, parts[2],
                                "Ship", currentFuel, carryingCapacity,CurrentCarryingCapacity, fuelCapacity, containersList, Port.getPortById(parts[0]));
                        return newShip;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> ListOfVehicleIDs() {
        List<String> valuesAtIndex2 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) { // Ensure there is an index 2
                    valuesAtIndex2.add(parts[2].substring(2));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valuesAtIndex2;
    }

/**
     * Takes user input to add a container to a specific vehicle.
     */
    public static void inputContainerToVehicle() {
        String vehicleID;
        String containerID;
        double containerWeight;
        int containerType;
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the vehicle: ");
        vehicleID = scanner.nextLine();
        
        System.out.println("Enter container ID: ");
        containerID = scanner.nextLine();
        
        System.out.println("Enter container weight: ");
        containerWeight = scanner.nextDouble();
        
        System.out.println("----------  Container Type ----------");
        System.out.println("1. Dry Storage");
        System.out.println("2. Open Top");
        System.out.println("3. Open Side");
        System.out.println("4. Refrigerated");
        System.out.println("5. Liquid");
        System.out.print("Please choose from 1-5: ");
        
        containerType = scanner.nextInt();
        
        Vehicles vehicle = Vehicles.getVehicleByID(vehicleID);
        
        if (vehicle == null) {
            System.out.println("Vehicle doesn't exist.");
        } else {
            switch (containerType) {
                case 1:
                    DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                            "Dry storage", vehicle, 3.5, 4.6);
                    Vehicles.addContainerToVehicle(vehicleContainersFileName, vehicleID, newDryStorage);
                    break;
                case 2:
                    OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                            "Open top", vehicle, 2.8, 3.2);
                    Vehicles.addContainerToVehicle(vehicleContainersFileName, vehicleID, newOpenTop);
                    break;
                case 3:
                    OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                            "Open side", vehicle, 2.7, 3.2);
                    Vehicles.addContainerToVehicle(vehicleContainersFileName, vehicleID, newOpenSide);
                    break;
                case 4:
                    Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                            "Refrigerated", vehicle, 4.5, 5.4);
                    Vehicles.addContainerToVehicle(vehicleContainersFileName, vehicleID, newRefrigerated);
                    break;
                case 5:
                    Liquid newLiquid = new Liquid(containerWeight, containerID,
                            "Liquid", vehicle, 4.8, 5.3);
                    Vehicles.addContainerToVehicle(vehicleContainersFileName, vehicleID, newLiquid);
                    break;
                default:
                    System.out.println("Option not valid");
            }
        }
    }

    public static void updateVehicleWeight(String vehicleIDToUpdate, double newValue) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(vehicleIDToUpdate)) {
                    if (parts.length > 5) {
                        parts[5] = Double.toString(newValue);
                    }
                }
                updatedLines.add(String.join(",", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portVehiclesListFileName))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeContainerFromVehicle(String filePath, String containerIDToDelete) {
        List<String> lines = new ArrayList<>();
        Containers containers = Containers.getContainerByID(containerIDToDelete);
        if (containers == null){
            System.out.println("Container doesn't exist");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 1 && !parts[1].equals(containerIDToDelete)) {
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Port.updatePortWeight(containers.getCurrentVehicle().getUniqueID(), containers.getCurrentVehicle().getCurrentCarryingCapacity()-containers.getContainerWeight());
            System.out.println("Container with ID " + containerIDToDelete + " has been deleted.");
        }
    }
    public static void refuelVehicle(String vehicleIDToUpdate, double newValue) {
        List<String> lines = new ArrayList<>();
        Vehicles vehicles = getVehicleByID(vehicleIDToUpdate);
        if (vehicles.getCurrentFuel()+newValue > vehicles.getFuelCapacity()){
            System.out.println("Can't add due to exceed the maximum fuel capacity.");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 3 && parts[2].equals(vehicleIDToUpdate)) {
                        parts[3] = String.valueOf(newValue);
                        line = String.join(",", parts);
                    }
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(portVehiclesListFileName))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Vehicle with ID " + vehicleIDToUpdate + " has been updated.");
        }
    }
    public static void writeUpdateVehicleFuel(String vehicleID, double fuelCapacity){
        try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            StringBuilder updatedFileContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[2].equals(vehicleID)) {
                    try {
                        double oldValue = Double.parseDouble(parts[3]);
                        double newValue = oldValue - fuelCapacity;
                        parts[3] = String.valueOf(newValue);
                    } catch (NumberFormatException e) {
                        // Handle parsing error if needed
                        System.err.println("Error parsing value at index 3 in line: " + line);
                    }
                }

                // Reconstruct the line with updated value
                StringBuilder updatedLine = new StringBuilder(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    updatedLine.append(",").append(parts[i]);
                }

                updatedFileContent.append(updatedLine).append("\n");
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(portVehiclesListFileName))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Vehicles.Vehicles{" +
                "name='" + name + '\'' +
                ", uniqueID='" + uniqueID + '\'' +
                ", currentFuel=" + currentFuel +
                ", carryingCapacity=" + carryingCapacity +
                ", fuelCapacity=" + fuelCapacity +
                ", currentPortID=" + currentPort.getUniqueID() +
                '}';
    }
}
