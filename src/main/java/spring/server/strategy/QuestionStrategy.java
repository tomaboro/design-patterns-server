package spring.server.strategy;

import spring.server.repository.UserRepository;
import spring.server.strategy.chain.AbstractChainOfResponsibility;

import javax.json.Json;
import javax.json.JsonObject;

public class QuestionStrategy implements Strategy {

    private AbstractChainOfResponsibility chain;
    private String question;

    public QuestionStrategy(AbstractChainOfResponsibility chain, String question) {
        this.chain = chain;
        this.question = question;
    }

    @Override
    public String sendJson() {
        String answer = chain.handleRequest(question);
        return answer;
    }
}
