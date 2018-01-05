package alexa;

public class AlexaResponseCreator {

	private String alexaResponse;

	public AlexaResponseCreator(String text) {
		OutputSpeechType outputSpeechType = new OutputSpeechType();
		outputSpeechType.setText("Odpowiedz to " + text);
		outputSpeechType.setType("Plain text");
		OutputSpeech outputSpeech = new OutputSpeech();
		outputSpeech.setText("Odpowiedz to " + text);
		Response response = new Response();
		response.setOutputSpeech(outputSpeech);
		response.setOutputSpeechType(outputSpeechType);
		response.setShouldEndSession("true");
		AlexaResponse alexaResponse = new AlexaResponse();
		alexaResponse.setResponse(response);
		alexaResponse.setVersion("1.0");
		alexaResponse.setSessionAttributes("{}");
		this.alexaResponse = alexaResponse.toString();
	}

	public void setAlexaResponse(String alexaResponse) {
		this.alexaResponse = alexaResponse;
	}

	public String getAlexaResponse() {
		return this.alexaResponse;
	}
}
