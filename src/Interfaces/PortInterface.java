package src.interfaces;

import src.entities.Containers.Containers;
import src.entities.Port.Port;
import src.entities.Port.Trip;
import src.entities.Vehicles.Vehicles;

public interface PortInterface {
    void updateCapacity(Containers container);

    void addContainer(Containers container);

    void removeContainer(Containers container);

    void addVehicles(Vehicles vehicle);

    void removeVehicle(Vehicles vehicle);

    int getNumberOfVehicles();

    void addTrip(Trip trip);

    double calculateDistance(Port anotherPort);

    boolean isOverWeight(Containers containers);


}
