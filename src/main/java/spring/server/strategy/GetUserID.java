package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class GetUserID implements Strategy {
    private UserRepository userRepository;

    public GetUserID(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String sendJson() {
        int size = userRepository.findAll().size();
        if (size == 0) {
            return "null";
        } else {
            String userID = userRepository.findAll().get(size - 1).getId();
            return userID;
        }
    }
}
