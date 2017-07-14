package leeshun.androidsip.handler;

import java.util.List;

/**
 * Created by leeshun on 2017/7/13.
 */

public class Listener {
    public static OnLoginListener onLoginListener;
    public static OnRegisterListener onRegisterListener;
    public static OnNewFriendListListener onNewFriendListListener;
    public static OnNewGroupListListener onNewGroupListListener;
    public static OnFriendListListener onFriendListListener;
    public static OnGroupListListener onGroupListListener;
    public static OnNewMessageListener onNewMessageListener;
    public static interface OnGroupListListener {
        public void OnGroupList(List<String> groups);
    }

    public static interface OnLoginListener {
        public void OnLogin(boolean result);
    }

    public static interface OnRegisterListener {
        public void OnRegister(boolean result);
    }

    public static interface OnNewFriendListListener {
        public void OnNewFriendList(List<String> users);
    }

    public static interface OnNewGroupListListener {
        public void OnNewGroupList(List<String> groups);
        public void addGroup(String groupName,boolean isAdd);
    }

    public static interface OnFriendListListener {
        public void OnFriendList(List<String> users);
    }
    public static interface OnNewMessageListener{
        public void OnNewMessage();
    }



    public static void OnLogin(boolean result) {
        onLoginListener.OnLogin(result);
    }

    public static void OnRegister(boolean result) {
        onRegisterListener.OnRegister(result);
    }

    public static void OnNewFriendList(List<String> users) {
        onNewFriendListListener.OnNewFriendList(users);
    }

    public static void OnNewGroupList(List<String> groups) {
        onNewGroupListListener.OnNewGroupList(groups);
    }

    public static void AddGroup(String groupName,boolean isAdd) {
        onNewGroupListListener.addGroup(groupName,isAdd);
    }

    public static void OnFriendList(List<String> users) {
        onFriendListListener.OnFriendList(users);
    }

    public static void OnGroupList(List<String> groups) {
        onGroupListListener.OnGroupList(groups);
    }
    public static void OnNewMessage()
    {
        if( onNewMessageListener!=null)
        {
            onNewMessageListener.OnNewMessage();
        }
    }

}
