package leeshun.androidsip.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leeshun on 2017/7/12.
 */

public class GroupHolder {
    private List<String> groups;

    private static GroupHolder holder;

    private GroupHolder() {
        groups = new ArrayList<>();
    }

    public static GroupHolder getInstance() {
        if(holder == null) {
            holder = new GroupHolder();
        }
        return holder;
    }

    public boolean hasGroup(String groupName) {
        for (String each : groups) {
            if(each.equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    public boolean addGroup(String groupName) {
        return groups.add(groupName);
    }

    public boolean deleteGroup(String groupName) {
        return groups.remove(groupName);
    }

}
