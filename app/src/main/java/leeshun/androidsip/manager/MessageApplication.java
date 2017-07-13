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



    public  void addMessage(ChatMessage msg) {
        System.err.println(msg.toString());
        chatmsglist.add(msg);
    }

    public  List<ChatMessage> getMessage(int count) {
        List<ChatMessage>mdatas=new ArrayList<ChatMessage>();
        for(int i = 0;i < count && i < chatmsglist.size();) {
            mdatas.add(chatmsglist.get(i));

            i++;
        }
        return  mdatas;
    }
}
