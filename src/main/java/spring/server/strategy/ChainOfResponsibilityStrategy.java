package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class ChainOfResponsibilityStrategy extends ChainOfResponsibility implements Strategy {

    public ChainOfResponsibilityStrategy(String question, String answer) {
        super(question, answer);
    }

    @Override
    public JsonObject sendJson(UserRepository userRepository) {
        //TODO
        JsonObject value = Json.createObjectBuilder()
                .add("answer", handleRequest("a"))
                .build();
        return value;
    }

    @Override
    public String handleRequest(String question) {
        if(this.question.equalsIgnoreCase(question))
            return answer;
        else if (nextHandler != null)
            return nextHandler.handleRequest(question);
        return "Question is not added to database";
    }

    @Override
    public ChainOfResponsibilityStrategy getNextHandler() {
        return (ChainOfResponsibilityStrategy) nextHandler;
    }
}
