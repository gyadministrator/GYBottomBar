package com.android.gybottombar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.bottombar.activity.GYBottomActivity;
import com.android.bottombar.model.GYBarItem;
import com.android.bottombar.view.GYBottomBarView;
import com.android.gybottombar.fragment.InfoFragment;
import com.android.gybottombar.fragment.ContactFragment;
import com.android.gybottombar.fragment.FindFragment;
import com.android.gybottombar.fragment.LoginFragment;
import com.android.gybottombar.fragment.MyFragment;
import com.android.gybottombar.utils.UserManager;

public class MainActivity extends GYBottomActivity implements GYBottomBarView.IGYBottomBarChangeListener {
    private GYBottomBarView bottomView;
    private static final String TAG = "MainActivity";
    private static int mPosition = 0;

    @Override
    public void onSelected(int position) {
        mPosition = position;
        if (position == 2) {
            //gyBottomBarView.hideBadge(position);
            gyBottomBarView.hideBubblePosition(position);
        }
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
        if (UserManager.isIsLogin()) {
            fragments.add(MyFragment.newInstance());
        } else {
            LoginFragment loginFragment = LoginFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("isShow", "1");
            loginFragment.setArguments(bundle);
            fragments.add(loginFragment);
        }
    }

    @Override
    protected void initSelectIcons() {
        icons.add(R.mipmap.home_selected);
        icons.add(R.mipmap.category_selected);
        icons.add(R.mipmap.service_selected);
        icons.add(R.mipmap.mine_selected);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //gyBottomBarView.setBadge(0, 2);
                gyBottomBarView.setBubbleWithPosition(0, 2);
            }
        }, 2000);
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
        bottomView.setBubbleWithPosition(0, 6);
        bottomView.setBubbleWithPosition(2, 8);
        bottomView.setBubbleWithPosition(3, 100);
        bottomView.setBubbleWithPosition(1, -1);
        /*bottomView.setBadgeWithBg(0, 6, "#FF0000");
        bottomView.setBadgeWithBg(2, 8, "#FF0000");
        bottomView.setBadgeWithBg(3, 100, "#FF0000");
        bottomView.setBadgeWithBg(1, -1, "#FF0000");*/
    }

    public void goLogin() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, LoginFragment.newInstance());
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitNow();
    }

    public void changeFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
        Log.e(TAG, "changeFragment: " + mPosition);
        Fragment fragment;
        switch (mPosition) {
            case 0:
                fragment = InfoFragment.newInstance();
                break;
            case 1:
                fragment = ContactFragment.newInstance();
                break;
            case 2:
                fragment = FindFragment.newInstance();
                break;
            case 3:
                fragment = MyFragment.newInstance();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mPosition);
        }
        fragments.remove(currentFragment);
        fragments.add(mPosition, fragment);
        bottomView.updateFragment(mPosition);
        mPosition = 0;
        UserManager.setIsLogin(true);
    }
}
