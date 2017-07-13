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
import android.javax.sip.message.Request;
import android.javax.sip.message.Response;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import leeshun.androidsip.handler.Listener;
import leeshun.androidsip.handler.RequestHandler;
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
        String method = responseEvent.getClientTransaction().getRequest().getMethod();
        String content = new String(responseEvent.getClientTransaction().getRequest().getRawContent());
        String type = content.substring(0,4);

        int statu = response.getStatusCode();
        switch (method) {
            case Request.REGISTER:
                if(statu == Response.OK) {
                    Listener.OnRegister(true);
                } else {
                    Listener.OnRegister(false);
                }
                break;
            case Request.MESSAGE:
                if(type.equals(State.LOGIN)) {
                    if(statu == Response.OK) {
                        Listener.OnLogin(true);
                    } else {
                        Listener.OnLogin(false);
                    }
                }
                else if(type.equals(State.ALL_FRIEND)) {
                    String result = new String(response.getRawContent());
                    String[] friends = result.split(";");
                    List<String> friendList = new ArrayList<>();
                    for(int i = 0;i < friends.length;++i) {
                        friendList.add(friends[i]);
                    }
                    Listener.OnNewFriendList(friendList);
                }

                break;

            case Request.SUBSCRIBE:
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
}
