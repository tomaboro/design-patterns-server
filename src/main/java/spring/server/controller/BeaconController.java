package spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.server.entity.Beacon;
import spring.server.entity.request.AddBeacon;
import spring.server.repository.BeaconRepository;

import java.util.List;

public class BeaconController {

    protected BeaconRepository beaconRepository;

    @Autowired
    public BeaconController(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
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
}
