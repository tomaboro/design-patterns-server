package alexa;

public class AlexaResponseCreator {

    private AlexaResponse alexaResponse;

    public AlexaResponseCreator(String text, Boolean shouldEndSession) {
        OutputSpeechType outputSpeechType = new OutputSpeechType();
        outputSpeechType.setText(text);
        outputSpeechType.setType("Plain text");
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

    public void setAlexaResponse(AlexaResponse alexaResponse) {
        this.alexaResponse = alexaResponse;
    }

    public AlexaResponse getAlexaResponse() {
        return this.alexaResponse;
    }

    public void setMessageText(String text) {
        this.alexaResponse.getResponse().getOutputSpeech().setText(text);
        this.alexaResponse.getResponse().getOutputSpeechType().setText(text);
    }
}
