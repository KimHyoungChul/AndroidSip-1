package leeshun.androidsip.manager;

import android.javax.sip.PeerUnavailableException;
import android.javax.sip.SipFactory;
import android.javax.sip.SipProvider;
import android.javax.sip.SipStack;
import android.javax.sip.address.AddressFactory;
import android.javax.sip.header.HeaderFactory;
import android.javax.sip.message.MessageFactory;

import java.util.Properties;

/**
 * Created by leeshun on 2017/7/12.
 */

public class SipManager {
    private AddressFactory addressFactory;
    private MessageFactory messageFactory;
    private HeaderFactory headerFactory;

    private SipFactory sipFactory;
    private SipStack sipStack;

    private SipProvider sipProvider;


    private static SipManager sipManager;


    private SipManager() {
        initialize();
    }

    private void initialize() {
        sipFactory = SipFactory.getInstance();
        sipFactory.resetFactory();
        sipFactory.setPathName("android.gov.nist");

        Properties properties = new Properties();
        properties.setProperty("android.javax.sip.STACK_NAME", "androidSip");
        //properties.setProperty("android.javax.sip.IP_ADDRESS",SipProfile.getInstance().getLocalIP());

        try {
            sipStack = sipFactory.createSipStack(properties);
            System.err.println(sipStack.toString());
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
        }
        try {
            headerFactory = sipFactory.createHeaderFactory();
            messageFactory = sipFactory.createMessageFactory();
            addressFactory = sipFactory.createAddressFactory();
        } catch (PeerUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SipManager getInstance() {
        if(sipManager == null) {
            sipManager = new SipManager();
        }
        return sipManager;
    }

    public AddressFactory getAddressFactory() {
        return addressFactory;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public HeaderFactory getHeaderFactory() {
        return headerFactory;
    }

    public SipStack getSipStack() {
        return sipStack;
    }

    public SipProvider getSipProvider() {
        return sipProvider;
    }

    public void setSipProvider(SipProvider sipProvider) {
        this.sipProvider = sipProvider;
    }
}
