package leeshun.androidsip.services;

import android.content.Context;
import android.javax.sip.DialogTerminatedEvent;
import android.javax.sip.IOExceptionEvent;
import android.javax.sip.InvalidArgumentException;
import android.javax.sip.ListeningPoint;
import android.javax.sip.ObjectInUseException;
import android.javax.sip.RequestEvent;
import android.javax.sip.ResponseEvent;
import android.javax.sip.SipListener;
import android.javax.sip.SipProvider;
import android.javax.sip.TimeoutEvent;
import android.javax.sip.TransactionTerminatedEvent;
import android.javax.sip.TransportNotSupportedException;
import android.javax.sip.header.FromHeader;
import android.javax.sip.message.Request;
import android.javax.sip.message.Response;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TooManyListenersException;

import leeshun.androidsip.domain.ChatMessage;
import leeshun.androidsip.domain.Friend;
import leeshun.androidsip.handler.Listener;
import leeshun.androidsip.handler.RequestHandler;
import leeshun.androidsip.manager.FriendHolder;
import leeshun.androidsip.manager.GroupHolder;
import leeshun.androidsip.manager.MessageApplication;
import leeshun.androidsip.manager.SipManager;
import leeshun.androidsip.manager.SipProfile;
import leeshun.androidsip.state.State;

/**
 * Created by leeshun on 2017/7/12.
 */

public class SipService implements SipListener{
    private SipProfile sipProfile;
    private Context context;
    private SipManager sipManager;
    private RequestHandler requestHandler;

    public SipService(Context context, final String userName, final String remoteIP, final int remotePort) throws
            UnknownHostException,
            InvalidArgumentException,
            TransportNotSupportedException,
            TooManyListenersException,
            ObjectInUseException, ParseException, InterruptedException {
        sipProfile = SipProfile.getInstance(userName,remoteIP,remotePort);
        this.context = context;
        sipManager = SipManager.getInstance();
        requestHandler = new RequestHandler();
        initializeLocalIP();
        initializeActionThread();
        initializeListeningEndPoint();
        initializeSendThread();
    }

    private void initializeActionThread() {
        ActionIntoRequestThread thread = new ActionIntoRequestThread();
        thread.start();
    }

    private void initializeSendThread() throws UnknownHostException {
        SendRequestThread thread = new SendRequestThread();
        thread.start();
    }

    private void initializeListeningEndPoint() throws
            InvalidArgumentException,
            TransportNotSupportedException,
            ObjectInUseException,
            TooManyListenersException {
        System.err.println(sipProfile.getLocalIP() + sipProfile.getLocalPort());
                ListeningPoint udp = sipManager.getSipStack().createListeningPoint(sipProfile.getLocalIP(),
                        sipProfile.getLocalPort(), "udp");
                SipProvider provider = sipManager.getSipStack().createSipProvider(udp);
                provider.addSipListener(this);

                sipManager.setSipProvider(provider);
    }

