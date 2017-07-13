package leeshun.androidsip.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import leeshun.androidsip.R;

/**
 * Created by leeshun on 2017/7/13.
 */

public class GroupListAdapter extends BaseAdapter {
    private List<String> groups;
    private LayoutInflater mInflater;

    public GroupListAdapter(List<String> groups, LayoutInflater mInflater) {
        this.groups = groups;
        this.mInflater = mInflater;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String groupID = groups.get(position);
        viewHolder holder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.main_tab_weixin_info_item, parent, false);
            holder = new viewHolder();
            holder.mNickname = (TextView) convertView
                    .findViewById(R.id.id_nickname);
            holder.mUserId = (TextView) convertView
                    .findViewById(R.id.id_userId);
            holder.mWapper = (RelativeLayout) convertView
                    .findViewById(R.id.id_item_ly);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.mNickname.setText(groupID);
        holder.mUserId.setText(groupID);
        return convertView;
    }

    private final class viewHolder {
        TextView mNickname;
        TextView mUserId;
        RelativeLayout mWapper;
    }
}
