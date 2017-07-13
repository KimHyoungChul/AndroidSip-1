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

public class FriendListAdapter extends BaseAdapter {
    private List<String> users;
    private LayoutInflater inflater;

    public FriendListAdapter(List<String> users, LayoutInflater inflater) {
        this.users = users;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String user = users.get(position);
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.main_tab_weixin_info_item,parent,false);
            holder = new ViewHolder();
            holder.mNickname = (TextView) convertView
                    .findViewById(R.id.id_nickname);
            holder.mWapper = (RelativeLayout) convertView
                    .findViewById(R.id.id_item_ly);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mNickname.setText(users.get(position));
        return convertView;
    }


    private final  class ViewHolder {
        TextView mNickname;
        RelativeLayout mWapper;
        //BadgeView mBadgeView;
    }
}
