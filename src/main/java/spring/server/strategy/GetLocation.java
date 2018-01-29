package spring.server.strategy;

import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class GetLocation implements Strategy {
    @Override
    public JsonObject sendJson(UserRepository userRepository) {
        String userID = "0"; //trzeba podac odpowiednie id ktore znajduje sie w userRepository
        String beacons = "";
        //System.out.println(userRepository.findOne(userID).getBeacons().size());
        for (int i = 0; i < userRepository.findOne(userID).getBeacons().size(); i++) {
           beacons += (userRepository.findOne(userID).getBeacons().get(i)+" ") ;
        }
        JsonObject value = Json.createObjectBuilder()
                .add("beacons",beacons)
                .build();
        return value;
    }
}
