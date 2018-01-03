package spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.*;
import spring.server.entity.request.AddAlexa;
import spring.server.entity.request.Alexa.AlexaJSON;
import spring.server.repository.*;

import java.util.List;

@RestController
public class MainController {

    private BeaconRepository beaconRepository;
    private AlexaRepository alexaRepository;

    @Autowired
    public MainController(BeaconRepository beaconRepository, AlexaRepository alexaRepository){
        this.beaconRepository = beaconRepository;
        this.alexaRepository = alexaRepository;
    }

    @RequestMapping(value = "/beacon", method = RequestMethod.GET)
    public List<Beacon> getBeacon(){
        return beaconRepository.findAll();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.GET)
    public List<Alexa> getAlexa(){
        return alexaRepository.findAll();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.POST)
    public String getID(@RequestBody AlexaJSON alexaJSON, String elo){
        return alexaJSON.getRequest().getIntent().getName();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.PUT)
    public void AddAlexa(@RequestBody AddAlexa addAlexa, String elo){
        System.out.println(elo);
        Alexa alexa = new Alexa();
        alexa.setId(addAlexa.getId());
        alexaRepository.save(alexa);
    }

}