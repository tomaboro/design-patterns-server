package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class GetUserID implements Strategy {
    @Override
    public JsonObject sendJson(UserRepository userRepository) {
        int size = userRepository.findAll().size();
        if (size == 0) {
            JsonObject value = Json.createObjectBuilder()
                    .add("id", "null")
                    .build();
            return value;
        } else {
            String userID = userRepository.findAll().get(size - 1).getId();
            JsonObject value = Json.createObjectBuilder()
                    .add("id", userRepository.findOne(userID).getId())
                    .build();
            return value;
        }
    }
}
