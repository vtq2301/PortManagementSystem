package src.entities.Manager;
import src.entities.Containers.Containers;
import src.entities.Port.*;
import src.entities.User.Admin;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ManagerTripInterfaces;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ManagerTrip implements ManagerTripInterfaces{
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. Move Vehicle to another port");
            System.out.println("2. Update arrival trip");
            System.out.println("3. Return");
            System.out.println("Please choose (1-3): ");
            option = scanner.nextInt();
            if (option==1){
                // Option to move a vehicle to another port
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                String portID;
                System.out.println("Enter vehicleID: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter portID: ");
                portID = scanner1.nextLine();
                moveVehicleToPort(vehicleID, portID);
            } else if (option==2) {
                // Option to update an arrival trip
                Scanner scanner1 = new Scanner(System.in);
                String tripID;
                System.out.println("Enter the trip that needed to be updated: ");
                tripID = scanner1.nextLine();
                Trip.updateTrip(tripID);
            } else if (option==3) {
                // Return to the previous menu
                Admin.start();
            } else {
                // Handle an invalid input
                System.out.println("Please enter a valid number");
            }
        } while (option!=6); // Continue the loop until the user chooses to return
    }
    
    @Override
public void moveVehicleToPort(String vehicleID, String portID) {
    // Retrieve the vehicle based on its unique ID
    Vehicles vehicles = Vehicles.getVehicleByID(vehicleID);
    System.out.println(portID);
    double totalFuel = 0;
    double distance;
    if (vehicles == null){
        // If the vehicle doesn't exist, print an error message
        System.out.println("Vehicle doesn't exist.");
    }
    else {
        // Calculate the distance between the current port and the destination port
        distance = vehicles.getCurrentPort().calculateDistance(Port.getPortById(portID));
        Port portToMove = Port.getPortById(portID);
        Date date = new Date();
        if (portToMove == null){
            // If the destination port doesn't exist, print an error message
            System.out.println("The port to move the vehicle doesn't exist.");
        } else {
            // Retrieve a list of containers currently in the vehicle
            List<Containers> containersList = Containers.getContainersInVehicleList(vehicleID);
            for (Containers containers:containersList){
                System.out.println(containers.getContainerWeight());
                // Calculate the total fuel consumption based on container weight and vehicle type
                if (vehicles.getVehicleType().equals("Ship")){
                    totalFuel += containers.getContainerWeight() * containers.getFuelConsumptionShip();
                }
                else {
                    totalFuel += containers.getContainerWeight() * containers.getFuelConsumptionTruck();
                }
            }
            // Calculate the total fuel required for the journey
            totalFuel = totalFuel * distance;
            if (vehicles.getVehicleType().equals("Ship")){
                if (totalFuel > vehicles.getCurrentFuel()){
                    // If there's not enough fuel, print an error message
                    System.out.println("Not enough fuel");
                }else {
                    // Move the vehicle to the destination port and create a trip record
                    Admin.writeMoveVehicleToPort(portVehiclesListFileName, vehicleID, "null");
                    Trip trip = new Trip("t001",vehicles, date, null, vehicles.getCurrentPort(), portToMove, null);
                    Trip.addTrip(trip, vehicles.getCurrentPort(), totalFuel);
                    System.out.println("Successfully moved");
                }
            } else {
                if (totalFuel < vehicles.getCurrentFuel() && portToMove.isLandingAbility()){
                    // Move the vehicle to the destination port and create a trip record
                    Admin.writeMoveVehicleToPort(portVehiclesListFileName, vehicleID, "null");
                    Trip trip = new Trip("t001", vehicles, date, null, vehicles.getCurrentPort(), portToMove, null);
                    Trip.addTrip(trip, vehicles.getCurrentPort(), totalFuel);
                    System.out.println("Successfully moved");
                } else if (!portToMove.isLandingAbility()){
                    // If the port doesn't support landing, print an error message
                    System.out.println("Landing not supported");
                } else {
                    // If there's not enough fuel, print an error message
                    System.out.println("Not enough fuel");
                }
            }
        }
    }
}

}
