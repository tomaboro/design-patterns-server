package spring.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.Alexa;
import spring.server.entity.request.AddAlexa;
import spring.server.repository.AlexaRepository;

import java.util.List;

@RestController
@RequestMapping("/alexa")
public class AlexaController {

    private AlexaRepository alexaRepository;

    @Autowired
    public AlexaController(AlexaRepository alexaRepository) {
        this.alexaRepository = alexaRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Alexa> getAlexa(){
        return alexaRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addAlexa(@RequestBody AddAlexa addAlexa){
        Alexa alexa = new Alexa();
        alexa.setId(addAlexa.getId());
        alexaRepository.save(alexa);
    }


}
