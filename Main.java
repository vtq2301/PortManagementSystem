import src.entities.Containers.Containers;
import src.entities.Port.Port;
import src.entities.Port.Trip;
import src.entities.User.Admin;
import src.entities.User.PortManager;
import src.entities.Vehicles.Vehicles;

import java.util.List;
import java.util.Scanner;

public class Main {
    static final String portFileName = "data/Ports.txt";
    static final String portManagerFileName = "data/PortManager.txt";
    static final String portContainersListFileName = "data/PortContainersList.txt";
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    static final String tripFileName = "data/Trip.txt";
    private static String vehicleContainersFileName = "data/VehicleContainersList.txt";
    public static void main(String[] args) {
        startProgram();
    }
    public static void startProgram(){
        String line = "-------------------";
        System.out.println(line);
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Team 22");
        System.out.println("S3929202, Vu Pham Nguyen Vu");
        System.out.println("S4011912, Tran Pham Quoc Vy");
        System.out.println("S3981278, Vu Tien Quang");
        System.out.println();
        System.out.println("Welcome sunshine!");
        Trip.tripInSevenDays();
        List<Port> portList = Port.getAllPorts();
        List<Vehicles> vehiclesList = Port.readPortVehiclesList(portVehiclesListFileName);
        List<Containers> containersList = Containers.getAllContainers();
        List<Trip> tripList = Trip.getAllTrip();
        List<PortManager> portManagerList = PortManager.getAllPortManagers();
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an action (1-2): ");
            option = scanner.nextInt();
            if (option == 1) {
                login();
            }
        } while (option != 2);
    }
    public static void login() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---------- Main Menu ----------");
            System.out.println("1. Admin");
            System.out.println("2. Port Manager");
            System.out.println("3. Exit");
            System.out.print("Choose an action (1-3): ");
            option = scanner.nextInt();
            if (option == 1) {
                String username;
                String password;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Username: ");
                username = scanner1.nextLine();
                System.out.println("Password: ");
                password = scanner1.nextLine();
                Admin admin = new Admin("Ad001","AdminTest", "RMIT@2023", "Admin");
                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)){
                    Admin.start();
                } else {
                    System.out.println("Wrong password or username");
                }

            } else if (option == 2) {
                List<PortManager> portManagerList = PortManager.getAllPortManagers();
                PortManager portManager;
                String username;
                String password;
                String portID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Port ID: ");
                portID = scanner1.nextLine();
                for (PortManager portManagers:portManagerList){
                    if (portManagers.getPort().getUniqueID().equals(portID)){
                        portManager=portManagers;
                        System.out.print("Username: ");
                        username = scanner1.nextLine();
                        System.out.print("Password: ");
                        password = scanner1.nextLine();
                        if (portManager.getUsername().equals(username) && portManager.getPassword().equals(password)){
                            PortManager.start(portManager);
                        } else {
                            System.out.println("Wrong password or username");
                        }
                    }
                }
            } else {
                System.out.println("Invalid input! You should enter 1 or 2!");
            }
        } while (option != 3);
        scanner.close();
    }
    
}
