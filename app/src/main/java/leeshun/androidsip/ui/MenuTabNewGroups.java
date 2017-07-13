package leeshun.androidsip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.domain.Interaction;
import leeshun.androidsip.handler.Listener;
import leeshun.androidsip.manager.ActionHolder;
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
    private List<String> mDatas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        mDatas = new ArrayList<>();
        mAdapter = new GroupListAdapter(mDatas,mInflater);
        Listener.onNewGroupListListener = this;
        ActionHolder.getInstance().addAction(new Interaction(Action.ALL_GROUP,"",""));
        refreash();
    }

    @Override
    public void onResume()
    {
        super.onResume();
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

                //add friend

            }
        });
        mAdapter.notifyDataSetChanged();
        return view;
    }
    public void addGroup(String user) {
        mDatas.add(user);
        mAdapter.notifyDataSetChanged();
    }
    public void refreash()
    {

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void OnNewGroupList(List<String> groups) {
        mDatas.clear();
        mDatas.addAll(groups);
        refreash();
    }
}
