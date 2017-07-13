package leeshun.androidsip.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.domain.ChatMessage;

/**
 * Created by leeshun on 2017/7/13.
 */

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ChatMessage> messages;

    public ChatMessageAdapter(Context context,List<ChatMessage> datas) {
        inflater = LayoutInflater.from(context);
        messages = datas;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = messages.get(position);

        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            if (chatMessage.isComing())
            {
                convertView = inflater.inflate(R.layout.main_chat_from_msg,
                        parent, false);
                viewHolder.createDate = (TextView) convertView
                        .findViewById(R.id.chat_from_createDate);
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.chat_from_content);
                viewHolder.nickname = (TextView) convertView
                        .findViewById(R.id.chat_from_name);
                convertView.setTag(viewHolder);
            } else
            {
                convertView = inflater.inflate(R.layout.main_chat_send_msg,
                        null);
                viewHolder.createDate = (TextView) convertView
                        .findViewById(R.id.chat_send_createDate);
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.chat_send_content);
                viewHolder.nickname = (TextView) convertView
                        .findViewById(R.id.chat_send_name);
                convertView.setTag(viewHolder);
            }

        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//		Date date = chatMessage.getDate();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.content.setText(chatMessage.getMessage());
        viewHolder.createDate.setText(chatMessage.getDateStr());
        viewHolder.nickname.setText(chatMessage.getNickname());

        return convertView;
    }

    private class ViewHolder {
        public TextView createDate;
        public TextView nickname;
        public TextView content;
    }
}
