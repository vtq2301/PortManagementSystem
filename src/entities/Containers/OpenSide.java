package src.entities.Containers;

import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;

public class OpenSide extends Containers{
    public OpenSide(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Open side", currentVehicle, 2.7, 3.2);
    }
    public OpenSide(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Open side", currentPort, 2.7, 3.2);
    }
}
