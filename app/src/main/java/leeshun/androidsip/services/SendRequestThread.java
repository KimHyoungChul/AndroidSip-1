package leeshun.androidsip.services;

import android.javax.sip.ClientTransaction;
import android.javax.sip.SipException;
import android.javax.sip.SipProvider;
import android.javax.sip.TransactionUnavailableException;
import android.javax.sip.message.Request;

import java.net.UnknownHostException;

import leeshun.androidsip.manager.RequestHolder;
import leeshun.androidsip.manager.SipManager;

/**
 * Created by leeshun on 2017/7/12.
 */

public class SendRequestThread implements Runnable{
    private SipManager sipManager;
    private SipProvider provider;

    private RequestHolder helper;

    public SendRequestThread() throws UnknownHostException {
        sipManager = SipManager.getInstance();
        provider = sipManager.getSipProvider();
        helper = RequestHolder.getInstance();
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //System.err.println("start send");
                Request request = helper.getTask();
                System.err.println(request.toString());
                ClientTransaction transaction = provider.getNewClientTransaction(request);
                //make a record which contain the relating message of the transaction and the request
                transaction.sendRequest();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TransactionUnavailableException e) {
                e.printStackTrace();
            } catch (SipException e) {
                e.printStackTrace();
            }
        }
    }
}
