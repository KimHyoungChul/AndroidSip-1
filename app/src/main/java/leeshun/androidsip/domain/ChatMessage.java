package leeshun.androidsip.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leeshun on 2017/7/13.
 */

public class ChatMessage {
    private String message; // message content
    private boolean isComing; // if other send message to you ,set true
    private Date date; // message time
    private String userId; // sender username
    private int icon;
    private String nickname;// receiver username
    private boolean readed;
    private String dateStr;
    private boolean isSend;
    private int groupId; //Id=0表示私聊

    public ChatMessage() {
    }

    public ChatMessage(String message, boolean isComing,
                       String userId, int icon, String nickname,
                       boolean readed, String dateStr, int groupId) {
        this.message = message;
        this.isComing = isComing;
        this.userId = userId;
        this.icon = icon;
        this.nickname = nickname;
        this.readed = readed;
        this.dateStr = dateStr;
        this.groupId = groupId;
    }

    public ChatMessage(String message, boolean isComing,
                       String userId, int icon, String nickname,
                       boolean readed, String dateStr) {
        this.message = message;
        this.isComing = isComing;
        this.userId = userId;
        this.icon = icon;
        this.nickname = nickname;
        this.readed = readed;
        this.dateStr = dateStr;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isComing() {
        return isComing;
    }

    public void setComing(boolean coming) {
        isComing = coming;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = dateFormat.format(date);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
