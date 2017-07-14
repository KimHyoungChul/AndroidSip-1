package leeshun.androidsip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.activity.ChattingActivity;
import leeshun.androidsip.domain.Interaction;
import leeshun.androidsip.handler.Listener;
import leeshun.androidsip.manager.ActionHolder;
import leeshun.androidsip.manager.GroupHolder;
import leeshun.androidsip.state.Action;
import leeshun.androidsip.util.GroupListAdapter;

/**
 * Created by leeshun on 2017/7/13.
 */

public class MenuTabGroups extends Fragment implements Listener.OnGroupListListener {
    private GroupListAdapter mAdapter;
    private LayoutInflater mInflater;
    private ListView mGroups;
    private View mEmptyView;
    private List<String> mDatas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        mDatas=new LinkedList<>();
        mAdapter = new GroupListAdapter(mDatas,mInflater);
        Listener.onGroupListListener = this;
        ActionHolder.getInstance().addAction(new Interaction(Action.GROUP_LIST,"",""));
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatas.clear();
        mDatas.addAll(GroupHolder.getInstance().getAllGroups());
        mAdapter.notifyDataSetChanged();
        //ActionHolder.getInstance().addAction(new Interaction(Action.GROUP_LIST,"",""));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.main_tab_weixin, container, false);
        mGroups = (ListView) view.findViewById(R.id.id_list_friends);
        mEmptyView = inflater.inflate(R.layout.no_zuo_no_die, container, false);
        mGroups.setEmptyView(mEmptyView);
        mGroups.setAdapter(mAdapter);
        mGroups.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {


                Intent intent = new Intent(getActivity(),
                        ChattingActivity.class);
                intent.putExtra("user", mDatas.get(position));//mDatas.get(position).getUserId()
                intent.putExtra("GroupId",1);
                startActivity(intent);
            }

        });

        mAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void OnGroupList(List<String> groups) {
        mDatas.clear();
        mDatas.addAll(groups);
        mAdapter.notifyDataSetChanged();
    }
}
