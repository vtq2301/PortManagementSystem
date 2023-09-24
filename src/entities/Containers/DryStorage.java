package src.entities.Containers;

import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;

public class DryStorage extends Containers{
    public DryStorage(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Dry storage", currentVehicle, 3.5, 4.6);
    }
    public DryStorage(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Dry storage", currentPort, 3.5, 4.6);
    }
}
