package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class GetUserID implements Strategy {
    @Override
    public JsonObject sendJson(UserRepository userRepository) {
        String userID = "0"; //trzeba podac odpowiednie id ktore znajduje sie w userRepository
        JsonObject value = Json.createObjectBuilder()
                .add("id", userRepository.findOne(userID).getId())
                .build();
        return value;
    }

}
