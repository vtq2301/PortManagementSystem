package src.entities.Manager;
import src.entities.Port.Port;
import src.entities.User.*;
import src.Interfaces.*;
import java.util.Scanner;

public class ManagerPortManager implements ManagerPortManagerInterfaces{
    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("2. Assign port manager");
            System.out.println("3. Remove port manager");
            System.out.println("4. Return");
            System.out.println("Please choose (1-3): ");
            option = scanner.nextInt();
            if (option==1){
                String portID;
                String userID;
                String username;
                String password;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter the port that you want the manager to be assigned:");
                portID = scanner1.nextLine();
                System.out.println("UserId of the manager:");
                userID = scanner1.nextLine();
                System.out.println("Username of the manager: ");
                username = scanner1.nextLine();
                System.out.println("Password of the manager: ");
                password = scanner1.nextLine();
                PortManager newPortManager = new PortManager(userID, username, password, "Port Manager", Port.getPortById(portID));
                Admin.assignPortManager(portID, newPortManager);
            } else if (option==2) {
                String managerID;
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Enter manager Id to delete: ");
                managerID = scanner1.nextLine();
                Admin.removeManager(managerID);
            } else if (option==3) {
                Admin.start();
            } else {
                System.out.println("Please enter a valid number");
            }
        } while (option!=4);
    }
}
