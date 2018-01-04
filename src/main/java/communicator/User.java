package communicator;

public class User {
    private String id;
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User(String id) {
        this.id = id;
    }

    public boolean isInArea(String beaconID, double distance){
        if (beaconID == getLocation().getBeaconID() && distance >= getLocation().getDistance())
            return true;
        return false;
    }
}
