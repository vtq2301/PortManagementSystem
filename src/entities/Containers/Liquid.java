package src.entities.Containers;

import src.entities.Port.Port;
import src.entities.Vehicles.Vehicles;

public class Liquid extends Containers{
    public Liquid(double containerWeight, String uniqueID, String containerType, Vehicles currentVehicle, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Liquid", currentVehicle, 4.8, 5.3);
    }
    public Liquid(double containerWeight, String uniqueID, String containerType, Port currentPort, double fuelConsumptionShip, double fuelConsumptionTruck){
        super(containerWeight, uniqueID, "Liquid", currentPort, 4.8, 5.3);
    }
}
