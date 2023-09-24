package src.entities.Port;

import src.entities.Containers.Containers;
import src.entities.Vehicles.*;
import src.interfaces.PortInterface;

import java.io.*;
import java.util.*;

public class Port implements PortInterface{
    static final String portFileName = "data/Ports.txt";
    static final String portContainersListFileName = "data/PortContainersList.txt";
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    private String uniqueID;
    private String name;
    private double latitude;
    private double longitude;
    private int storingCapacity;
    private double currentCapacity;
    private boolean landingAbility;
    private List<Containers> containerList;
    private List<Vehicles> vehicleList;
    private List<Trip> tripList;

    public Port(String uniqueID, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility, List<Containers> containerList, List<Vehicles> vehicleList, List<Trip> tripList, double currentCapacity) {
        this.uniqueID = "p"+ uniqueID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containerList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
        this.tripList = new ArrayList<>();
        this.currentCapacity = currentCapacity;
    }

    public Port(){

    }
    // Getters and Setters of unique ID
    public String getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
    
    // Getters and Setters of name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters of latitude
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getters and Setters of Longitude
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Getters and Setters of storing capacity
    public int getStoringCapacity() {
        return storingCapacity;
    }
    public void setStoringCapacity(int storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    // Getters and Setters of landing ability
    public boolean isLandingAbility() {
        return landingAbility;
    }
    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }
    //get number of containers
    public int getNumberOfContainers(){
        return containerList.size();
    }
    //update the current capacity of the port
    public void updateCapacity(Containers container){
        currentCapacity += container.getContainerWeight();
    }
    //add container
    public void addContainer(Containers container){
        if (currentCapacity + container.getContainerWeight() > storingCapacity){
            System.out.println("Storing capacity overload");
        } else{
            updateCapacity(container);
            containerList.add(container);
        }
    }

    public void removeContainer(Containers container){
        containerList.remove(container);
    }
    //add vehicles 
    public void addVehicles(Vehicles vehicle){
        if (vehicle.getClass() == Ship.class){
            vehicleList.add(vehicle); 
        } else if (landingAbility = true) {
            vehicleList.add(vehicle);
        } else {
            System.out.println("Can't land a truck in this port");
        }

    }

