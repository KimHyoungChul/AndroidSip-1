package leeshun.androidsip.domain;

import java.io.Serializable;

import leeshun.androidsip.state.Action;

/**
 * Created by leeshun on 2017/7/12.
 */

public class Interaction implements Serializable {
    private Action action;
    private String to;
    private String message;

    public Interaction(Action action, String to, String message) {
        this.action = action;
        this.to = to;
        this.message = message;
    }

    public Action getAction() {
        return action;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
