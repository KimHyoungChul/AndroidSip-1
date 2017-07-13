package leeshun.androidsip.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.domain.Interaction;
import leeshun.androidsip.handler.Listener;
import leeshun.androidsip.manager.ActionHolder;
import leeshun.androidsip.manager.GroupHolder;
import leeshun.androidsip.state.Action;
import leeshun.androidsip.util.GroupListAdapter;

/**
 * Created by leeshun on 2017/7/13.
 */

public class MenuTabNewGroups extends Fragment implements Listener.OnNewGroupListListener {
    private GroupListAdapter mAdapter;
    private LayoutInflater mInflater;
    private ListView mFriends;
    private View mEmptyView;
    private Handler handler;
    private List<String> mDatas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        mDatas = new ArrayList<>();
        mAdapter = new GroupListAdapter(mDatas,mInflater);
        Listener.onNewGroupListListener = this;
        handler = new Handler();
        ActionHolder.getInstance().addAction(new Interaction(Action.ALL_GROUP,"",""));
        refreash();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //ActionHolder.getInstance().addAction(new Interaction(Action.ALL_GROUP,"",""));
        refreash();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab_weixin, container, false);
        mFriends = (ListView) view.findViewById(R.id.id_list_friends);
        mEmptyView = inflater.inflate(R.layout.no_zuo_no_die, container, false);
        mFriends.setEmptyView(mEmptyView);

        mFriends.setAdapter(mAdapter);
        mFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActionHolder.getInstance().addAction(new Interaction(Action.JOIN_GROUP,"",mDatas.get(position)));
            }
        });
        mAdapter.notifyDataSetChanged();
        return view;
    }

    public void refreash() {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void OnNewGroupList(List<String> groups) {
        mDatas.clear();
        mDatas.addAll(groups);
        refreash();
    }

    @Override
    public void addGroup(String groupName, boolean isAdd) {
        System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if(isAdd) {
            mDatas.remove(groupName);
            refreash();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),"加群成功",Toast.LENGTH_SHORT).show();
                }
            });
            System.err.println("@@@@@@@@@@@@@@" + groupName);
            GroupHolder.getInstance().addGroup(groupName);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),"加群失败",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
