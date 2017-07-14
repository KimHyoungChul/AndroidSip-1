package leeshun.androidsip.services;


import android.javax.sip.InvalidArgumentException;
import android.javax.sip.message.Request;

import java.text.ParseException;

import leeshun.androidsip.domain.Interaction;
import leeshun.androidsip.manager.ActionHolder;
import leeshun.androidsip.manager.FriendHolder;
import leeshun.androidsip.manager.RequestHolder;
import leeshun.androidsip.manager.SipProfile;
import leeshun.androidsip.state.Action;
import leeshun.androidsip.util.RequestMaker;

/**
 * Created by leeshun on 2017/7/12.
 */

public class ActionIntoRequestThread implements Runnable {
    private ActionHolder holder;
    private RequestMaker maker;
    private RequestHolder requestHolder;
    private FriendHolder friendHolder;

    public ActionIntoRequestThread() {
        holder = ActionHolder.getInstance();
        maker = RequestMaker.getInstance();
        requestHolder = RequestHolder.getInstance();
        friendHolder = FriendHolder.getInstance();
    }

    public void start() {
        new Thread(this).start();
    }


    @Override
    public void run() {
        while (true) {
            try {
                requestHolder.addTask(actionToRequest(holder.getAction()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private Request actionToRequest(Interaction interaction) throws ParseException, InvalidArgumentException {
        Action action = interaction.getAction();
        Request request = null;
        if(action == Action.GROUP_MESSAGE) {
            request =  maker.makeGroupMessage(SipProfile.getInstance().getRemoteSipAddress(),interaction.getMessage());
        }
        else if(action == Action.JOIN_GROUP ) {
            request = maker.makeJoinGroup(SipProfile.getInstance().getRemoteSipAddress(),
                    interaction.getMessage() + "#" + SipProfile.getInstance().getSipUserName());
        }
        else if(action == Action.EXIT_GROUP) {
            request = maker.makeExitGroup(SipProfile.getInstance().getRemoteSipAddress(),
                    interaction.getMessage() + "#" + SipProfile.getInstance().getSipUserName());
        }
        else if(action == Action.CREATE_GROUP) {
            request = maker.makeCreateGroup(SipProfile.getInstance().getRemoteSipAddress(),
                    interaction.getMessage() + "#" + SipProfile.getInstance().getSipUserName());
        }
        else if(action == Action.LOGIN) {
            request = maker.makeLogin(SipProfile.getInstance().getRemoteSipAddress(),interaction.getMessage());
        }
        else if(action == Action.FRIEND_LIST) {
            request = maker.makeGetFriendList(SipProfile.getInstance().getRemoteSipAddress(),SipProfile.getInstance().getSipUserName());
        }
        else if(action == Action.GROUP_LIST) {
            request = maker.makeGetGroupList(SipProfile.getInstance().getRemoteSipAddress(),SipProfile.getInstance().getSipUserName());
        }
        else if(action == Action.UPDATE_PASSWORD) {
            request = maker.makeUpdatePassword(SipProfile.getInstance().getRemoteSipAddress(),SipProfile.getInstance().getSipUserName());
        }
        else if(action == Action.SUBSCRIBE) {
            request = maker.makeSubscribe(SipProfile.getInstance().getRemoteSipAddress(),interaction.getMessage());
        }
        else if(action == Action.PUBLISH) {
            request = maker.makePublish(SipProfile.getInstance().getRemoteSipAddress(),SipProfile.getInstance().getLocalIP());
        }
        else if(action == Action.PERSON_MESSAGE) {
            System.err.println("Person -----------          " + interaction.getMessage());
            request = maker.makeMessage(interaction.getTo(),interaction.getMessage());
        }
        else if(action == Action.REGISTER) {
            request = maker.makeRegister(SipProfile.getInstance().getRemoteSipAddress(),interaction.getMessage());
        }
        else if(action == Action.ALL_FRIEND) {
            request = maker.makeAllFriends(SipProfile.getInstance().getRemoteSipAddress());
        }
        else if(action == Action.ALL_GROUP) {
            System.err.println("---------------------------------------------");
            System.err.println(SipProfile.getInstance().getRemoteSipAddress());
            request = maker.makeAllGroups(SipProfile.getInstance().getRemoteSipAddress());
        }
        return request;
    }
}
