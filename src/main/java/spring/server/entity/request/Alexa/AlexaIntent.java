package spring.server.entity.request.Alexa;

public class AlexaIntent {
    String name;
    AlexaSlots slots;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlexaSlots getSlots() {
        return slots;
    }

    public void setSlots(AlexaSlots slots) {
        this.slots = slots;
    }
}
