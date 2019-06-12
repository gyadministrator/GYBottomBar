package com.android.gybottombar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.bottombar.model.GYBarItem;
import com.android.bottombar.view.GYBottomBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GYBottomBarView.IGYBottomBarChangeListener {
    private GYBottomBarView bottomView;
    private List<GYBarItem> barItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomView = findViewById(R.id.bottomView);
        initBarItems();
        try {
            bottomView.setBarItems(barItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottomView.setBarChangeListener(this);
    }

    private void initBarItems() {
        barItems.add(new GYBarItem("首页", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("视频", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("资讯", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("我的", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("音乐", R.mipmap.ic_launcher));
    }

    @Override
    public void onSelected(int position) {
        Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
    }
}
