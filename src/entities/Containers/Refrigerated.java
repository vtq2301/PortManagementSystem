package src.entities.Containers;

import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;

public class Refrigerated extends Containers{
    public Refrigerated(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Refrigerated", currentVehicle, 4.5, 5.4);
    }
    public Refrigerated(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Refrigerated", currentPort, 4.5, 5.4);
    }
}
