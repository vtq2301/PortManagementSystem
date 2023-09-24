package src.entities.Vehicles;

import src.entities.Containers.Containers;
import src.entities.Port.*;

import java.util.List;

public class Ship extends Vehicles{
    private double requiredFuel;
    public Ship(String name, String uniqueID, String vehicleType, double currentFuel, double carryingCapacity, double currentCarryingCapacity, double fuelCapacity, List<Containers> containersList, Port currentPort){
        super(name, "sh" + uniqueID,"Ship", currentFuel, carryingCapacity,currentCarryingCapacity, fuelCapacity, containersList, currentPort);
    }

    @Override
    public boolean canAddContainers(Containers containers) {
        return true;
    }
}
