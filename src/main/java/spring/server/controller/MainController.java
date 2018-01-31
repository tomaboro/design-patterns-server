package spring.server.controller;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.*;
import spring.server.entity.request.QuestionRequest;
import spring.server.entity.request.UserRequest;
import spring.server.entity.request.Alexa.*;
import spring.server.factory.AlexaResponseFactory;
import spring.server.factory.ResponseInterface;
import spring.server.repository.*;
import spring.server.strategy.*;
import spring.server.strategy.chain.AbstractChainOfResponsibility;
import spring.server.strategy.chain.ChainOfResponsibility;

import javax.json.JsonObject;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MainController {

    private AlexaRepository alexaRepository;
    private UserRepository userRepository;
    private AlexaResponseFactory factory = new AlexaResponseFactory();
    private AbstractChainOfResponsibility chainOfResponsibility;

    @Autowired
    public MainController(AlexaRepository alexaRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.alexaRepository = alexaRepository;
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.GET)
    public List<Alexa> getAlexa() {
        return alexaRepository.findAll();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getID(@RequestBody String alexaJSON) throws JSONException {
        //String intent = alexaJSON.getIntent();
        //alexaJSON.getMessage();
        System.out.println(alexaJSON);
        JSONObject json = new JSONObject(alexaJSON);
        String intent = json.getJSONObject("request").getJSONObject("intent").getString("name");

        ResponseInterface alexaResponse = factory.getResponse("ALEXA");
        Context context;
        String alexaAnswer;
        switch (intent) {
            case "CreateUserID":
                context = new Context(new CreateUserID(userRepository));
                alexaAnswer = context.executeStrategy();
                alexaResponse.setMessageText("Your ID is " + alexaAnswer);
                break;
            case "GetUserID":
                context = new Context(new GetUserID(userRepository));
                alexaAnswer = context.executeStrategy();
                if (alexaAnswer == "null")
                    alexaResponse.setMessageText("First, you must create user ID.");
                else
                    alexaResponse.setMessageText("Your ID is " + alexaAnswer);
                break;
            case "GetLocation":
                context = new Context(new GetLocation(userRepository));
                alexaAnswer = context.executeStrategy();
                alexaResponse.setMessageText("You are within range of " + alexaAnswer);
                break;

            case "GetRandom":
                context = new Context(new GetRandom());
                alexaAnswer = context.executeStrategy();
                alexaResponse.setMessageText("Your random number is " + alexaAnswer);
                break;
            case "AnswerQuestion":
                String message = json.getJSONObject("request").getJSONObject("intent").getJSONObject("slots").getJSONObject("question").getString("value");
                if (chainOfResponsibility != null) {
                    context = new Context(new QuestionStrategy(chainOfResponsibility, message));

                    alexaAnswer = context.executeStrategy();
                    alexaResponse.setMessageText(alexaAnswer);
                } else
                    alexaResponse.setMessageText("Question is not added to database");
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
    public String updateUser(@RequestBody UserRequest userRequest) {
        User user;
        if ((user = userRepository.findOne(userRequest.getId())) != null) {
            String beaconId = userRequest.getBeacon();
            List<String> beacons = user.getBeacons();
            if (!beacons.contains(beaconId)) {
                beacons.add(beaconId);
                user.setBeacons(beacons);
            } else {
                return "Beacon already added!";
            }
        } else {
            user = new User();
            user.setId(userRequest.getId());
            List<String> beacons = new LinkedList<String>();
            beacons.add(userRequest.getBeacon());
            user.setBeacons(beacons);
        }
        userRepository.save(user);
        return "OK";
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteBeconFromUser(@RequestBody UserRequest userRequest) {
        User user;
        if ((user = userRepository.findOne(userRequest.getId())) != null) {
            List<String> beacons = user.getBeacons();
            if (!beacons.remove(userRequest.getBeacon()))
                return "This user doesn't have beacon of that id!";
            else
                user.setBeacons(beacons);
        } else {
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
    public String addQuestion(@RequestBody QuestionRequest questionRequest) {
        if (chainOfResponsibility != null) {
            AbstractChainOfResponsibility actual = chainOfResponsibility;
            if (questionRequest.getQuestion().equals(actual.getQuestion()))
                return "That Question is already added to chain!";
            AbstractChainOfResponsibility next = chainOfResponsibility.getNextHandler();
            while (next != null) {
                if (questionRequest.getQuestion().equals(next.getQuestion()))
                    return "That Question is already added to chain!";
                actual = next;
                next = next.getNextHandler();
            }
            actual.setNextHandler(new ChainOfResponsibility(questionRequest.getQuestion(), questionRequest.getAnswer()));
        } else {
            chainOfResponsibility = new ChainOfResponsibility(questionRequest.getQuestion(), questionRequest.getAnswer());
        }
        return "Question added to chain successfully!";
    }

    @RequestMapping(value = "/question", method = RequestMethod.DELETE)
    public String deleteQuestion(@RequestBody QuestionRequest questionRequest) {
        if (chainOfResponsibility != null) {
            AbstractChainOfResponsibility actual = chainOfResponsibility;
            if (actual.getQuestion().equals(questionRequest.getQuestion())) {
                chainOfResponsibility = actual.getNextHandler();
            } else {
                AbstractChainOfResponsibility next = actual.getNextHandler();
                while (next != null && !questionRequest.getQuestion().equals(next.getAnswer())) {
                    actual = next;
                    next = next.getNextHandler();
                }
                if (next != null) {
                    actual.setNextHandler(next.getNextHandler());
                } else
                    return "There is no such question in chain!";
            }
        } else {
            return "There is no such question in chain!";
        }
        return "Question removed from chain successfully!";
    }
}