package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.JsonObject;

public interface Strategy {
    public String sendJson();
}
