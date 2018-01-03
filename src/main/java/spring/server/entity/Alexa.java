package spring.server.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Alexa {
    //TODO: the alexa JSON objects

    @Id
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
