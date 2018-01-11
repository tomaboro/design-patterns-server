package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.JsonObject;

public class BeaconStrategy implements Strategy {

    @Override
    public JsonObject sendJson(UserRepository userRepository) {
        return null;
    }
}
