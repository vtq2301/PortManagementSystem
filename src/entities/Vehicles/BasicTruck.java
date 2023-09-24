package src.entities.Vehicles;
import src.entities.Containers.Containers;
import src.entities.Containers.DryStorage;
import src.entities.Containers.OpenSide;
import src.entities.Containers.OpenTop;
import src.entities.Port.*;

import java.util.List;

public class BasicTruck extends Vehicles{
    public BasicTruck(String name, String uniqueID, String vehicleType, double currentFuel, double carryingCapacity, double currentCarryingCapacity, double fuelCapacity, List<Containers> containersList, Port currentPort){
        super(name, "tr" + uniqueID,"Basic Truck", currentFuel, carryingCapacity,currentCarryingCapacity, fuelCapacity, containersList, currentPort);
    }

    @Override
    public boolean canAddContainers(Containers containers) {
        if(containers.getClass() == DryStorage.class || containers.getClass() == OpenTop.class || containers.getClass() == OpenSide.class){
            return true;
        } else {
            return false;
        }
    }
}
