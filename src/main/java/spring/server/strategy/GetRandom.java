package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Random;

public class GetRandom implements Strategy {
    @Override
    public String sendJson() {
        Random generator = new Random();
        Integer x = generator.nextInt(101);
        return x.toString();
    }
}
