package leeshun.androidsip.manager;

import android.util.Log;

import java.net.UnknownHostException;

/**
 * Created by leeshun on 2017/7/11.
 */

public class SipProfile {
    private final String TAG = "SipProfile";

    private String localIP;
    private int localPort;
    private String transport;


    private String remoteIP;
    private int remotePort;

    private String sipUserName;

    private static SipProfile sipProfile;

    public static SipProfile getInstance(final String sipUserName ,String remoteIP,final int remotePort) throws UnknownHostException {
        if(sipProfile == null) {
            sipProfile = new SipProfile();
            sipProfile.localPort = 5090;
            sipProfile.remoteIP = remoteIP;
            sipProfile.remotePort = remotePort;
            sipProfile.sipUserName = sipUserName;
        }
        return sipProfile;
    }

    public static synchronized SipProfile getInstance() {
        if(sipProfile == null) {
            throw new NullPointerException();
        }
        return sipProfile;
    }

    public String getLocalIP() {
        return localIP;
    }

    public void setLocalIP(final String localIP) {
        Log.d(TAG,"setting localIP " + localIP);
        this.localIP = localIP;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(final int localPort) {
        Log.d(TAG,"setting localPort " + localPort);
        this.localPort = localPort;
    }

    public String getLocalEndPoint() {
        return localIP + ":" + localPort;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(final String remoteIP) {
        Log.d(TAG,"setting remoteIP " + remoteIP);
        this.remoteIP = remoteIP;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(final int remotePort) {
        Log.d(TAG,"setting remotePort " + remotePort);
        this.remotePort = remotePort;
    }

    public String getRemoteEndPoint() {
        return remoteIP + ":" + remotePort;
    }

    public String getSipUserName() {
        return sipUserName;
    }

    public void setSipUserName(final String sipUserName) {
        Log.d(TAG,"setting sipUserName " + sipUserName);
        this.sipUserName = sipUserName;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(final String transport) {
        Log.d(TAG,"setting transport " + transport);
        this.transport = transport;
    }

    public String getRemoteSipAddress() {
        return "sip:admin@" + remoteIP + ":" + remotePort;
    }
}
