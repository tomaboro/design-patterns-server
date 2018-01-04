package spring.server.strategy;

import javax.json.JsonObject;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public JsonObject executeStrategy(String message, String where){
        return strategy.sendJson(message,where);
    }
}
