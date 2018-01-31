package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.JsonObject;

public class Context {
    private Strategy strategy;


    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public String executeStrategy() {
        return strategy.sendJson();
    }
}
