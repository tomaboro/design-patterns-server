package spring.server.strategy.chain;

import spring.server.repository.UserRepository;

public abstract class AbstractChainOfResponsibility {

    protected String question;
    protected String answer;
    protected String beaconID;
    protected AbstractChainOfResponsibility nextHandler;

    public AbstractChainOfResponsibility(String question, String answer,String beaconID) {
        this.question = question;
        this.answer = answer;
        this.beaconID = beaconID;
    }

    public void setNextHandler(AbstractChainOfResponsibility nextHandler) {
        this.nextHandler = nextHandler;

    }

    public AbstractChainOfResponsibility getNextHandler() {
        return nextHandler;
    }
    public abstract String handleRequest(String question, UserRepository userRepository);

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getBeaconID() {
        return beaconID;
    }
}
