package spring.server.entity.request.Alexa;

import spring.server.entity.request.Alexa.AlexaIntent;

public class AlexaRequest {
    AlexaIntent intent;

    public AlexaIntent getIntent() {
        return intent;
    }

    public void setIntent(AlexaIntent intent) {
        this.intent = intent;
    }
}
