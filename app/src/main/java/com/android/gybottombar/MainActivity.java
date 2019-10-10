package com.android.gybottombar;

import android.widget.Toast;

import com.android.bottombar.activity.GYBottomActivity;
import com.android.bottombar.model.GYBarItem;
import com.android.bottombar.view.GYBottomBarView;
import com.android.gybottombar.fragment.InfoFragment;
import com.android.gybottombar.fragment.ContactFragment;
import com.android.gybottombar.fragment.FindFragment;
import com.android.gybottombar.fragment.MyFragment;

public class MainActivity extends GYBottomActivity implements GYBottomBarView.IGYBottomBarChangeListener {
    private GYBottomBarView bottomView;

    @Override
    public void onSelected(int position) {
        Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initBarItems() {
        barItems.add(new GYBarItem("微信", R.mipmap.home_normal));
        barItems.add(new GYBarItem("通信录", R.mipmap.category_normal));
        barItems.add(new GYBarItem("发现", R.mipmap.service_normal));
        barItems.add(new GYBarItem("我", R.mipmap.mine_normal));
    }

    @Override
    protected void initFragment() {
        fragments.add(InfoFragment.newInstance());
        fragments.add(ContactFragment.newInstance());
        fragments.add(FindFragment.newInstance());
        fragments.add(MyFragment.newInstance());
    }

    @Override
    protected void initSelectIcons() {
        icons.add(R.mipmap.home_selected);
        icons.add(R.mipmap.category_selected);
        icons.add(R.mipmap.service_selected);
        icons.add(R.mipmap.mine_selected);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected GYBottomBarView getBottomBarView() {
        return bottomView;
    }

    @Override
    protected int initContainerId() {
        return R.id.fl_container;
    }

    @Override
    protected GYBottomBarView.IGYBottomBarChangeListener initChangeListener() {
        return this;
    }

    @Override
    protected void initView() {
        bottomView = findViewById(R.id.bottomView);
    }

    @Override
    protected void initPositionBadge() {
        super.initPositionBadge();
        bottomView.setPositionBadge(0, 6);
        bottomView.setPositionBadge(3, -1);
        bottomView.setPositionBadge(2, 100);
    }
}
