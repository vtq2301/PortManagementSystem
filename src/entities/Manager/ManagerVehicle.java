package src.entities.Manager;
import src.entities.Containers.Containers;
import src.entities.Port.*;
import src.entities.User.*;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ManagerVehicleInterfaces;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ManagerVehicle implements ManagerVehicleInterfaces{
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("----------  Vehicle Admin Menu ----------");
            System.out.println("1. Add vehicle to port");
            System.out.println("2. Remove vehicle");
            System.out.println("3. Refuel vehicle");
            System.out.println("4. Return");
            System.out.println("Please choose (1-4): ");
            option = scanner.nextInt();
            if (option==1){
                Port.addVehicleToPort();
            } else if (option==2) {
                String vehicleToDelete;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Vehicles that you want to delete: ");
                vehicleToDelete = scanner1.nextLine();
                Port.removeVehicleFromPort(vehicleToDelete);
            } else if (option==3) {
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                double fuel;
                System.out.println("Enter VehicleID that needed to be refueled: ");
                vehicleID = scanner1.nextLine();
                System.out.println("Enter the amount of fuel: ");
                fuel = scanner1.nextDouble();
                Vehicles.refuelVehicle(vehicleID, fuel);
            } else if (option==4) {
                Admin.start();
            } else {
                System.out.println("Please input a valid number");
            }
        } while (option!=5);
    }


}
