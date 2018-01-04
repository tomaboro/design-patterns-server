package spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.*;
import spring.server.entity.request.AddAlexa;
import spring.server.entity.request.AddBeacon;
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

    @RequestMapping(value = "/beacon", method = RequestMethod.POST)
    public String addBeacon(@RequestBody AddBeacon addBeacon){
        Beacon beacon = new Beacon();
        beacon.setId(addBeacon.getId());
        beacon.setType(addBeacon.getType());
        beaconRepository.save(beacon);
        return "Positive request";
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.GET)
    public List<Alexa> getAlexa(){
        return alexaRepository.findAll();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.POST)
    public String getID(@RequestBody AlexaJSON alexaJSON){
        return alexaJSON.getMessage();
    }

    @RequestMapping(value = "/alexa", method = RequestMethod.PUT)
    public void AddAlexa(@RequestBody AddAlexa addAlexa){
        Alexa alexa = new Alexa();
        alexa.setId(addAlexa.getId());
        alexaRepository.save(alexa);
    }

}
