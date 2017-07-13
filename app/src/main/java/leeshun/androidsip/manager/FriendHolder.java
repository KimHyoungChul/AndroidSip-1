package leeshun.androidsip.manager;

import java.util.ArrayList;
import java.util.List;

import leeshun.androidsip.domain.Friend;

/**
 * Created by leeshun on 2017/7/12.
 */

public class FriendHolder {
    private List<Friend> friends;

    private static FriendHolder holder;

    private FriendHolder() {
        friends = new ArrayList<>();
    }

    public synchronized static FriendHolder getInstance() {
        if(holder == null) {
            holder = new FriendHolder();
        }
        return holder;
    }

    public boolean addFriend(Friend friend) {
        return friends.add(friend);
    }

    public boolean updateAddress(String username,String IPAddress) {
        for(Friend each : friends) {
            if(each.getUsername().equals(username)) {
                each.setIpAddress(IPAddress);
                return true;
            }
        }
        return false;
    }

    public String getSipAddress(String username) {
        for(Friend each : friends) {
            if(each.getUsername().equals(username)) {
                return "sip:" + username + "@" + each.getIpAddress() + ":5090";
            }
        }
        return "";
    }

    public boolean hasFriend(String username) {
        for(Friend each : friends) {
            if(each.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
