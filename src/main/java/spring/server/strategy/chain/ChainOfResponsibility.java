package spring.server.strategy.chain;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class ChainOfResponsibility extends AbstractChainOfResponsibility {

    public ChainOfResponsibility(String question, String answer) {
        super(question, answer);
    }

    @Override
    public String handleRequest(String question) {
        if(this.question.equalsIgnoreCase(question))
            return answer;
        else if (nextHandler != null)
            return nextHandler.handleRequest(question);
        return "Question is not added to database";
    }
}
