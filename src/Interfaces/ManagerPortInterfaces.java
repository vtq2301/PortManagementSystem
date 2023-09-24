package src.Interfaces;

import src.entities.Containers.Containers;

public interface ManagerPortInterfaces {
    void start();
    void moveContainerFromPortToVehicle(String containerID, String VehicleID);
    void writeContainerFromPortToVehicle(String filePath, String vehicleID, String portID, Containers container);
    void moveContainerFromVehicleToPort(String containerUniqueID, String portUniqueID);
}
