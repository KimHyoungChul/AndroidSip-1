package leeshun.androidsip.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import leeshun.androidsip.domain.Interaction;

/**
 * Created by leeshun on 2017/7/12.
 */

public class ActionHolder {
    private BlockingQueue<Interaction> actions;

    private static ActionHolder holder;

    private ActionHolder() {
        actions = new LinkedBlockingQueue<>();
    }

    public static synchronized ActionHolder getInstance() {
        if(holder == null) {
            holder = new ActionHolder();
        }
        return holder;
    }

    public Interaction getAction() throws InterruptedException {
        return actions.take();
    }

    public void addAction(Interaction interaction) {
        actions.add(interaction);
    }
}
