package src.entities.Manager;
import src.Interfaces.ManagerTripInterfaces;
import src.entities.Containers.Containers;
import src.entities.Port.*;
import src.entities.User.Admin;
import src.entities.Vehicles.Vehicles;

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
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                String portID;
                System.out.println("Enter vehicleID: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter portID: ");
                portID = scanner1.nextLine();
                moveVehicleToPort(vehicleID, portID);
            } else if (option==2) {
                Scanner scanner1 = new Scanner(System.in);
                String tripID;
                System.out.println("Enter the trip that needed to be updated: ");
                tripID = scanner1.nextLine();
                Trip.updateTrip(tripID);
            } else if (option==3) {
                Admin.start();
            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option!=6);
    }
    @Override
    public void moveVehicleToPort(String vehicleID, String portID) {
        Vehicles vehicles = Vehicles.getVehicleByID(vehicleID);
        System.out.println(portID);
        double totalFuel = 0;
        double distance;
        if (vehicles == null){
            System.out.println("Vehicle doesn't exist.");
        }
        else {
            distance = vehicles.getCurrentPort().calculateDistance(Port.getPortById(portID));
            Port portToMove = Port.getPortById(portID);
            Date date = new Date();
            if (portToMove == null){
                System.out.println("The port to move the vehicle doesn't exist.");
            } else {
                List<Containers> containersList = Containers.getContainersInVehicleList(vehicleID);
                for (Containers containers:containersList){
                    System.out.println(containers.getContainerWeight());
                    if (vehicles.getVehicleType().equals("Ship")){
                        totalFuel += containers.getContainerWeight() * containers.getFuelConsumptionShip();
                    }
                    else {
                        totalFuel += containers.getContainerWeight() * containers.getFuelConsumptionTruck();
                    }
                }
                totalFuel = totalFuel*distance;
                if (vehicles.getVehicleType().equals("Ship")){
                    if (totalFuel > vehicles.getCurrentFuel()){
                        System.out.println("Not enough fuel");
                    }else {
                        Admin.writeMoveVehicleToPort(portVehiclesListFileName, vehicleID, "null");
                        Trip trip = new Trip("t001",vehicles, date, null, vehicles.getCurrentPort(), portToMove, null);
                        Trip.addTrip(trip, vehicles.getCurrentPort(), totalFuel);
                        System.out.println("Successfully moved");
                    }
                } else {
                    if (totalFuel < vehicles.getCurrentFuel() && portToMove.isLandingAbility()){
                        Admin.writeMoveVehicleToPort(portVehiclesListFileName, vehicleID, "null");
                        Trip trip = new Trip("t001", vehicles, date, null, vehicles.getCurrentPort(), portToMove, null);
                        Trip.addTrip(trip, vehicles.getCurrentPort(), totalFuel);
                        System.out.println("Successfully moved");
                    } else if (!portToMove.isLandingAbility()){
                        System.out.println("Landing not supported");
                    } else {
                        System.out.println("Not enough fuel");
                    }
                }
            }
        }
    }
}
