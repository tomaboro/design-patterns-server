package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Random;

public class GetRandom implements Strategy{
    @Override
    public JsonObject sendJson(UserRepository userRepository){
        Random generator = new Random();
        int x = generator.nextInt(101);
        JsonObject value = Json.createObjectBuilder()
                .add("random", x)
                .build();
        return value;
    }
}
