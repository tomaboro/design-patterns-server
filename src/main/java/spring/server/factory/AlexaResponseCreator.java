package spring.server.factory;

public class AlexaResponseCreator implements ResponseInterface {

    private AlexaResponse alexaResponse;

    public AlexaResponseCreator(String text, Boolean shouldEndSession) {
        OutputSpeechType outputSpeechType = new OutputSpeechType();
        outputSpeechType.setText(text);
        outputSpeechType.setType("PlainText");
        OutputSpeech outputSpeech = new OutputSpeech();
        outputSpeech.setText(text);
        Response response = new Response();
        response.setOutputSpeech(outputSpeech);
        response.setOutputSpeechType(outputSpeechType);
        response.setShouldEndSession(shouldEndSession.toString());
        AlexaResponse alexaResponse = new AlexaResponse();
        alexaResponse.setResponse(response);
        alexaResponse.setVersion("1.0");
        alexaResponse.setSessionAttributes("{}");
        this.alexaResponse = alexaResponse;
    }

    public void setMessageText(String text) {
        this.alexaResponse.getResponse().getOutputSpeech().setText(text);
        this.alexaResponse.getResponse().getOutputSpeechType().setText(text);
    }

    public String getString() {
        return this.alexaResponse.toString();
    }
}
