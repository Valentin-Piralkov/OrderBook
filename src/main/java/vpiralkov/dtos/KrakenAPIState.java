package vpiralkov.dtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KrakenAPIState {
    public enum State {
        DISCONNECTED,
        CONNECTED_SUCCESSFULLY,
        SUBSCRIBED,
    }

    private State currentState;
    private HashMap<String, Boolean> subscriptionPairs;

    public KrakenAPIState(List<String> pairs) {
        currentState = State.DISCONNECTED;
        subscriptionPairs = new HashMap<>();
        for (String pair : pairs) {
            subscriptionPairs.put(pair, false);
        }
    }

    public void setSubscribed(String pair) {
        subscriptionPairs.put(pair, true);
        for (String p : subscriptionPairs.keySet()) {
            if (!subscriptionPairs.get(p)) {
                return;
            }
        }
        currentState = State.SUBSCRIBED;
    }

    public void setConnected() {
        currentState = State.CONNECTED_SUCCESSFULLY;
    }

    public State getCurrentState() {
        return currentState;
    }

}
