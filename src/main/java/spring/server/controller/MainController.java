package spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.*;
import spring.server.entity.request.Alexa.*;
import spring.server.repository.*;
import spring.server.strategy.*;

import javax.json.JsonObject;
import java.util.List;

@RestController
public class MainController extends BeaconController{

    private AlexaRepository alexaRepository;

    @Autowired
    public MainController(BeaconRepository beaconRepository, AlexaRepository alexaRepository){
        super(beaconRepository);
        this.alexaRepository = alexaRepository;
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.GET)
    public List<Alexa> getAlexa(){
        return alexaRepository.findAll();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.POST)
    public String getID(@RequestBody AlexaJSON alexaJSON){
        String message = alexaJSON.getMessage();
        Context context;
        JsonObject jsonObject;
        switch (message){
            case "GetID":{
                context = new Context(new GetAlexaID());
                break;
            }
            case "GiveLocation":{
                context = new Context(new SendAlexaLocation());
                jsonObject = context.executeStrategy("message","tmp");
                break;
            }
            default:{
                System.out.println("Bad alexa request");
            }
        }
        //TODO: should return JSON not string
        return message;
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.PUT)
    public void AddAlexa(@RequestBody AddAlexa addAlexa){
        Alexa alexa = new Alexa();
        alexa.setId(addAlexa.getId());
        alexaRepository.save(alexa);
    }

}
