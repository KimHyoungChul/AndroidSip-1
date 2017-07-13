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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.domain.ChatMessage;
import leeshun.androidsip.domain.Interaction;
import leeshun.androidsip.manager.ActionHolder;
import leeshun.androidsip.manager.FriendHolder;
import leeshun.androidsip.manager.MessageApplication;
import leeshun.androidsip.manager.SipProfile;
import leeshun.androidsip.state.Action;
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

        mNickName.setText(user);
        mDatas = MessageApplication.getInstance().getMessage(user,groupId);
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mChatMessagesListView.setAdapter(mAdapter);
        mChatMessagesListView.setSelection(mDatas.size() - 1);
    }

    private void initializeEvent() {
        mMsgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = mMsgInput.getText().toString();
                if(TextUtils.isEmpty(msg)) {
                    return;
                }
                final ChatMessage chatMessage = new ChatMessage();
                chatMessage.setComing(false);
                chatMessage.setDate(new Date());
                chatMessage.setMessage(msg);
                chatMessage.setNickname(user);
                chatMessage.setUserId(SipProfile.getInstance().getSipUserName());
                chatMessage.setGroupId(groupId);
                chatMessage.setSend(false);
                if(groupId == 0) {
                    if (FriendHolder.getInstance().getSipAddress(user).equals("")) {
                        Toast.makeText(ChattingActivity.this,"对方不在线",Toast.LENGTH_SHORT).show();
                        System.err.println("into subscribe");
                    } else {
                        ActionHolder.getInstance().addAction(new Interaction(Action.PERSON_MESSAGE,
                                FriendHolder.getInstance().getSipAddress(user),msg));
                        chatMessage.setSend(true);
                    }
                } else {
                    ActionHolder.getInstance().addAction(new Interaction(Action.GROUP_MESSAGE,
                            "",user + "#" + msg));
                }

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if(!FriendHolder.getInstance().getSipAddress(user).equals("")) {
                                ActionHolder.getInstance().addAction(new Interaction(Action.PERSON_MESSAGE,"",msg));
                                chatMessage.setSend(true);
                            }
                        }
                    }
                }).start();*/

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
