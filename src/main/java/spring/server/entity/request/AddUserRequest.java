package spring.server.entity.request;

import spring.server.entity.Beacon;

import java.util.List;

public class AddUserRequest {
    private String id;
    private List<Beacon> beacons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }
}
