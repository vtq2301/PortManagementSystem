package src.entities.Vehicles;
import src.entities.Containers.Containers;
import src.entities.Containers.Refrigerated;
import src.entities.Port.*;

import java.util.List;

public class ReeferTruck extends Vehicles{
    public ReeferTruck(String name, String uniqueID, String vehicleType, double currentFuel, double carryingCapacity, double currentCarryingCapacity, double fuelCapacity, List<Containers> containersList, Port currentPort){
        super(name, "tr" + uniqueID,"Reefer Truck", currentFuel, carryingCapacity,currentCarryingCapacity, fuelCapacity, containersList, currentPort);
    }

    @Override
    public boolean canAddContainers(Containers containers) {
        if(containers.getClass() == Refrigerated.class){
            return true;
        } else {
            System.out.println("Container type invalid");
            return false;
        }
    }
}
