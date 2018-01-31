package spring.server.strategy;

import spring.server.entity.User;
import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class CreateUserID implements Strategy {
    private static Integer IDCounter = 0;
    private UserRepository userRepository;

    public CreateUserID(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String sendJson() {
        String ID = IDCounter.toString();
        User user = new User();
        user.setId(IDCounter.toString());
        userRepository.save(user);
        IDCounter++;
        return ID;
    }
}
