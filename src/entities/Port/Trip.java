package src.entities.Port;

import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import src.entities.User.Admin;
import src.entities.Vehicles.Vehicles;

public class Trip {
    static final String tripFileName = "data/Trip.txt";
    static final String portVehiclesListFileName = "data/PortVehiclesList.txt";
    private String tripID;
    private Vehicles vehicleInformation;
    private java.util.Date departureDate;
    private java.util.Date arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private Port vehicleStatus;

    public Trip(String tripID, Vehicles vehicleInformation, java.util.Date departureDate, java.util.Date arrivalDate, Port departurePort, Port arrivalPort, Port vehicleStatus) {
        this.tripID = tripID;
        this.vehicleInformation = vehicleInformation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.vehicleStatus = vehicleStatus;
    }

    public Vehicles getVehicleInformation() {
        return vehicleInformation;
    }

    public void setVehicleInformation(Vehicles vehicleInformation) {
        this.vehicleInformation = vehicleInformation;
    }

    public java.util.Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public java.util.Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public Port getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(Port vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
    public static void addTrip(Trip trip, Port currentPort, double totalFuel) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tripFileName, true))) {
            writer.write(trip.getTripID() + ",");
            writer.write(trip.getVehicleInformation().getUniqueID().substring(2) + ",");
            writer.write(trip.getDepartureDate() + ","); // Use the current date and time
            writer.write(trip.getArrivalDate()+",");
            writer.write(trip.getDeparturePort().getUniqueID() + ",");
            writer.write(trip.getArrivalPort().getUniqueID()+",");
            writer.write(trip.getVehicleStatus()+",");
            writer.write(totalFuel+"");
            writer.newLine();

            System.out.println("Trip data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Trip> getAllTrip() {
        List<Trip> trips = new ArrayList<>();
        java.util.Date arrivalDate;
        try (BufferedReader reader = new BufferedReader(new FileReader(tripFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                sdf.setTimeZone(TimeZone.getTimeZone("ICT"));

                if (parts.length == 8) {
                    try {
                        String tripID = parts[0];
                        Vehicles vehicleInfo = Vehicles.getVehicleByID(parts[1]);
                        java.util.Date departureDate = sdf.parse(parts[2]);
                        if (parts[3].equals("null")){
                            arrivalDate = null;
                        } else {
                            arrivalDate = sdf.parse(parts[3]);
                        }
                        Port departurePortID = Port.getPortById(parts[4]);
                        Port arrivalPortID = Port.getPortById(parts[5]);
                        Port vehicleStatusPortID = Port.getPortById(parts[6]);
                        Trip trip = new Trip(tripID, vehicleInfo, departureDate, arrivalDate, departurePortID, arrivalPortID, vehicleStatusPortID);
                        trips.add(trip);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trips;
    }
    public static void updateTrip(String tripID) {
        List<String> updatedLines = new ArrayList<>();
        Trip trip;
        List<Trip> tripList = getAllTrip();
        for (Trip trips:tripList){
            if (trips.getTripID().equals(tripID)){
                trip = trips;
                Admin.writeMoveVehicleToPort(portVehiclesListFileName, trip.getVehicleInformation().getUniqueID(), "null");
                try (BufferedReader reader = new BufferedReader(new FileReader(tripFileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 1 && parts[0].equals(tripID)) {
                            if (parts.length > 6) {
                                parts[3] = new java.util.Date() +"";
                                parts[6] = parts[5];
                                Vehicles.writeUpdateVehicleFuel(trip.getVehicleInformation().getUniqueID(), Double.parseDouble(parts[7]));
                            }
                        }
                        updatedLines.add(String.join(",", parts));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
            }
        }

        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tripFileName))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Trip> filterTripsArrivalDate(String filterDateStr) {
        List<Trip> filteredTrips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(tripFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String arrivalDateStr = parts[3];
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        sdf.setTimeZone(TimeZone.getTimeZone("ICT"));
                        java.util.Date arrivalDate = sdf.parse(arrivalDateStr);
                        String formattedArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate);
                        if (formattedArrivalDate.equals(filterDateStr)) {
                            Trip trip = new Trip(parts[0], Vehicles.getVehicleByID(parts[1]), sdf.parse(parts[2]), arrivalDate, Port.getPortById(parts[4]), Port.getPortById(parts[4]), Port.getPortById(parts[4]));
                            filteredTrips.add(trip);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredTrips;
    }
    public static List<Trip> filterTripsDepartureDate(String filterDateStr) {
        List<Trip> trips = new ArrayList<>();
        java.util.Date arrivalDate;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/Trip.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                sdf.setTimeZone(TimeZone.getTimeZone("ICT"));

                if (parts.length == 7) {
                    try {
                        String tripID = parts[0];
                        Vehicles vehicleInfo = Vehicles.getVehicleByID(parts[1]);
                        java.util.Date departureDate = sdf.parse(parts[2]);
                        if (parts[3].equals("null")){
                            arrivalDate = null;
                        } else {
                            arrivalDate = sdf.parse(parts[3]);
                        }
                        Port departurePortID = Port.getPortById(parts[4]);
                        Port arrivalPortID = Port.getPortById(parts[5]);
                        Port vehicleStatusPortID = Port.getPortById(parts[6]);
                        Trip trip = new Trip(tripID, vehicleInfo, departureDate, arrivalDate, departurePortID, arrivalPortID, vehicleStatusPortID);
                        trips.add(trip);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trips;
    }
    public static List<Trip> filterTripsByDateRange(String startDateStr, String endDateStr) {
        List<Trip> filteredTrips = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(tripFileName))) {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            try {
                sdf.setTimeZone(TimeZone.getTimeZone("ICT"));
                java.util.Date startDate = sdf.parse(startDateStr);
                java.util.Date endDate = sdf.parse(endDateStr + " 23:59:59 GMT 1970");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String arrivalDateStr = parts[3];
                        String departureDateStr = parts[2];
                        try {
                            java.util.Date arrivalDate = sdf.parse(arrivalDateStr);
                            java.util.Date departureDate = sdf.parse(departureDateStr);
                            if (arrivalDate.compareTo(startDate) >= 0 && arrivalDate.compareTo(endDate) <= 0) {
                                Trip trip = new Trip(parts[0], Vehicles.getVehicleByID(parts[1]), departureDate, arrivalDate,
                                        Port.getPortById(parts[4]), Port.getPortById(parts[5]), Port.getPortById(parts[6]));
                                filteredTrips.add(trip);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredTrips;
    }
    public static void tripInSevenDays(){
        List<Trip> tripList = getAllTrip();
        List<Trip> updatedTripList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        for (Trip trip:tripList){
            Date currentDate = new Date();
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(trip.getArrivalDate());
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                Date newDate = calendar.getTime();
                int compare = currentDate.compareTo(newDate);
                if (compare<=0){
                    updatedTripList.add(trip);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tripFileName))) {

            for (Trip trip : updatedTripList) {
                double fuelConsumption = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(tripFileName))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        System.out.println(line+"Hello");
                        String[] parts = line.split(",");
                        System.out.println(parts.length >= 7);
                        if (parts.length >= 7 && parts[0].equals(trip.getTripID())){
                            fuelConsumption = Double.parseDouble(parts[6]);
                        }
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String tripData =trip.getTripID() + ",";
                tripData +=trip.getVehicleInformation().getUniqueID().substring(2) + ",";
                tripData +=trip.getDepartureDate() + ",";
                tripData +=trip.getArrivalDate() + ",";
                tripData +=trip.getDeparturePort().getUniqueID() + ",";
                tripData +=trip.getArrivalPort().getUniqueID() + ",";
                tripData +=trip.getVehicleStatus().getUniqueID() + ",";
                tripData +=fuelConsumption+"";
                writer.write(tripData);
                writer.newLine(); // Add a newline separator between trips
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static double calculateTotalFuelConsumption(String givenDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        double totalFuelConsumption = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(tripFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    try {
                        Date departureDate = dateFormat.parse(parts[2]);
                        double fuelConsumption = Double.parseDouble(parts[6]);
                        System.out.println(isSameDay(departureDate, dateFormat.parse(givenDay)));
                        System.out.println(isSameDay(departureDate, dateFormat.parse(givenDay)));
                        if (isSameDay(departureDate, dateFormat.parse(givenDay))) {
                            totalFuelConsumption += fuelConsumption;
                        }
                    } catch (ParseException | NumberFormatException e) {
                        // Handle parsing errors if needed
                        System.err.println("Error parsing date or fuel consumption in line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalFuelConsumption;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        return dateFormat.format(date1).equals(dateFormat.format(date2));
    }
}
