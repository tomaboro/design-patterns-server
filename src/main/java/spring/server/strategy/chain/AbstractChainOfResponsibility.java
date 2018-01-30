package spring.server.strategy.chain;

public abstract class AbstractChainOfResponsibility {

    protected String question;
    protected String answer;
    protected AbstractChainOfResponsibility nextHandler;

    public AbstractChainOfResponsibility(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void setNextHandler(AbstractChainOfResponsibility nextHandler) {
        this.nextHandler = nextHandler;

    }

    public AbstractChainOfResponsibility getNextHandler() {
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
