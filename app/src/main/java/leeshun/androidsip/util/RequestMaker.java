package leeshun.androidsip.util;

import android.javax.sip.InvalidArgumentException;
import android.javax.sip.address.SipURI;
import android.javax.sip.header.CSeqHeader;
import android.javax.sip.header.CallIdHeader;
import android.javax.sip.header.ContentTypeHeader;
import android.javax.sip.header.FromHeader;
import android.javax.sip.header.MaxForwardsHeader;
import android.javax.sip.header.ToHeader;
import android.javax.sip.message.Request;

import java.text.ParseException;
import java.util.ArrayList;

import leeshun.androidsip.manager.SipManager;
import leeshun.androidsip.state.State;

/**
 * Created by leeshun on 2017/7/12.
 */

public class RequestMaker {
    private HeaderMaker headerMaker;
    private SipManager sipManager;

    private static RequestMaker maker;

    private RequestMaker() {
        headerMaker = new HeaderMaker();
        sipManager = SipManager.getInstance();
    }

    public static RequestMaker getInstance() {
        if(maker == null) {
            maker = new RequestMaker();
        }
        return maker;
    }

    public Request makePublish(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.PUBLISH);
        request.setContent(message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeMessage(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.PERSON_MESSAGE + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeAllFriends(String to) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.ALL_FRIEND,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeAllGroups(String to) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.ALL_GROUP,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeRegister(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.REGISTER);
        ContentTypeHeader contentTypeHeader = headerMaker.makeContentTypeHeader();
        request.setContent(message,contentTypeHeader);
        return request;
    }

    public Request makeSubscribe(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.INFO);
        request.setContent(message,headerMaker.makeContentTypeHeader());
        request.addHeader(headerMaker.makeContactHeader());
        return request;
    }

    public Request makeGetFriendList(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.GET_FRIEND_LIST + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeGetGroupList(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.GET_GROUP_LIST + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeGroupMessage(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to, Request.MESSAGE);
        request.setContent(State.GROUP_MESSAGE + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeUpdatePassword(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.UPDATE_PASSWORD + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeJoinGroup(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.JOIN_GROUP + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeExitGroup(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.EXIT_GROUP + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeCreateGroup(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.CREATE_GROUP + message,headerMaker.makeContentTypeHeader());
        return request;
    }

    public Request makeLogin(String to,String message) throws ParseException, InvalidArgumentException {
        Request request = makeRequest(to,Request.MESSAGE);
        request.setContent(State.LOGIN + message,headerMaker.makeContentTypeHeader());
        return request;
    }


    private Request makeRequest(String to,String requestMethod) throws ParseException, InvalidArgumentException {
        System.err.println("makerrequest" + to);
        FromHeader fromHeader = headerMaker.makeFromHeader();
        ToHeader toHeader = headerMaker.makeToHeader(to);

        SipURI requestURI = sipManager.getAddressFactory().createSipURI(headerMaker.getAddress(to),headerMaker.getAddress(to));
        requestURI.setTransportParam("udp");

        ArrayList viaHeaders = headerMaker.makeViaHeader();

        CallIdHeader callIdHeader = sipManager.getSipProvider().getNewCallId();

        CSeqHeader cSeqHeader = sipManager.getHeaderFactory().createCSeqHeader(1L,requestMethod);

        MaxForwardsHeader maxForwardsHeader = sipManager.getHeaderFactory().createMaxForwardsHeader(70);

        Request request = sipManager.getMessageFactory().createRequest(requestURI,
                requestMethod, callIdHeader, cSeqHeader, fromHeader,
                toHeader, viaHeaders, maxForwardsHeader);
        return request;
    }


}
