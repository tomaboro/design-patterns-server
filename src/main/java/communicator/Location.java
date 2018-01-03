package communicator;

public class Location {
    private String beaconID;
    private double distance;

    public String getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(String beaconID) {
        this.beaconID = beaconID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Location(String beaconID, double distance) {
        this.beaconID = beaconID;
        this.distance = distance;
    }
}
