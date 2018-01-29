package spring.server.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {

    @Id
    private String id;

    @ElementCollection
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
