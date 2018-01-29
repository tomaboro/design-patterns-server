package spring.server.factory;

public class AlexaResponseFactory {
    public ResponseInterface getResponse(String responseType){
        if(responseType == null){
            return null;
        }
        if(responseType.equalsIgnoreCase("ALEXA")){
            return new AlexaResponseCreator("Wrong Alexa request",true);
        }
        return null;
    }
}
