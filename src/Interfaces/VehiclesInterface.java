package src.interfaces;

import java.util.List;

import src.entities.Containers.Containers;
import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;

public interface VehiclesInterface {
    boolean canAddContainers(Containers containers);

    void removeContainers(Containers containers);

    boolean moveToPort(Port port);

    boolean overweight(Containers containers);

    void moveVehicleToPort(Port port);

    
}
