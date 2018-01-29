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
import java.util.List;

@RestController
public class MainController {

    private AlexaRepository alexaRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AlexaResponseFactory factory = new AlexaResponseFactory();

    @Autowired
    public MainController(QuestionRepository questionRepository, AlexaRepository alexaRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
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
            case "CreateUserID": {
                context = new Context(new CreateUserID());
                jsonObject = context.executeStrategy(userRepository);
                String id = jsonObject.getString("id");
                alexaResponse.setMessageText("Your ID is " + id);
                break;
            }
            case "GetUserID": {
                context = new Context(new GetUserID());
                jsonObject = context.executeStrategy(userRepository);
                String id = jsonObject.getString("id");
                if (id == "null")
                    alexaResponse.setMessageText("First, you must create user ID.");
                else
                    alexaResponse.setMessageText("Your ID is " + id);
                break;
            }
            case "GetLocation": {
                context = new Context(new GetLocation());
                jsonObject = context.executeStrategy(userRepository);
                String beacons = jsonObject.getString("beacons");
                alexaResponse.setMessageText("You are within range of " + beacons);
                break;
            }
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

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addUser(@RequestBody AddUserRequest addUserRequest) {
        User user = new User();
        user.setId(addUserRequest.getId());
        user.setBeacons(addUserRequest.getBeacons());
        userRepository.save(user);
        return "OK";
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public List<Question> getQuestion() {
        return questionRepository.findAll();
    }

    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public String addQuestion(@RequestBody AddQuestion addQuestion) {
        Question question = new Question();
        question.setQuestion(addQuestion.getQuestion());
        question.setAnswer(addQuestion.getAnswer());
        questionRepository.save(question);
        return "Positive request";
    }
}