package leeshun.androidsip.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.domain.ChatMessage;
import leeshun.androidsip.manager.MessageApplication;
import leeshun.androidsip.util.ChatMessageAdapter;

/**
 * Created by leeshun on 2017/7/13.
 */

public class ChattingActivity extends Activity {
    private TextView mNickName;
    private EditText mMsgInput;
    private Button mMsgSend;
    private ListView mChatMessagesListView;
    private List<ChatMessage> mDatas;
    private ChatMessageAdapter mAdapter;
    private int groupId;
    private String user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chatting);

        mDatas = new ArrayList<>();
        groupId = getIntent().getIntExtra("GroupId",0);

        initializeView();
        initializeEvent();
    }

    private void initializeView() {
        mChatMessagesListView = (ListView) findViewById(R.id.id_chat_listView);
        mMsgInput = (EditText) findViewById(R.id.id_chat_msg);
        mMsgSend = (Button) findViewById(R.id.id_chat_send);
        mNickName = (TextView) findViewById(R.id.id_nickname);

        user = getIntent().getStringExtra("user");

        if(TextUtils.isEmpty(user)) {
            this.finish();
        }

        mDatas = MessageApplication.getInstance().getMessage(10);
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mChatMessagesListView.setAdapter(mAdapter);
        mChatMessagesListView.setSelection(mDatas.size() - 1);
    }

    private void initializeEvent() {
        mMsgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mMsgInput.getText().toString();
                if(TextUtils.isEmpty(msg)) {
                    return;
                }
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setComing(false);
                chatMessage.setDate(new Date());
                chatMessage.setMessage(msg);
                chatMessage.setNickname(user);
                chatMessage.setUserId(user);

                MessageApplication.getInstance().addMessage(chatMessage);
                mDatas.add(chatMessage);
                mAdapter.notifyDataSetChanged();
                mChatMessagesListView.setSelection(mDatas.size() - 1);
                mMsgInput.setText("");

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()) {
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
    }
}
