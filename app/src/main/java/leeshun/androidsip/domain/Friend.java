package leeshun.androidsip.domain;

/**
 * Created by leeshun on 2017/7/12.
 */

public class Friend {
    private String username;
    private String ipAddress;

    public Friend(String username, String ipAddress) {
        this.username = username;
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
