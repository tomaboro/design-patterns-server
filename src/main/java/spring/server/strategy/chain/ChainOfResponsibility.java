package spring.server.strategy.chain;

import spring.server.entity.User;
import spring.server.repository.UserRepository;

import javax.json.Json;
import javax.json.JsonObject;

public class ChainOfResponsibility extends AbstractChainOfResponsibility {


    public ChainOfResponsibility(String question, String answer, String beaconID) {
        super(question, answer, beaconID);
    }
    @Override
    public ChainOfResponsibility getNextHandler() {
        return (ChainOfResponsibility) nextHandler;
    }


    @Override
    public String handleRequest(String question, UserRepository userRepository) {
        for (User user : userRepository.findAll()) {
            if(user.getBeacons().contains(beaconID))
            {
                if (this.question.equalsIgnoreCase(question))
                    return answer;
                else if (nextHandler != null)
                    return nextHandler.handleRequest(question,userRepository);
                return "Question is not added to database you aren't near to correct beacon";
            }
        }
        if (nextHandler != null)
            return nextHandler.handleRequest(question,userRepository);
        return "Question is not added to database or you aren't near to correct beacon";
    }
}
