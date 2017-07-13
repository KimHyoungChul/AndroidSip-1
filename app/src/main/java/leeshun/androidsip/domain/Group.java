package leeshun.androidsip.domain;

/**
 * Created by leeshun on 2017/7/13.
 */

public class Group {
    private String groupID;
    private String user;
    private int headIcon;

    public Group(String groupID, String user, int headIcon) {
        this.groupID = groupID;
        this.user = user;
        this.headIcon = headIcon;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(int headIcon) {
        this.headIcon = headIcon;
    }
}
