package spring.server.strategy;

import spring.server.repository.UserRepository;
import spring.server.strategy.chain.ChainOfResponsibility;

import javax.json.Json;
import javax.json.JsonObject;

public class QuestionStrategy implements Strategy {

    private ChainOfResponsibility chain;
    private String question;
    private UserRepository userRepository;
    public QuestionStrategy(ChainOfResponsibility chain, String question,UserRepository userRepository) {
        this.chain = chain;
        this.question = question;
        this.userRepository = userRepository;
    }

    @Override
    public String sendJson() {
        String answer = chain.handleRequest(question,userRepository);
        return answer;
    }
}
