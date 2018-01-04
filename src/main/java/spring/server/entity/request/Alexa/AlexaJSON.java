package spring.server.entity.request.Alexa;

public class AlexaJSON {
    private AlexaRequest request;

    public AlexaRequest getRequest() {
        return request;
    }

    public void setRequest(AlexaRequest request) {
        this.request = request;
    }

    public String getMessage(){
        return request.intent.getName();
    }
}