    private void initializeLocalIP() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String localIP = intToIP(ipAddress);
        sipProfile.setLocalIP(localIP);
    }

    private String intToIP(final int ipAddress) {
        StringBuilder builder = new StringBuilder();
        builder.append(ipAddress & 0xFF).append(".");
        builder.append((ipAddress >> 8) & 0xFF).append(".");
        builder.append((ipAddress >> 16) & 0XFF).append(".");
        builder.append((ipAddress >> 24) & 0xFF);
        return builder.toString();
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        requestHandler.setRequestEvent(requestEvent);
        requestHandler.setRequest(requestEvent.getRequest());
        requestHandler.handler();
        /*Request request = requestEvent.getRequest();
        String method = request.getMethod();
        switch (method) {
            case Request.NOTIFY:
                break;
            case Request.MESSAGE:
                break;
        }*/
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        Request request = responseEvent.getClientTransaction().getRequest();
        String method = request.getMethod();
        String content = new String(responseEvent.getClientTransaction().getRequest().getRawContent());
        String type = content.substring(0,4);

        int status = response.getStatusCode();
        switch (method) {
            case Request.REGISTER:
                processRegister(status);
                break;
            case Request.MESSAGE:
                if(type.equals(State.LOGIN)) {
                    processLogin(status);
                }
                else if(type.equals(State.PERSON_MESSAGE)) {
                    String message = content.substring(4);
                    processPersonMessage(message,((FromHeader)request.getHeader(FromHeader.NAME)).getAddress().getDisplayName());
                }
                else if(type.equals(State.ALL_FRIEND)) {
                    String result = new String(response.getRawContent());
                    processAllFriend(result);
                }
                else if(type.equals(State.GET_FRIEND_LIST)) {
                    String result = new String(response.getRawContent());
                    processFriends(result);
                }
                else if(type.equals(State.ALL_GROUP)) {
                    String result = new String(response.getRawContent());
                    processAllGroup(result);
                }
                else if(type.equals(State.GET_GROUP_LIST)) {
                    String result = new String(response.getRawContent());
                    processGroups(result);
                }
                else if(type.equals(State.JOIN_GROUP)) {
                    //handle join group
                    System.err.println("@@@@@@@@@@" + "JOIN_GROUP");
                    String result = new String(response.getRawContent());
                    processJoinGroup(status,result);
                }
                break;
            case Request.INFO:
                String username = content.substring(0,content.indexOf('#'));
                String ipAddress = content.substring(content.indexOf('#') + 1);
                FriendHolder.getInstance().updateAddress(username,ipAddress);
                break;
        }


        Log.d("Response",response.toString());
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {

    }

    @Override
    public void processIOException(IOExceptionEvent ioExceptionEvent) {
        Log.e("IOException", ":" + ioExceptionEvent.toString());
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        Log.e("TansactionTerminated", ":" + transactionTerminatedEvent.toString());
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        Log.e("DialogTerminated", ":" + dialogTerminatedEvent.toString());
    }

    private void processLogin(int status) {
        if(status == Response.OK) {
            Listener.OnRegister(true);
        } else {
            Listener.OnRegister(false);
        }
    }

    private void processRegister(int status) {
        if(status == Response.OK) {
            Listener.OnLogin(true);
        } else {
            Listener.OnLogin(false);
        }
    }

    private void processJoinGroup(int status,String message) {
        System.err.println("ProcessJOINGROUP" + message);
        if (status == Response.OK) {
            Listener.AddGroup(message,true);
        } else {
            Listener.AddGroup(message,false);
        }
    }

    private void processPersonMessage(String message,String from) {
        if(from.equals(SipProfile.getInstance().getSipUserName())) {
            return;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSend(true);
        chatMessage.setGroupId(0);
        chatMessage.setUserId(SipProfile.getInstance().getSipUserName());
        chatMessage.setDate(new Date());
        chatMessage.setComing(true);
        chatMessage.setReaded(false);
        chatMessage.setMessage(message);
        chatMessage.setNickname(from);
        MessageApplication.getInstance().addMessage(chatMessage);
    }

    private void processAllFriend(String result) {
        String[] friends = result.split(";");
        String currentUsername = SipProfile.getInstance().getSipUserName();
        List<String> friendList = new ArrayList<>();
        for(int i = 0;i < friends.length;i += 2) {
            if(!FriendHolder.getInstance().hasFriend(friends[i]) &&
                    !currentUsername.equals(friends[i]))
                friendList.add(friends[i]);
        }
        Listener.OnNewFriendList(friendList);
    }

    private void processFriends(String result) {
        String[] friends = result.split(";");
        List<String> list = new ArrayList<>();
        for(int i = 0;i < friends.length;i += 2) {
            FriendHolder.getInstance().addFriend(new Friend(friends[i],friends[i + 1]));
            list.add(friends[i]);
        }
        Listener.OnFriendList(list);
    }

    private void processAllGroup(String result) {
        String[] groups = result.split(";");
        List<String> list = new ArrayList<>();
        for(int i = 0;i < groups.length;++i) {
            if(!GroupHolder.getInstance().hasGroup(groups[i])) {
                list.add(groups[i]);
            }
        }
        Listener.OnNewGroupList(list);
    }

    private void processGroups(String result) {
        String[] groups = result.split(";");
        List<String> list = new ArrayList<>();
        for(int i = 0;i < groups.length;++i) {
            list.add(groups[i]);
            GroupHolder.getInstance().addGroup(groups[i]);
        }
        Listener.OnGroupList(list);
    }
}
