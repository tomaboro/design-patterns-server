package spring.server.strategy;

import javax.json.JsonObject;

public class SendAlexaLocation implements Strategy {
    @Override
    public JsonObject sendJson(String message, String where) {
        return null;
    }
}
