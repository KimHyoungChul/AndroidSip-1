package leeshun.androidsip.handler;

import android.javax.sip.RequestEvent;
import android.javax.sip.header.FromHeader;
import android.javax.sip.header.ToHeader;
import android.javax.sip.message.Request;

import java.util.Date;

import leeshun.androidsip.domain.ChatMessage;
import leeshun.androidsip.manager.MessageApplication;
import leeshun.androidsip.state.State;

/**
 * Created by leeshun on 2017/7/13.
 */

public class RequestHandler {
    private RequestEvent requestEvent;
    private String content;
    private Request request;

    public RequestHandler() {
    }

    public void setRequestEvent(RequestEvent requestEvent) {
        this.requestEvent = requestEvent;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void handler() {
        content = new String(request.getRawContent());
        String method = request.getMethod();
        switch (method) {
            case Request.NOTIFY:
                notifyHandler();
                break;
            case Request.MESSAGE:
                messageHandler();
                break;
        }
    }

    private void notifyHandler() {

    }

    private void messageHandler() {
        if (content == null || content.equals("") || content.length() < 4) {
            throw new NullPointerException();
        }
        String type = content.substring(0,4);
        String message = content.substring(4);

        switch (type) {
            case State.PERSON_MESSAGE:
                onPersonMessage(message);
                break;
            case State.GROUP_MESSAGE:
                break;
            case State.JOIN_GROUP:
                break;
            case State.EXIT_GROUP:
                break;
        }
    }

    private void onPersonMessage(String message) {
        System.err.println(message);
        String from = ((FromHeader)request.getHeader(FromHeader.NAME)).getAddress().getDisplayName();
        String owner = ((ToHeader)request.getHeader(ToHeader.NAME)).getAddress().getDisplayName();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(owner);
        chatMessage.setNickname(from);
        chatMessage.setMessage(message);
        chatMessage.setDate(new Date());
        chatMessage.setComing(true);
        chatMessage.setReaded(false);

        MessageApplication.getInstance().addMessage(chatMessage);
    }
}
