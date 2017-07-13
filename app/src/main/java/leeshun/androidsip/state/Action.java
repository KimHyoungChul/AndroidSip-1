package leeshun.androidsip.state;

/**
 * Created by leeshun on 2017/7/12.
 */

public enum Action {
    //get all friends
    ALL_FRIEND,

    //get all groups
    ALL_GROUP,

    //send message to person
    PERSON_MESSAGE,

    //add friend or get friend ip address
    SUBSCRIBE,

    //register a new user into a register server
    REGISTER,

    //tell other you are inline and update your ip address
    PUBLISH,

    //get my friend list
    FRIEND_LIST,

    //get my group list
    GROUP_LIST,

    //change the password
    UPDATE_PASSWORD,

    //login
    LOGIN,

    //send group message
    GROUP_MESSAGE,

    //join a new group
    JOIN_GROUP,

    //exit a old group
    EXIT_GROUP,

    //create a new group
    CREATE_GROUP
}
