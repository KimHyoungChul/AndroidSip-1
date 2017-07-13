package leeshun.androidsip.util;

import android.javax.sip.InvalidArgumentException;
import android.javax.sip.SipProvider;
import android.javax.sip.address.Address;
import android.javax.sip.address.AddressFactory;
import android.javax.sip.address.SipURI;
import android.javax.sip.header.ContactHeader;
import android.javax.sip.header.ContentTypeHeader;
import android.javax.sip.header.FromHeader;
import android.javax.sip.header.HeaderFactory;
import android.javax.sip.header.ToHeader;
import android.javax.sip.header.ViaHeader;
import android.javax.sip.message.MessageFactory;

import java.text.ParseException;
import java.util.ArrayList;

import leeshun.androidsip.manager.SipManager;
import leeshun.androidsip.manager.SipProfile;

/**
 * Created by leeshun on 2017/7/12.
 */
public class HeaderMaker {
    private SipManager sipManager;
    private SipProfile sipProfile;

    private SipProvider sipProvider;
    private AddressFactory addressFactory;
    private MessageFactory messageFactory;
    private HeaderFactory headerFactory;

    public HeaderMaker() {
        this.sipManager = SipManager.getInstance();
        this.sipProfile = SipProfile.getInstance();

        addressFactory = sipManager.getAddressFactory();
        messageFactory = sipManager.getMessageFactory();
        headerFactory = sipManager.getHeaderFactory();
    }

    public FromHeader makeFromHeader() throws ParseException {
        SipURI sipURI = addressFactory.createSipURI(sipProfile.getSipUserName(),sipProfile.getLocalIP());
        sipURI.setPort(sipProfile.getLocalPort());
        Address address = addressFactory.createAddress(sipURI);
        address.setDisplayName(sipProfile.getSipUserName());
        return headerFactory.createFromHeader(address,"textserver");
    }


    public String getUsername(String to) {
        return to.substring(to.indexOf(":") + 1, to.indexOf("@"));
    }

    public String getAddress(String to) {
        return to.substring(to.indexOf("@") + 1);
    }

    public ToHeader makeToHeader(String to) throws ParseException {
        String username = getUsername(to);
        String address = getAddress(to);
        SipURI toAddress = addressFactory.createSipURI(username, address);
        Address toNameAddress = addressFactory.createAddress(toAddress);
        toNameAddress.setDisplayName(username);
        ToHeader toHeader = headerFactory.createToHeader(toNameAddress,"toAddress");

        return toHeader;
    }

    public ArrayList makeViaHeader() throws ParseException, InvalidArgumentException {
        ArrayList viaHeaders = new ArrayList();
        ViaHeader viaHeader = headerFactory.createViaHeader(sipProfile.getLocalIP(),
                sipProfile.getLocalPort(),"udp","branch");
        viaHeaders.add(viaHeader);

        return viaHeaders;
    }

    public ContactHeader makeContactHeader() throws ParseException, InvalidArgumentException {
        SipURI contactURI = addressFactory.createSipURI(sipProfile.getSipUserName(),sipProfile.getLocalIP());
        contactURI.setPort(sipProfile.getLocalPort());
        Address contactAddress = addressFactory.createAddress(contactURI);
        contactAddress.setDisplayName(sipProfile.getSipUserName());

        ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
        contactHeader.setExpires(3600);

        return contactHeader;
    }

    public ContentTypeHeader makeContentTypeHeader() throws ParseException {
        return headerFactory.createContentTypeHeader("text","plain");
    }
}
