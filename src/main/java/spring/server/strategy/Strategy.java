package spring.server.strategy;

import javax.json.JsonObject;

public interface Strategy {
    public JsonObject sendJson(String message, String where);
}
