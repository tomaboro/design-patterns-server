package spring.server.strategy;

import javax.json.JsonObject;

public abstract class ChainOfResponsibility {

    protected String question;
    protected String answer;
    protected ChainOfResponsibility nextHandler;

    public ChainOfResponsibility(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void setNextHandler(ChainOfResponsibility nextHandler) {
        this.nextHandler = nextHandler;

    }

    public ChainOfResponsibility getNextHandler() {
        return nextHandler;
    }
    public abstract String handleRequest(String question);

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
