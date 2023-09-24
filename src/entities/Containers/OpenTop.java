package src.entities.Containers;

import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;

public class OpenTop extends Containers {
    public OpenTop(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Open top", currentVehicle, 2.8, 3.2);
    }
    public OpenTop(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Open top", currentPort, 2.8, 3.2);
    }
}
