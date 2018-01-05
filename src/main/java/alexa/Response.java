package alexa;

public class Response
{
    private OutputSpeechType outputSpeechType;

    private OutputSpeech outputSpeech;
    
    private String shouldEndSession;

    
    public OutputSpeechType getOutputSpeechType ()
    {
        return outputSpeechType;
    }

    public void setOutputSpeechType (OutputSpeechType outputSpeechType)
    {
        this.outputSpeechType = outputSpeechType;
    }

    public OutputSpeech getOutputSpeech ()
    {
        return outputSpeech;
    }

    public void setOutputSpeech (OutputSpeech outputSpeech)
    {
        this.outputSpeech = outputSpeech;
    }
    
    public String getShouldEndSession ()
    {
        return shouldEndSession;
    }

    public void setShouldEndSession (String shouldEndSession)
    {
        this.shouldEndSession = shouldEndSession;
    }

    @Override
    public String toString()
    {
        return "\"outputSpeech\": {"+outputSpeechType+"}, \"speechletResponse\": { \"outputSpeech\": {"+outputSpeech+"}, \"shouldEndSession\": "+shouldEndSession+"}";
    }
}