    public double getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public List<Containers> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Containers> containerList) {
        this.containerList = containerList;
    }

    public List<Vehicles> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicles> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }


    public void removeVehicle(Vehicles vehicle){
        vehicleList.remove(vehicle);
    }
    //get number of vehicles
    public int getNumberOfVehicles(){
        return vehicleList.size();
    }
    //add trip
    public void addTrip(Trip trip){
        tripList.add(trip);
    }
    //Calculate distance from a port to another port
    public double calculateDistance(Port anotherPort) {
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        double lat2 = anotherPort.getLatitude();
        double lon2 = anotherPort.getLongitude();
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    public boolean isOverWeight(Containers containers){
        if (this.currentCapacity + containers.getContainerWeight() < this.storingCapacity){
            System.out.println(this.currentCapacity + containers.getContainerWeight());
            System.out.println(this.storingCapacity);
            return false;
        }
        return true;
    }
    public static List<Port> getAllPorts(){
        List<Port> portList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String uniqueID = parts[0].substring(1);
                    String name = parts[1];
                    double latitude = Double.parseDouble(parts[2]);
                    double longitude = Double.parseDouble(parts[3]);
                    int storingCapacity = Integer.parseInt(parts[4]);
                    double currentCapacity = Double.parseDouble(parts[5]);
                    boolean landingAbility = Boolean.parseBoolean(parts[6]);
                    Port port = new Port(uniqueID, name, latitude, longitude, storingCapacity, landingAbility, null, null, null, currentCapacity);
                    portList.add(port);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portList;
    }
    public static Port getPortById(String portID){
        List<Port> portList = getAllPorts();
        for (Port port : portList) {
            if (port.getUniqueID().equals(portID)) {
                return port;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "Port{" +
                "uniqueID='" + uniqueID + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", storingCapacity=" + storingCapacity +
                ", currentCapacity=" + currentCapacity +
                ", landingAbility=" + landingAbility +
                ", containerList=" + containerList +
                ", vehicleList=" + vehicleList +
                ", tripList=" + tripList +
                '}';
    }
    public static void addContainerToPort(String portUniqueID, Containers container) {
        List<Containers> containersList = Containers.readContainerDataFromFile(portContainersListFileName);
        Port port = Port.getPortById(portUniqueID);
        boolean repeated = false;
        for (Containers containers:containersList){
            if (containers.getUniqueID().equals(container.getUniqueID())){
                repeated = true;
            }
        } if (repeated){
            System.out.println("Can't add due to duplicated container IDs");
        } else if (port.isOverWeight(container)) {
            System.out.println(port.getCurrentCapacity()+container.getContainerWeight());
            System.out.println(port.getStoringCapacity());
            System.out.println("Port overweight, can't add container.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(portContainersListFileName, true))) {
                writer.write(portUniqueID + ",");
                writer.write(container.getUniqueID() + ",");
                writer.write(container.getContainerType() + ",");
                writer.write(container.getContainerWeight() + ",");
                writer.write(container.getFuelConsumptionShip() + ",");
                writer.write(container.getFuelConsumptionTruck()+"");
                writer.newLine();
                updatePortWeight(portUniqueID, port.getCurrentCapacity()+container.getContainerWeight());
                System.out.println("Container data has been appended to the file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updatePortWeight(String portIDToUpdate, double newValue) {
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(portIDToUpdate)) {
                    if (parts.length > 5) {
                        parts[5] = Double.toString(newValue);
                    }
                }
                updatedLines.add(String.join(",", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portFileName))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeContainerFromPort(String containerIDToDelete) {
        List<String> lines = new ArrayList<>();
        Containers containers = Containers.getContainerByID(containerIDToDelete);
        if (containers == null){
            System.out.println("Container doesn't exist");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(portContainersListFileName))) {
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

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(portContainersListFileName))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            updatePortWeight(containers.getCurrentPort().getUniqueID(), containers.getCurrentPort().getCurrentCapacity()-containers.getContainerWeight());
            System.out.println("Container with ID " + containerIDToDelete + " has been deleted.");
        }
    }
    public static void addPort() {
        Scanner scanner = new Scanner(System.in);
        String uniqueID;
        String name;
        double latitude;
        double longitude;
        int storingCapacity;
        double currentCapacity;
        boolean landingAbility;
        System.out.print("Port unique ID: \n");
        uniqueID=scanner.nextLine();
        System.out.print("Port name: \n");
        name=scanner.nextLine();
        System.out.print("Port latitude: \n");
        latitude = scanner.nextDouble();
        System.out.print("Port longitude: \n");
        longitude = scanner.nextDouble();
        System.out.print("Port storing capacity: \n");
        storingCapacity = scanner.nextInt();
        System.out.print("Port current capacity: \n");
        currentCapacity = scanner.nextDouble();
        System.out.print("Port landing ability (true/false): \n");
        landingAbility = scanner.nextBoolean();
        Port newPort = new Port(uniqueID, name, latitude, longitude, storingCapacity, landingAbility, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), currentCapacity);
        adminWritePort(newPort,portFileName);
    }
    public static void adminWritePort(Port port, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            Set<String> uniquePortIDs = new HashSet<>(); // Maintain a set of unique portIDs

            // Read the existing content of the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);

                    // Extract portID from existing lines and add it to the set
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].startsWith("")) {
                        uniquePortIDs.add(parts[0]);
                    }
                }
            }
            if (!uniquePortIDs.contains(port.getUniqueID())) {
                StringBuilder portLine = new StringBuilder();
                portLine.append(port.getUniqueID()).append(",");
                portLine.append(port.getName()).append(",");
                portLine.append(port.getLatitude()).append(",");
                portLine.append(port.getLongitude()).append(",");
                portLine.append(port.getStoringCapacity()).append(",");
                portLine.append(port.getCurrentCapacity()).append(",");
                portLine.append(port.isLandingAbility());
                lines.add(portLine.toString());
                // Add the new portID to the set
                uniquePortIDs.add(port.getUniqueID());
            }

            // Write the updated lines (including existing and new data) back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removePort(String portIDToDelete) {
        List<String> portLines = new ArrayList<>();
        try (BufferedReader portReader = new BufferedReader(new FileReader(portFileName))) {
            String line;
            while ((line = portReader.readLine()) != null) {
                if (line.startsWith(portIDToDelete)) {
                    continue;
                }
                portLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> vehiclesLines = new ArrayList<>();
        try (BufferedReader vehiclesReader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            while ((line = vehiclesReader.readLine()) != null) {
                if (line.startsWith(portIDToDelete)) {
                    vehiclesLines.add(line.replaceFirst(portIDToDelete, "null"));
                }else {
                    vehiclesLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> containerLines = new ArrayList<>();
        try (BufferedReader containersReader = new BufferedReader(new FileReader(portContainersListFileName))) {
            String line;
            while ((line = containersReader.readLine()) != null) {
                if (line.startsWith(portIDToDelete)) {
                    containerLines.add(line.replaceFirst(portIDToDelete, "null"));
                } else {
                    containerLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter portWriter = new BufferedWriter(new FileWriter(portFileName))) {
            for (String line : portLines) {
                portWriter.write(line);
                portWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter vehiclesWriter = new BufferedWriter(new FileWriter(portVehiclesListFileName))) {
            for (String line : vehiclesLines) {
                vehiclesWriter.write(line);
                vehiclesWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter containersWriter = new BufferedWriter(new FileWriter(portContainersListFileName))) {
            for (String line : containerLines) {
                containersWriter.write(line);
                containersWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writePortVehiclesList(Vehicles vehicle) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portVehiclesListFileName, true))) {
            String line = vehicle.getCurrentPort().getUniqueID() + ","
                    + vehicle.getName() + ","
                    + vehicle.getUniqueID() + ","
                    + vehicle.getCurrentFuel() + ","
                    + vehicle.getCarryingCapacity() + ","
                    + vehicle.getCurrentCarryingCapacity() + ","
                    + vehicle.getFuelCapacity() + ","
                    + vehicle.getClass();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Vehicles> readPortVehiclesList(String filePath) {
        List<Vehicles> vehicleList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String portUniqueID = parts[0];
                    String name = parts[1];
                    String uniqueID = parts[2];
                    double currentFuel = Double.parseDouble(parts[3]);
                    double carryingCapacity = Double.parseDouble(parts[4]);
                    double currentCarryingCapacity = Double.parseDouble(parts[5]);
                    double fuelCapacity = Double.parseDouble(parts[6]);
                    String classType = parts[7];
                    if (classType.equals("class Vehicles.BasicTruck")){
                        BasicTruck basicTruck = new BasicTruck(name, uniqueID, "Basic Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, Vehicles.getContainersInVehicleList(uniqueID), getPortById(portUniqueID));
                        vehicleList.add(basicTruck);
                    } else if (classType.equals("class Vehicles.ReeferTruck")){
                        ReeferTruck reeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, Vehicles.getContainersInVehicleList(uniqueID), getPortById(portUniqueID));
                        vehicleList.add(reeferTruck);
                    } else if (classType.equals("class Vehicles.TankerTruck")){
                        TankerTruck tankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, Vehicles.getContainersInVehicleList(uniqueID), getPortById(portUniqueID));
                        vehicleList.add(tankerTruck);
                    } else if (classType.equals("class Vehicles.Ship")){
                        Ship ship = new Ship(name, uniqueID, "Ship", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, Vehicles.getContainersInVehicleList(uniqueID), getPortById(portUniqueID));
                        vehicleList.add(ship);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }
    public static void addVehicleToPort() {
        List<String> existUniqueIDs = Vehicles.ListOfVehicleIDs();
        boolean repeatedIDs = false;
        Scanner scanner = new Scanner(System.in);
        String name;
        String uniqueID;
        int vehicleType;
        double carryingCapacity;
        double fuelCapacity;
        String portID;
        System.out.println("Current Port ID: ");
        portID = scanner.nextLine();
        System.out.println("Name of Vehicle: ");
        name = scanner.nextLine();
        System.out.println("Unique ID of Vehicle: ");
        uniqueID = scanner.nextLine();
        for (String IDs:existUniqueIDs){
            if (uniqueID.equals(IDs)){
                repeatedIDs = true;
            }
        }
        System.out.println(repeatedIDs);
        System.out.println("Carrying capacity of Vehicle: ");
        carryingCapacity = scanner.nextDouble();
        System.out.println("Fuel capacity of Vehicle: ");
        fuelCapacity = scanner.nextDouble();
        System.out.println("Type of Vehicle");
        System.out.println("1. Basic Truck");
        System.out.println("2. Tanker Truck");
        System.out.println("3. Reefer Truck");
        System.out.println("4. Ship");
        System.out.println("Pleas choose from 1-4: ");
        vehicleType = scanner.nextInt();
        List<Containers> newContainerList = new ArrayList<>();
        if (vehicleType == 1){
            Port currentPort = getPortById(portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } else if (!currentPort.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } else if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                BasicTruck newBasicTruck = new BasicTruck(name, uniqueID, "Basic Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                currentPort.addVehicles(newBasicTruck);
                newBasicTruck.setCurrentPort(currentPort);
                writePortVehiclesList(newBasicTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 2) {
            Port currentPort = getPortById(portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } if (!currentPort.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                TankerTruck newTankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                currentPort.addVehicles(newTankerTruck);
                newTankerTruck.setCurrentPort(currentPort);
                writePortVehiclesList(newTankerTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 3) {
            Port currentPort = getPortById(portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } if (!currentPort.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else{
                ReeferTruck newReeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                currentPort.addVehicles(newReeferTruck);
                newReeferTruck.setCurrentPort(currentPort);
                writePortVehiclesList(newReeferTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 4) {
            Port currentPort = getPortById(portID);
            if(currentPort == null){
                System.out.println("Port doesn't exist");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                Ship newShip = new Ship(name, uniqueID, "Ship",0, carryingCapacity, 0, fuelCapacity, newContainerList, currentPort);
                System.out.println(newShip);
                currentPort.addVehicles(newShip);
                newShip.setCurrentPort(currentPort);
                writePortVehiclesList(newShip);
                System.out.println("Vehicle added successfully");
            }
        } else {
            System.out.println("Option not exist, please try again.");
        }
    }
    public static void addVehicleToPortForPM(Port port) {
        List<String> existUniqueIDs = Vehicles.ListOfVehicleIDs();
        boolean repeatedIDs = false;
        Scanner scanner = new Scanner(System.in);
        String name;
        String uniqueID;
        int vehicleType;
        double carryingCapacity;
        double fuelCapacity;
        System.out.println("Name of Vehicle: ");
        name = scanner.nextLine();
        System.out.println("Unique ID of Vehicle: ");
        uniqueID = scanner.nextLine();
        for (String IDs:existUniqueIDs){
            if (uniqueID.equals(IDs)){
                repeatedIDs = true;
            }
        }
        System.out.println(repeatedIDs);
        System.out.println("Carrying capacity of Vehicle: ");
        carryingCapacity = scanner.nextDouble();
        System.out.println("Fuel capacity of Vehicle: ");
        fuelCapacity = scanner.nextDouble();
        System.out.println("Type of Vehicle");
        System.out.println("1. Basic Truck");
        System.out.println("2. Tanker Truck");
        System.out.println("3. Reefer Truck");
        System.out.println("4. Ship");
        System.out.println("Pleas choose from 1-4: ");
        vehicleType = scanner.nextInt();
        List<Containers> newContainerList = new ArrayList<>();
        if (vehicleType == 1){
            if(port == null){
                System.out.println("Port doesn't exist");
            } else if (!port.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } else if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                BasicTruck newBasicTruck = new BasicTruck(name, uniqueID, "Basic Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, port);
                port.addVehicles(newBasicTruck);
                newBasicTruck.setCurrentPort(port);
                writePortVehiclesList(newBasicTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 2) {
            if(port == null){
                System.out.println("Port doesn't exist");
            } if (!port.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                TankerTruck newTankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, port);
                port.addVehicles(newTankerTruck);
                newTankerTruck.setCurrentPort(port);
                writePortVehiclesList(newTankerTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 3) {
            if(port == null){
                System.out.println("Port doesn't exist");
            } if (!port.isLandingAbility()){
                System.out.println("Port doesn't support landing");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else{
                ReeferTruck newReeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck",0, carryingCapacity, 0, fuelCapacity, newContainerList, port);
                port.addVehicles(newReeferTruck);
                newReeferTruck.setCurrentPort(port);
                writePortVehiclesList(newReeferTruck);
                System.out.println("Vehicle added successfully");
            }
        } else if (vehicleType == 4) {
            if(port == null){
                System.out.println("Port doesn't exist");
            } if (repeatedIDs){
                System.out.println("Vehicle IDs existed");
            }
            else {
                Ship newShip = new Ship(name, uniqueID, "Ship",0, carryingCapacity, 0, fuelCapacity, newContainerList, port);
                System.out.println(newShip);
                port.addVehicles(newShip);
                newShip.setCurrentPort(port);
                writePortVehiclesList(newShip);
                System.out.println("Vehicle added successfully");
            }
        } else {
            System.out.println("Option not exist, please try again.");
        }
    }
    public static void removeVehicleFromPort(String vehicleIDToDelete) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2 && !parts[2].equals(vehicleIDToDelete)) {
                    lines.add(line);
                }
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
        String containersFilePath = "data/VehicleContainersList.txt"; // Adjust the path as needed
        List<String> containerLines = new ArrayList<>();
        try (BufferedReader containerReader = new BufferedReader(new FileReader(containersFilePath))) {
            String containerLine;
            while ((containerLine = containerReader.readLine()) != null) {
                String[] containerParts = containerLine.split(",");
                if (containerParts.length > 2 && !containerParts[0].equals(vehicleIDToDelete)) {
                    containerLines.add(containerLine);
                } else {
                    containerLines.add(containerLine.replaceFirst(vehicleIDToDelete, "null"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter containerWriter = new BufferedWriter(new FileWriter(containersFilePath))) {
            for (String containerLine : containerLines) {
                containerWriter.write(containerLine);
                containerWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Vehicle with ID " + vehicleIDToDelete + " has been deleted.");
    }
    public Map<String, List<Vehicles>> readPortVehicles() {
        Map<String, List<Vehicles>> portVehiclesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(portVehiclesListFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String portID = parts[0];
                    String name = parts[1];
                    String uniqueID = parts[2].substring(2);
                    double currentFuel = Double.parseDouble(parts[3]);
                    double carryingCapacity = Double.parseDouble(parts[4]);
                    double currentCarryingCapacity = Double.parseDouble(parts[5]);
                    double fuelCapacity = Double.parseDouble(parts[6]);
                    String classType = parts[7];
                    if (classType.equals("class Vehicles.BasicTruck")){
                        BasicTruck basicTruck = new BasicTruck(name, uniqueID, "Basic Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortById(portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(basicTruck);
                    } else if (classType.equals("class Vehicles.ReeferTruck")){
                        ReeferTruck reeferTruck = new ReeferTruck(name, uniqueID, "Reefer Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortById(portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(reeferTruck);
                    } else if (classType.equals("class Vehicles.TankerTruck")){
                        TankerTruck tankerTruck = new TankerTruck(name, uniqueID, "Tanker Truck", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortById(portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(tankerTruck);
                    } else if (classType.equals("class Vehicles.Ship")){
                        Ship ship = new Ship(name, uniqueID, "Ship", currentFuel,
                                carryingCapacity, currentCarryingCapacity, fuelCapacity, null, getPortById(portID));
                        if (!portVehiclesMap.containsKey(portID)) {
                            portVehiclesMap.put(portID, new ArrayList<>());
                        }
                        portVehiclesMap.get(portID).add(ship);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portVehiclesMap;
    }
}
