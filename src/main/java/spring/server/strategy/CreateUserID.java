package spring.server.strategy;

import spring.server.entity.User;
import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class CreateUserID implements Strategy {
    private static Integer IDcounter = 0;

    @Override
    public JsonObject sendJson(UserRepository userRepository) {
        JsonObject value = Json.createObjectBuilder()
                .add("id", IDcounter.toString())
                .build();
        User user = new User();
        user.setId(IDcounter.toString());
        userRepository.save(user);
        IDcounter++;
        return value;
    }
}
