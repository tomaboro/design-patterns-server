package alexa;

public class OutputSpeech
{
    private String text;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }


    @Override
    public String toString()
    {
        return "\"text\": \""+text+"\"";
    }
}