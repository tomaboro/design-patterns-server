package spring.server.entity.request;

import java.util.List;

public class AddUserRequest {
    private String id;
    private List<String> beacons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<String> beacons) {
        this.beacons = beacons;
    }
}
