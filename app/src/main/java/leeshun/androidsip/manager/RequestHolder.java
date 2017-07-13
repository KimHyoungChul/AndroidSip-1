package leeshun.androidsip.manager;

import android.javax.sip.message.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leeshun on 2017/7/12.
 */

public class RequestHolder {
    private BlockingQueue<Request> requests;

    private static RequestHolder helper;

    private RequestHolder() {
        requests = new LinkedBlockingQueue<>();
    }

    public static RequestHolder getInstance() {
        if(helper == null) {
            helper = new RequestHolder();
        }
        return helper;
    }

    public void addTask(Request request) throws InterruptedException {
        requests.put(request);
    }

    public Request getTask() throws InterruptedException {
        return requests.take();
    }
}
