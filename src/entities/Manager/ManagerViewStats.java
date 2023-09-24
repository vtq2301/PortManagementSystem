package src.entities.Manager;
import src.interfaces.*;
import src.entities.Containers.Containers;
import src.entities.Port.*;
import src.entities.User.Admin;
import src.entities.User.PortManager;
import src.entities.Vehicles.Vehicles;
import src.interfaces.ManagerViewStatsInterfaces;

import java.util.List;
import java.util.Scanner;
public class ManagerViewStats implements ManagerViewStatsInterfaces{
    static final String portContainersListFileName = "data/PortContainersList.txt";
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("1. View ports");
            System.out.println("2. View  all containers");
            System.out.println("3. View  all vehicles");
            System.out.println("4. View all containers in a port");
            System.out.println("5. View all containers in a vehicle");
            System.out.println("6. View all port managers");
            System.out.println("7. View all trips");
            System.out.println("8. View all trips in a given day (Departure)");
            System.out.println("9. View all trips in a given day (Arrival)");
            System.out.println("10. View all trips from day A to day B");
            System.out.println("11. Return");
            System.out.println("Please choose (1-11): ");
            option = scanner.nextInt();
            if (option==1){
                List<Port> newPortList = Port.getAllPorts();
                for (Port port:newPortList){
                    System.out.println(port.getUniqueID());
                }
            } else if (option==2) {
                List<Containers> containersList = Containers.getAllContainers();
                for (Containers containers:containersList){
                    System.out.println(containers.getUniqueID());
                }
            } else if (option==3) {
                List<Vehicles> vehiclesList = Port.readPortVehiclesList(portVehiclesListFileName);
                for (Vehicles vehicles:vehiclesList){
                    System.out.printf("%s %s at port %s \n",vehicles.getVehicleType(), vehicles.getUniqueID().substring(2), vehicles.getCurrentPort().getUniqueID());
                }
            } else if (option==4) {
                Scanner scanner1 = new Scanner(System.in);
                String portID;
                System.out.println("Enter the port:");
                portID = scanner1.nextLine();
                List<Containers> containersList = Containers.getContainersInPortList(portContainersListFileName, portID);
                for (Containers containers:containersList){
                    System.out.println("Container "+ containers.getUniqueID()+" in port " + portID);
                }
            } else if (option==5) {
                Scanner scanner1 = new Scanner(System.in);
                String vehicleID;
                System.out.println("Enter the port:");
                vehicleID = scanner1.nextLine();
                List<Containers> containersList = Containers.getContainersInVehicleList(vehicleID);
                for (Containers containers:containersList){
                    System.out.println("Container "+ containers.getUniqueID()+" in vehicle " + vehicleID);
                }
            } else if (option==6) {
                List<PortManager> portManagerList = Admin.getAllPortManager();
                for (PortManager portManager:portManagerList){
                    System.out.println("PortManagerID: " + portManager.getUserID());
                    System.out.println("Port: " + portManager.getPort().getUniqueID());
                    System.out.println("");
                }
            } else if (option==7) {
                List<Trip> tripList = Trip.getAllTrip();
                System.out.println(tripList);
                for (Trip trip:tripList){
                    System.out.println(trip.getTripID());
                }
            } else if (option==8) {
                String dateArrival;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter date arrival in format (yyyy-mm-dd)");
                dateArrival = scanner1.nextLine();
                List<Trip> trips = Trip.filterTripsArrivalDate(dateArrival);
                for (Trip trip : trips) {
                    System.out.println(trip.getTripID());
                }
            } else if (option==9) {
                String dateDeparture;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter date departure in format (yyyy-mm-dd)");
                dateDeparture = scanner1.nextLine();
                List<Trip> trips = Trip.filterTripsDepartureDate(dateDeparture);
                for (Trip trip : trips) {
                    System.out.println(trip.getTripID());
                }
            } else if (option==10) {
                String startDateStr;
                String endDateStr;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Please enter the start date (yyyy-MM-dd: ");
                startDateStr = scanner1.nextLine();
                System.out.println("Please enter the end date (yyyy-MM-dd: ");
                endDateStr = scanner1.nextLine();
                System.out.println(Trip.filterTripsByDateRange(startDateStr,endDateStr));
            } else if (option==11) {
                Admin.start();
            }
        } while (option!=11);
    }
}
