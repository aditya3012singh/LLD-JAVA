import java.util.*;
import java.time.LocalDateTime;
import java.time.Duration;
class Vehicle{
    private final String vehicleId;
    private final VehicleType vehicleType;
    private final String licensePlate;

    Vehicle(String vehicleId, VehicleType vehicleType, String licensePlate){
        this.vehicleId= vehicleId;
        this.vehicleType= vehicleType;
        this.licensePlate= licensePlate;
    }

    String getVehicleId() {
        return vehicleId;
    }

    VehicleType getVehicleType() {
        return vehicleType;
    }

    String getLicensePlate() {
        return licensePlate;
    }
}

enum VehicleType{
    CAR, MOTORCYCLE, TRUCK
}

class ParkingSpot{
    private final String spotId;
    private final String spotType;
    private Vehicle vehicle;

    ParkingSpot(String spotId, String spotType){
        this.spotId= spotId;
        this.spotType= spotType;
        this.vehicle= null;
    }

    boolean isAvailable(){
        return vehicle== null;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if(vehicle==null){
            System.out.println("Error: Vehicle cannot be null.");
            return false;
        }
        if(!isAvailable()) {
            System.out.println("Error: Parking spot is already occupied.");
            return false;
        }else{
            this.vehicle= vehicle;
            System.out.println("Vehicle parked in spot: "+spotId);
            return true;
        }
    }

    public boolean releaseVehicle() {
        if(isAvailable()) {
            System.out.println("Error: Parking spot is already empty.");
            return false;
        }else{
            this.vehicle= null;
            System.out.println("Vehicle released from spot: "+spotId);
            return true;
        }
    }

    String getSpotType() {
        return spotType;
    }

    Vehicle getVehicle() {
        return vehicle;
    }

}


class ParkingSession{
    private final String ticketId;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private final ParkingSpot parkingSpot;
    private SessionStatus sessionStatus;
    private String paymentStatus;
    private double fee;

    ParkingSession(String ticketId, LocalDateTime entryTime, ParkingSpot parkingSpot){
        this.ticketId= ticketId;
        this.entryTime= entryTime;
        this.parkingSpot= parkingSpot;
        this.sessionStatus= SessionStatus.ACTIVE;
        this.paymentStatus= "PENDING";
    }

    // boolean endSession(LocalDateTime exitTime, double fee){
    //     if(this.sessionStatus== SessionStatus.COMPLETED){
    //         System.out.println("Error: Parking session already completed.");
    //         return false;
    //     }
    //     this.exitTime= exitTime;
    //     this.fee= fee;
    //     this.sessionStatus= SessionStatus.COMPLETED;
    //     // this.paymentStatus= "PAID";
    //     return true;
    // }

    boolean recordExitTime(LocalDateTime exitTime){
        if(this.sessionStatus== SessionStatus.COMPLETED){
            System.out.println("Error: Parking session already completed.");
            return false;
        }
        if(exitTime.isBefore(entryTime)){
            System.out.println("Error: Exit time cannot be before entry time.");
            return false;
        }
        this.exitTime = exitTime;   
        return true;
    }

    void completeSession(double fee){
        this.fee= fee;
        this.sessionStatus= SessionStatus.COMPLETED;
    }

    String getTicketId() {
        return ticketId;
    }

    SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    LocalDateTime getEntryTime() {
        return entryTime;
    }

    double getFee() {
        return fee;
    }

    LocalDateTime getExitTime() {
        return exitTime;
    }

}

enum SessionStatus{
    ACTIVE, COMPLETED
}

class ParkingLot{
    String lotId;
    String location;
    List<ParkingSpot> parkingSpots;
    Map<String, ParkingSession> sessions;
    private final FeeCalculator feeCalculator;

    ParkingLot(String lotId, String location, List<ParkingSpot> parkingSpots, Map<String, ParkingSession> sessions, FeeCalculator feeCalculator){
        this.lotId= lotId;
        this.location= location;
        this.parkingSpots= parkingSpots;
        this.sessions= sessions;
        this.feeCalculator= feeCalculator;
    }

    ParkingSpot findAvailableSpot(VehicleType vehicleType){
        for(ParkingSpot spot: parkingSpots){
            
            if(spot.isAvailable() && spot.getSpotType().equals(vehicleType.toString())){
                return spot;
            }
        }
        return null;
    }

    void parkVehicle(Vehicle vehicle, LocalDateTime entryTime){
        String ticketId = "TICKET-" + vehicle.getVehicleId();
        ParkingSession existingSession = sessions.get(ticketId);
        if(existingSession!= null && existingSession.getSessionStatus()== SessionStatus.ACTIVE){
            System.out.println("Error: Vehicle is already parked.");
            return;
        }
        ParkingSpot availableSpot= findAvailableSpot(vehicle.getVehicleType());
        if(availableSpot!= null){
            // if(availableSpot.getVehicle()!= null){
            //     System.out.println("Error: Parking spot is already occupied.");
            //     return;
            // }
            boolean parked = availableSpot.parkVehicle(vehicle);
            if (!parked) {
                System.out.println("Error: Failed to park vehicle.");
                return;
            }
            ParkingSession session= new ParkingSession("TICKET-"+vehicle.getVehicleId(), entryTime, availableSpot);
            sessions.put(session.getTicketId(), session);
            System.out.println("Vehicle parked. Ticket ID: "+session.getTicketId());
        } else {
            System.out.println("No available parking spot for vehicle type: "+vehicle.getVehicleType());
        }
    }

    void exitVehicle(String ticketId, LocalDateTime exitTime){
        ParkingSession session= sessions.get(ticketId);

        if(session!= null){
            if(session.getSessionStatus()== SessionStatus.COMPLETED){
                System.out.println("Error: Parking session already completed.");
                return;
            }else if(session.getParkingSpot().getVehicle()== null){
                System.out.println("Error: No vehicle found for this parking session.");
                return;
            }else{
                
                boolean recordedExit= session.recordExitTime(exitTime);
                if(!recordedExit){
                    System.out.println("Error in exit time");
                    return;
                }
                // if (!sessionEnded) {
                //     System.out.println("Error: Failed to end parking session.");
                //     return;
                // }
                // sessions.remove(ticketId);
                double fee = 0;
                try {
                    fee= feeCalculator.calculateFee(session);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error calculating fee: " + e.getMessage());
                    return;
                }
                
                session.completeSession(fee);
                session.getParkingSpot().releaseVehicle();
                System.out.println("Vehicle exited. Fee: $"+ session.getFee());
            }
            
        } else {
            System.out.println("Error: Invalid ticket ID.");
            return;
        }
    }
}
            
interface FeeCalculator{
    double calculateFee(ParkingSession session);
}

class CarFeeCalculator implements FeeCalculator{
    @Override
    public double calculateFee(ParkingSession session) {
        LocalDateTime entryTime = session.getEntryTime();
        LocalDateTime exitTime = session.getExitTime();
        if(entryTime== null || exitTime== null){
            System.out.println("Error: Entry time or exit time is null.");
            throw new IllegalArgumentException("Entry time and exit time must not be null.");
        }
        if(exitTime.isBefore(entryTime)){
            System.out.println("Error: Exit time is before entry time.");
            throw new IllegalArgumentException("Exit time cannot be before entry time.");
        }
        Duration duration = Duration.between(entryTime, exitTime);
        double hours = duration.toMinutes() / 60.0;
        double billableHours = Math.ceil(hours);
        return billableHours * 50.0; // $50 per hour for cars
    }
}