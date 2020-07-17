package com.android.bottombar.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.android.bottombar.model.GYBarItem;
import com.android.bottombar.view.GYBottomBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: GYBottomBar
 * Created by gy(1984629668@qq.com)
 * Created Time on 2019/10/9 16:22
 */
public abstract class GYBottomActivity extends AppCompatActivity {
    protected List<GYBarItem> barItems = new ArrayList<>();
    protected List<Fragment> fragments = new ArrayList<>();
    protected List<Integer> icons = new ArrayList<>();
    protected GYBottomBarView gyBottomBarView;

    /**
     * 初始化底部项
     */
    protected abstract void initBarItems();

    /**
     * 初始化fragment
     */
    protected abstract void initFragment();

    /**
     * 初始化选中图标
     */
    protected abstract void initSelectIcons();

    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract int initContentView();

    /**
     * 获取底部控件
     *
     * @return
     */
    protected abstract GYBottomBarView getBottomBarView();

    /**
     * 初始化角标
     */
    protected void initPositionBadge() {

    }

    protected abstract int initContainerId();

    protected abstract GYBottomBarView.IGYBottomBarChangeListener initChangeListener();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initContentView() == 0) {
            try {
                throw new Exception("布局必须设置");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setContentView(initContentView());

            initView();
            gyBottomBarView = getBottomBarView();
            initBarItems();
            gyBottomBarView.setBarItems(barItems);
            initPositionBadge();
            initFragment();
            initSelectIcons();
            gyBottomBarView.setSelectIcon(icons);
            if (initContainerId() == 0) {
                try {
                    throw new Exception("containerId不能为空");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                gyBottomBarView.setFragments(getSupportFragmentManager(), fragments, initContainerId());

                if (initChangeListener() != null) {
                    gyBottomBarView.setBarChangeListener(initChangeListener());
                }
            }
        }
    }
}
