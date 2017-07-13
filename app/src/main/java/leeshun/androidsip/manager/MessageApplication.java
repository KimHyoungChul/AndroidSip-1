package leeshun.androidsip.manager;

import java.util.ArrayList;
import java.util.List;

import leeshun.androidsip.domain.ChatMessage;

/**
 * Created by leeshun on 2017/7/13.
 */

public class MessageApplication {
    private static List<ChatMessage> chatmsglist;
    private  static MessageApplication mApplication;

    private MessageApplication() {
        chatmsglist = new ArrayList<>();
    }

    public  static MessageApplication getInstance() {
        if(mApplication == null) {
            mApplication = new MessageApplication();
        }
        return mApplication;
    }



    public void addMessage(ChatMessage msg) {
        chatmsglist.add(msg);
    }

    public List<ChatMessage> getMessage(String userName,int groupID) {
        List<ChatMessage> mdatas = new ArrayList<>();
        for(ChatMessage each : chatmsglist) {
            if(each.getNickname().equals(userName) && each.getGroupId() == groupID) {
                mdatas.add(each);
            }
        }
        return  mdatas;
    }
}
