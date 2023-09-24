package src.entities.Containers;

import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ContainerInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Containers implements ContainerInterface{
    // Filenames
    static final String portFileName = "data/Ports.txt";
    static final String portManagerFileName = "data/PortManager.txt";
    static final String portContainersListFileName = "data/PortContainersList.txt";
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    static final String tripFileName = "data/Trip.txt";
    private static String vehicleContainersFileName = "data/VehicleContainersList.txt";
    // Attributes
    private double containerWeight;
    private String uniqueID;
    private String containerType;
    private double requiredFuel;
    private Port currentPort;
    private Vehicles currentVehicle;
    private double fuelConsumptionShip;
    private double fuelConsumptionTruck;

    public Containers(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck) {
        this.containerWeight = containerWeight;
        this.uniqueID = "c"+uniqueID;
        this.containerType = containerType;
        this.currentVehicle = currentVehicle;
        this.fuelConsumptionShip = fuelConsumptionShip;
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }

    public Containers(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck) {
        this.containerWeight = containerWeight;
        this.uniqueID = "c"+uniqueID;
        this.containerType = containerType;
        this.currentPort = currentPort;
        this.fuelConsumptionShip = fuelConsumptionShip;
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }

    public Containers() {
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public double getContainerWeight() {
        return containerWeight;
    }


    public void setContainerWeight(double containerWeight) {
        this.containerWeight = containerWeight;
    }

    public double requiredShipFuelConsumption(){
        if (this.getClass() == DryStorage.class){
            requiredFuel = containerWeight*3.5;
            return requiredFuel;
        } else if (this.getClass() == Liquid.class) {
            requiredFuel=containerWeight*4.8;
            return requiredFuel;
        } else if (this.getClass() == OpenSide.class) {
            requiredFuel=containerWeight*2.7;
            return requiredFuel;
        } else if (this.getClass() == OpenTop.class) {
            requiredFuel=containerWeight*2.8;
            return requiredFuel;
        } else if (this.getClass() == Refrigerated.class) {
            requiredFuel=containerWeight*4.5;
            return requiredFuel;
        } else {
            System.out.println("Can't get type");
            return 0;
        }
    }
    public double requiredTruckFuelConsumption(){
        if (this.getClass() == DryStorage.class){
            requiredFuel=containerWeight*4.6;
            return requiredFuel;
        } else if (this.getClass() == Liquid.class) {
            requiredFuel=containerWeight*5.3;
            return requiredFuel;
        } else if (this.getClass() == OpenSide.class) {
            requiredFuel=containerWeight*3.2;
            return requiredFuel;
        } else if (this.getClass() == OpenTop.class) {
            requiredFuel=containerWeight*3.2;
            return requiredFuel;
        } else if (this.getClass() == Refrigerated.class) {
            requiredFuel=containerWeight*5.4;
            return requiredFuel;
        } else {
            System.out.println("Can't get type");
            return 0;
        }
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public Vehicles getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicles currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    public double getFuelConsumptionShip() {
        return fuelConsumptionShip;
    }

    public void setFuelConsumptionShip(double fuelConsumptionShip) {
        this.fuelConsumptionShip = fuelConsumptionShip;
    }

    public double getFuelConsumptionTruck() {
        return fuelConsumptionTruck;
    }

    public void setFuelConsumptionTruck(double fuelConsumptionTruck) {
        this.fuelConsumptionTruck = fuelConsumptionTruck;
    }

    public void setRequiredFuel(double requiredFuel) {
        this.requiredFuel = requiredFuel;
    }
/**
 * Reads container data from a file and creates container objects.
 *
 * @param filePath The path to the container data file.
 * @return A list of container objects.
 */
    public static List<Containers> readContainerDataFromFile(String filePath) {
    List<Containers> containerList = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String portUniqueID = parts[0];
                String uniqueID = parts[1].substring(1);
                String containerType = parts[2];
                double containerWeight = Double.parseDouble(parts[3]);
                double fuelConsumptionShip = Double.parseDouble(parts[4]);
                double fuelConsumptionTruck = Double.parseDouble(parts[5]);
                if (containerType.equals("Dry storage")) {
                    // Create a DryStorage container object and add it to the list
                    DryStorage newDryStorage = new DryStorage(containerWeight, uniqueID,
                            "Dry storage", Port.getPortById(portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                    containerList.add(newDryStorage);
                } else if (containerType.equals("Open top")) {
                    // Create an OpenTop container object and add it to the list
                    OpenTop newOpenTop = new OpenTop(containerWeight, uniqueID,
                            "Open top", Port.getPortById(portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                    containerList.add(newOpenTop);
                } else if (containerType.equals("Open side")) {
                    // Create an OpenSide container object and add it to the list
                    OpenSide newOpenSide = new OpenSide(containerWeight, uniqueID,
                            "Open side", Port.getPortById(portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                    containerList.add(newOpenSide);
                } else if (containerType.equals("Refrigerated")) {
                    // Create a Refrigerated container object and add it to the list
                    Refrigerated newRefrigerated = new Refrigerated(containerWeight, uniqueID,
                            "Refrigerated", Port.getPortById(portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                    containerList.add(newRefrigerated);
                } else if (containerType.equals("Liquid")) {
                    // Create a Liquid container object and add it to the list
                    Liquid newLiquid = new Liquid(containerWeight, uniqueID,
                            "Liquid", Port.getPortById(portUniqueID), fuelConsumptionShip, fuelConsumptionTruck);
                    containerList.add(newLiquid);
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return containerList;
}
/**
 * Retrieves a container by its unique ID.
 *
 * @param containerIDToFind The unique ID of the container to retrieve.
 * @return The container object with the specified ID, or null if not found.
 */
    public static Containers getContainerByID(String containerIDToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(portContainersListFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(containerIDToFind)) {
                    double containerWeight = Double.parseDouble(parts[3]);
                    String containerType = parts[2];
                    double fuelConsumptionShip = Double.parseDouble(parts[4]);
                    double fuelConsumptionTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Dry storage")) {
                        // Create and return a DryStorage container object
                        return new DryStorage(containerWeight, containerIDToFind.substring(1),
                                "Dry storage", Port.getPortById(parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Open top")) {
                        // Create and return an OpenTop container object
                        return new OpenTop(containerWeight, containerIDToFind.substring(1),
                                "Open top", Port.getPortById(parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Open side")) {
                        // Create and return an OpenSide container object
                        return new OpenSide(containerWeight, containerIDToFind.substring(1),
                                "Open side", Port.getPortById(parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Refrigerated")) {
                        // Create and return a Refrigerated container object
                        return new Refrigerated(containerWeight, containerIDToFind.substring(1),
                                "Refrigerated", Port.getPortById(parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    } else if (containerType.equals("Liquid")) {
                        // Create and return a Liquid container object
                        return new Liquid(containerWeight, containerIDToFind.substring(1),
                                "Liquid", Port.getPortById(parts[0]), fuelConsumptionShip, fuelConsumptionTruck);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
/**
 * Retrieves a list of containers associated with a specific vehicle.
 *
 * @param vehicleID The unique ID of the vehicle.
 * @return A list of containers loaded on the vehicle, or an empty list if none are found.
 */
    public static List<Containers> getContainersInVehicleList(String vehicleID) {
        List<Containers> containersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(vehicleContainersFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equals(vehicleID)) {
                    String containerID = parts[1].substring(2);
                    String containerType = parts[2];
                    double containerWeight = Double.parseDouble(parts[3]);
                    double fuelShip = Double.parseDouble(parts[4]);
                    double fuelTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Open side")) {
                        // Create and add an OpenSide container object to the list
                        OpenSide openSide = new OpenSide(containerWeight, containerID, containerType, Vehicles.getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(openSide);
                    } else if (containerType.equals("Open top")) {
                        // Create and add an OpenTop container object to the list
                        OpenTop openTop = new OpenTop(containerWeight, containerID, containerType, Vehicles.getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(openTop);
                    } else if (containerType.equals("Dry storage")) {
                        // Create and add a DryStorage container object to the list
                        DryStorage dryStorage = new DryStorage(containerWeight, containerID, containerType, Vehicles.getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(dryStorage);
                    } else if (containerType.equals("Liquid")) {
                        // Create and add a Liquid container object to the list
                        Liquid liquid = new Liquid(containerWeight, containerID, containerType, Vehicles.getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(liquid);
                    } else if (containerType.equals("Refrigerated")) {
                        // Create and add a Refrigerated container object to the list
                        Refrigerated refrigerated = new Refrigerated(containerWeight, containerID, containerType, Vehicles.getVehicleByID(vehicleID), fuelShip, fuelTruck);
                        containersList.add(refrigerated);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return containersList;
    }

/**
 * Retrieves a container from a given file by its unique ID.
 *
 * @param containerFilePath The path to the file containing container data.
 * @param containerIDToFind The unique ID of the container to retrieve.
 * @return The container object if found; otherwise, returns null.
 */
    public static Containers getContainerByIDInVehicle(String containerFilePath, String containerIDToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(containerFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(containerIDToFind)) {
                    // Create and return the container object
                    double containerWeight = Double.parseDouble(parts[3]);
                    String containerType = parts[2];
                    if (containerType.equals("Dry storage")){
                        // Create a DryStorage container
                        DryStorage newDryStorage = new DryStorage(containerWeight, containerIDToFind.substring(1),
                                "Dry storage", Vehicles.getVehicleByID(parts[0]), 3.5, 4.6);
                        return newDryStorage;
                    } else if (containerType.equals("Open top")) {
                        // Create an OpenTop container
                        OpenTop newOpenTop = new OpenTop(containerWeight, containerIDToFind.substring(1),
                                "Open top", Vehicles.getVehicleByID(parts[0]), 2.8, 3.2);
                        return newOpenTop;
                    } else if (containerType.equals("Open side")) {
                        // Create an OpenSide container
                        OpenSide newOpenSide = new OpenSide(containerWeight, containerIDToFind.substring(1),
                                "Open side", Vehicles.getVehicleByID(parts[0]), 2.7, 3.2);
                        return newOpenSide;
                    } else if (containerType.equals("Refrigerated")) {
                        // Create a Refrigerated container
                        Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerIDToFind.substring(1),
                                "Refrigerated", Vehicles.getVehicleByID(parts[0]), 4.5, 5.4);
                        return newRefrigerated;
                    } else if (containerType.equals("Liquid")) {
                        // Create a Liquid container
                        Liquid newLiquid = new Liquid(containerWeight, containerIDToFind.substring(1),
                                "Liquid", Vehicles.getVehicleByID(parts[0]), 4.8, 5.3);
                        return newLiquid;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
 * Retrieves a list of containers in a specific port from a given file.
 *
 * @param filePath The path to the file containing container data.
 * @param portID   The unique ID of the port to filter containers by.
 * @return A list of container objects in the specified port; an empty list if none are found.
 */
    public static List<Containers> getContainersInPortList(String filePath, String portID) {
        List<Containers> containersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equals(portID)) {
                    String containerID = parts[1].substring(2);
                    String containerType = parts[2];
                    double containerWeight = Double.parseDouble(parts[3]);
                    double fuelShip = Double.parseDouble(parts[4]);
                    double fuelTruck = Double.parseDouble(parts[5]);
                    if (containerType.equals("Open side")){
                        // Create and add an OpenSide container to the list
                        OpenSide openSide = new OpenSide(containerWeight, containerID, containerType, Port.getPortById(portID), fuelShip, fuelTruck);
                        containersList.add(openSide);
                    } else if (containerType.equals("Open top")) {
                        // Create and add an OpenTop container to the list
                        OpenTop openTop = new OpenTop(containerWeight, containerID, containerType, Port.getPortById(portID), fuelShip, fuelTruck);
                        containersList.add(openTop);
                    } else if (containerType.equals("Dry storage")) {
                        // Create and add a DryStorage container to the list
                        DryStorage dryStorage = new DryStorage(containerWeight, containerID, containerType, Port.getPortById(portID), fuelShip, fuelTruck);
                        containersList.add(dryStorage);
                    } else if (containerType.equals("Liquid")) {
                        // Create and add a Liquid container to the list
                        Liquid liquid = new Liquid(containerWeight, containerID, containerType, Port.getPortById(portID), fuelShip, fuelTruck);
                        containersList.add(liquid);
                    } else if (containerType.equals("Refrigerated")) {
                        // Create and add a Refrigerated container to the list
                        Refrigerated refrigerated = new Refrigerated(containerWeight, containerID, containerType, Port.getPortById(portID), fuelShip, fuelTruck);
                        containersList.add(refrigerated);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return containersList;
    }

    /**
     * Retrieves a list of all containers from both vehicle and port container files.
     *
     * @return A list of all container objects.
     */
    public static List<Containers> getAllContainers() {
        List<Containers> containerList = new ArrayList<>();
        try (BufferedReader vehicleReader = new BufferedReader(new FileReader(vehicleContainersFileName));
            BufferedReader portReader = new BufferedReader(new FileReader(portContainersListFileName))) {
            String line;
            // Read containers from the vehicle containers file
            while ((line = vehicleReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    containerList.add(getContainerByIDInVehicle(vehicleContainersFileName, parts[1])); // Add the container line to the list
                }
            }
            // Read containers from the port containers file
            while ((line = portReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    containerList.add(getContainerByID(parts[1])); // Add the container line to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return containerList;
    }
    /**
 * Takes user input to add a container to a port.
 */
public static void inputContainerToPort() {
    String portID;
    String containerID;
    double containerWeight;
    int containerType;
    
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("Enter the port: ");
    portID = scanner.nextLine();
    
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
    
    Port findPort = Port.getPortById(portID);
    
    if (findPort == null) {
        System.out.println("Port doesn't exist.");
    } else {
        switch (containerType) {
            case 1:
                DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                        "Dry storage", findPort, 3.5, 4.6);
                Port.addContainerToPort(portID, newDryStorage);
                break;
            case 2:
                OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                        "Open top", findPort, 2.8, 3.2);
                Port.addContainerToPort(portID, newOpenTop);
                break;
            case 3:
                OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                        "Open side", findPort, 2.7, 3.2);
                Port.addContainerToPort(portID, newOpenSide);
                break;
            case 4:
                Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                        "Refrigerated", findPort, 4.5, 5.4);
                Port.addContainerToPort(portID, newRefrigerated);
                break;
            case 5:
                Liquid newLiquid = new Liquid(containerWeight, containerID,
                        "Liquid", findPort, 4.8, 5.3);
                Port.addContainerToPort(portID, newLiquid);
                break;
            default:
                System.out.println("Option not valid");
        }
    }
}

/**
 * Takes user input to add a container to a specific port.
 * 
 * @param port The port to which the container will be added.
 */
    public static void inputContainerToPortForPM(Port port) {
        String containerID;
        double containerWeight;
        int containerType;
        
        Scanner scanner = new Scanner(System.in);
        
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
        
        if (port == null) {
            System.out.println("Port doesn't exist.");
        } else {
            switch (containerType) {
                case 1:
                    DryStorage newDryStorage = new DryStorage(containerWeight, containerID,
                            "Dry storage", port, 3.5, 4.6);
                    Port.addContainerToPort(port.getUniqueID(), newDryStorage);
                    break;
                case 2:
                    OpenTop newOpenTop = new OpenTop(containerWeight, containerID,
                            "Open top", port, 2.8, 3.2);
                    Port.addContainerToPort(port.getUniqueID(), newOpenTop);
                    break;
                case 3:
                    OpenSide newOpenSide = new OpenSide(containerWeight, containerID,
                            "Open side", port, 2.7, 3.2);
                    Port.addContainerToPort(port.getUniqueID(), newOpenSide);
                    break;
                case 4:
                    Refrigerated newRefrigerated = new Refrigerated(containerWeight, containerID,
                            "Refrigerated", port, 4.5, 5.4);
                    Port.addContainerToPort(port.getUniqueID(), newRefrigerated);
                    break;
                case 5:
                    Liquid newLiquid = new Liquid(containerWeight, containerID,
                            "Liquid", port, 4.8, 5.3);
                    Port.addContainerToPort(port.getUniqueID(), newLiquid);
                    break;
                default:
                    System.out.println("Option not valid");
            }
        }
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


    @Override
    public String toString() {
        return "Containers{" +
                "containerWeight=" + containerWeight +
                ", uniqueID='" + uniqueID + '\'' +
                ", containerType='" + containerType + '\'' +
                ", requiredFuel=" + requiredFuel +
                ", currentVehicle=" + currentVehicle +
                ", fuelConsumptionShip=" + fuelConsumptionShip +
                ", fuelConsumptionTruck=" + fuelConsumptionTruck +
                '}';
    }
}
