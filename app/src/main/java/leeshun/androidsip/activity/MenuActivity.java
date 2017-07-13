package leeshun.androidsip.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

import leeshun.androidsip.R;
import leeshun.androidsip.ui.MenuTabFriends;
import leeshun.androidsip.ui.MenuTabGroups;
import leeshun.androidsip.ui.MenuTabNewFriends;
import leeshun.androidsip.ui.MenuTabNewGroups;

public class MenuActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private LinearLayout mTabLiaotian;
    private TextView mLiaotian;
    private TextView mQunliao;
    private TextView mJiahaoyou;
    private TextView mJiaqun;
    private BadgeView mBadgeViewforLiaotian;
    private MenuTabFriends mFrined;
    private MenuTabGroups mGroup;
    private MenuTabNewFriends newFriends;
    private MenuTabNewGroups newGroups;
    private ImageView mTabLine;
    private int screenWidth;
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeTabLine();
        initializeView();
        initializeFragmentAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mOnPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mLiaotian.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        break;
                    case 1:
                        mQunliao.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        break;
                    case 2:
                        mJiahaoyou.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        break;
                    case 3:
                        mJiaqun.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }


    private void mOnPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine
                .getLayoutParams();
        if (currentIndex == 0 && position == 0)// 0->1
        {
            lp.leftMargin = (int) (positionOffset
                    * (screenWidth * 1.0 / 4) + currentIndex
                    * (screenWidth / 4));

        } else if (currentIndex == 1 && position == 0) // 1->0
        {
            lp.leftMargin = (int) (-(1 - positionOffset)
                    * (screenWidth * 1.0 / 4) + currentIndex
                    * (screenWidth / 4));

        } else if (currentIndex == 1 && position == 1) // 1->2
        {

            lp.leftMargin = (int) (positionOffset
                    * (screenWidth * 1.0 / 4) + currentIndex
                    * (screenWidth / 4));
        } else if (currentIndex == 2 && position == 1) // 2->1
        {
            lp.leftMargin = (int) (-(1 - positionOffset)
                    * (screenWidth * 1.0 / 4) + currentIndex
                    * (screenWidth / 4));
        }else if (currentIndex == 2 && position == 2) // 2->3
        {
            lp.leftMargin = (int) (positionOffset
                    * (screenWidth * 1.0 / 4) + currentIndex
                    * (screenWidth / 4));
        }else if (currentIndex == 3 && position == 2) // 3->2
        {
            lp.leftMargin = (int) (-(1 - positionOffset)
                    * (screenWidth * 1.0 / 4) + currentIndex
                    * (screenWidth / 4));
        }

        mTabLine.setLayoutParams(lp);
    }

    private void initializeFragmentAdapter() {
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
    }

    private void initializeView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabLiaotian = (LinearLayout) findViewById(R.id.id_tab_liaotian_ly);

        mLiaotian = (TextView) findViewById(R.id.id_liaotian);
        mQunliao = (TextView) findViewById(R.id.id_qunliao);
        mJiahaoyou = (TextView) findViewById(R.id.id_jiahaoyou);
        mJiaqun=(TextView) findViewById(R.id.id_jiaqun);

        mFrined = new MenuTabFriends();
        mGroup  = new MenuTabGroups();
        newFriends = new MenuTabNewFriends();
        newGroups = new MenuTabNewGroups();
        mFragments.add(mFrined);
        mFragments.add(mGroup);
        mFragments.add(newFriends);
        mFragments.add(newGroups);
        mBadgeViewforLiaotian = new BadgeView(this);
        mTabLiaotian.addView(mBadgeViewforLiaotian);
        mBadgeViewforLiaotian.setVisibility(View.GONE);
    }

    private void initializeTabLine() {
        mTabLine = (ImageView) findViewById(R.id.id_tab_line);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine
                .getLayoutParams();
        lp.width = screenWidth / 4;
        mTabLine.setLayoutParams(lp);
    }

    protected void resetTextView() {
        mLiaotian.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        mQunliao.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        mJiahaoyou.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        mJiaqun.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
    }

    public void onChat(View view) {
        mViewPager.setCurrentItem(0);
    }

    public void onGroupChat(View view) {
        mViewPager.setCurrentItem(1);
    }

    public void onAddFriend(View view) {
        mViewPager.setCurrentItem(2);
    }

    public void onAddGroup(View view) {
        mViewPager.setCurrentItem(3);
    }
}
