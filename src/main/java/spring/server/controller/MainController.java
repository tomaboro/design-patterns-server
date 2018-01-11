package spring.server.controller;

import alexa.AlexaResponseCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.*;
import spring.server.entity.request.AddBeacon;
import spring.server.entity.request.AddUserRequest;
import spring.server.entity.request.Alexa.*;
import spring.server.repository.*;
import spring.server.strategy.*;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    private AlexaRepository alexaRepository;
    private UserRepository userRepository;
    private BeaconRepository beaconRepository;
    private List<Integer> usedIDs = new ArrayList<Integer>();

    @Autowired
    public MainController(BeaconRepository beaconRepository, AlexaRepository alexaRepository, UserRepository userRepository) {
        this.beaconRepository = beaconRepository;
        this.userRepository = userRepository;
        this.alexaRepository = alexaRepository;
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.GET)
    public List<Alexa> getAlexa() {
        return alexaRepository.findAll();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getID(@RequestBody AlexaJSON alexaJSON) {
        String message = alexaJSON.getMessage();
        AlexaResponseCreator alexaResponseCreator = new AlexaResponseCreator("Bad Alexa request", true);
        Context context;
        JsonObject jsonObject;
        switch (message) {
            case "CreateUserID": {
                context = new Context(new CreateUserID());
                jsonObject = context.executeStrategy(userRepository);
                String id = jsonObject.getString("id");
                alexaResponseCreator.setMessageText("Your ID is " + id);
                usedIDs.add(Integer.valueOf(id));
                break;
            }
            case "GetUserID": {
                context = new Context(new GetUserID());
                jsonObject = context.executeStrategy(userRepository);
                String id = jsonObject.getString("id");
                alexaResponseCreator.setMessageText("Your ID is " + id);
                break;
            }
            case "GetLocation": {
                context = new Context(new GetLocation());
                jsonObject = context.executeStrategy(userRepository);
                String beacons = jsonObject.getString("beacons");
                alexaResponseCreator.setMessageText("You are within range of " + beacons);
                break;
            }
        }
        return alexaResponseCreator.getAlexaResponse().toString();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.PUT)
    public void AddAlexa(@RequestBody AddAlexa addAlexa) {
        Alexa alexa = new Alexa();
        alexa.setId(addAlexa.getId());
        alexaRepository.save(alexa);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addUser(@RequestBody AddUserRequest addUserRequest) {
        User user = new User();
        user.setId(addUserRequest.getId());
        user.setBeacons(addUserRequest.getBeacons());
        userRepository.save(user);
        return "OK";
    }

    @RequestMapping(value = "/beacon", method = RequestMethod.GET)
    public List<Beacon> getBeacon() {
        return beaconRepository.findAll();
    }

    @RequestMapping(value = "/beacon", method = RequestMethod.POST)
    public String addBeacon(@RequestBody AddBeacon addBeacon) {
        Beacon beacon = new Beacon();
        beacon.setId(addBeacon.getId());
        beacon.setType(addBeacon.getType());
        beaconRepository.save(beacon);
        return "Positive request";
    }
}