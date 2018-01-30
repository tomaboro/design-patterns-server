package spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.*;
import spring.server.entity.request.AddQuestion;
import spring.server.entity.request.AddUserRequest;
import spring.server.entity.request.Alexa.*;
import spring.server.factory.AlexaResponseFactory;
import spring.server.factory.ResponseInterface;
import spring.server.repository.*;
import spring.server.strategy.*;

import javax.json.JsonObject;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MainController {

    private AlexaRepository alexaRepository;
    private UserRepository userRepository;
    private AlexaResponseFactory factory = new AlexaResponseFactory();
    private ChainOfResponsibilityStrategy chainOfResponsibilityStrategy;

    @Autowired
    public MainController( AlexaRepository alexaRepository, UserRepository userRepository) {
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
        ResponseInterface alexaResponse = factory.getResponse("ALEXA");
        Context context;
        JsonObject jsonObject;
        switch (message) {
            case "CreateUserID":
                context = new Context(new CreateUserID());
                jsonObject = context.executeStrategy(userRepository);
                String id = jsonObject.getString("id");
                alexaResponse.setMessageText("Your ID is " + id);
                break;
            case "GetUserID":
                context = new Context(new GetUserID());
                jsonObject = context.executeStrategy(userRepository);
                id = jsonObject.getString("id");
                if (id == "null")
                    alexaResponse.setMessageText("First, you must create user ID.");
                else
                    alexaResponse.setMessageText("Your ID is " + id);
                break;
            case "GetLocation":
                context = new Context(new GetLocation());
                jsonObject = context.executeStrategy(userRepository);
                String beacons = jsonObject.getString("beacons");
                alexaResponse.setMessageText("You are within range of " + beacons);
                break;
            case "ChainOfResponsibility":
                context = new Context(chainOfResponsibilityStrategy);
                //TODO
                jsonObject = context.executeStrategy(userRepository);
                alexaResponse.setMessageText(jsonObject.getString("answer"));
                break;
        }
        return alexaResponse.getString();
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

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String updateUser(@RequestBody AddUserRequest addUserRequest) {
        User user;
        if((user = userRepository.findOne(addUserRequest.getId())) != null)
        {
            String beaconId = addUserRequest.getBeacon();
            List<String> beacons = user.getBeacons();
            if(!beacons.contains(beaconId)){
                beacons.add(beaconId);
                user.setBeacons(beacons);
            }
            else{
                return "Beacon already added!";
            }
        }
        else{
            user = new User();
            user.setId(addUserRequest.getId());
            List<String> beacons = new LinkedList<String>();
            beacons.add(addUserRequest.getBeacon());
            user.setBeacons(beacons);
        }
        userRepository.save(user);
        return "OK";
    }
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteBeconFromUser(@RequestBody AddUserRequest addUserRequest) {
        User user;
        if((user = userRepository.findOne(addUserRequest.getId())) != null)
        {
            List<String> beacons = user.getBeacons();
            if(!beacons.remove(addUserRequest.getBeacon()))
                return "This user doesn't have beacon of that id!";
            else
                user.setBeacons(beacons);
        }
        else{
            return "User not exists!";
        }
        userRepository.save(user);
        return "OK";
    }


    //@RequestMapping(value = "/question", method = RequestMethod.GET)
    //public List<Question> getQuestion() {
    //    return questionRepository.findAll();
    //}

    @RequestMapping(value = "/question", method = RequestMethod.PUT)
    public String addQuestion(@RequestBody AddQuestion addQuestion) {
        if(chainOfResponsibilityStrategy != null) {
            ChainOfResponsibility actual = chainOfResponsibilityStrategy;
            ChainOfResponsibility next = chainOfResponsibilityStrategy.getNextHandler();
            while (next != null) {
                actual = next;
                next = chainOfResponsibilityStrategy.getNextHandler();
            }
            actual.setNextHandler(new ChainOfResponsibilityStrategy(addQuestion.getQuestion(),addQuestion.getAnswer()));
        }
        else{
            chainOfResponsibilityStrategy = new ChainOfResponsibilityStrategy(addQuestion.getQuestion(),addQuestion.getAnswer());
        }

        //Question question = new Question();
        //question.setQuestion(addQuestion.getQuestion());
        //question.setAnswer(addQuestion.getAnswer());
        //questionRepository.save(question);
        return "Positive request";
    }
}