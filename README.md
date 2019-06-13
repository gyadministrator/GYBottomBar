# GYBottomBar
方便的底部栏
先上图！！！！！：

![image](https://github.com/gyadministrator/GYBottomBar/blob/master/images/20190613102553.png)
![image](https://github.com/gyadministrator/GYBottomBar/blob/master/images/20190613102037.png)

How to use?

Gradle方式：
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven {
				url 'https://jitpack.io'
			}
		}
	}
  
  Step 2. Add the dependency


  dependencies {
		
		implementation 'com.github.gyadministrator:GYBottomBar:1.1'
	  }
  
  Maven方式:

  ep 1. Add the JitPack repository to your build file

  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
  Step 2. Add the dependency
  
  	<dependency>
	    <groupId>com.github.gyadministrator</groupId>
	    <artifactId>GYBottomBar</artifactId>
	    <version>1.0</version>
	</dependency>
  
  Use in Activity, So Easy!!!
  
  在xml中添加这个view
  
 <FrameLayout
        android:id="@+id/fl_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <com.android.bottombar.view.GYBottomBarView
        android:id="@+id/bottomView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:normalTextColor="#CCCCCC" />

  activity中使用
  
  package com.android.gybottombar;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.bottombar.model.GYBarItem;
import com.android.bottombar.view.GYBottomBarView;
import com.android.gybottombar.fragment.TestFragment;
import com.android.gybottombar.fragment.TestFragment1;
import com.android.gybottombar.fragment.TestFragment2;
import com.android.gybottombar.fragment.TestFragment3;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GYBottomBarView.IGYBottomBarChangeListener {
    private GYBottomBarView bottomView;
    private List<GYBarItem> barItems = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomView = findViewById(R.id.bottomView);
        initBarItems();
        bottomView.setBarItems(barItems);
        bottomView.setPositionBadge(0, 6);
        bottomView.setPositionBadge(1, 0);
        bottomView.setPositionBadge(2, 100);
        initFragment();
        initIcons();
        bottomView.setSelectIcon(icons);
        bottomView.setFragments(getSupportFragmentManager(), fragments, R.id.fl_container);
        bottomView.setBarChangeListener(this);
    }

    private void initIcons() {
        icons.add(R.drawable.more);
        icons.add(R.drawable.more);
        icons.add(R.drawable.more);
        icons.add(R.drawable.more);
    }

    private void initFragment() {
        fragments.add(TestFragment.newInstance());
        fragments.add(TestFragment1.newInstance());
        fragments.add(TestFragment2.newInstance());
        fragments.add(TestFragment3.newInstance());
    }

    private void initBarItems() {
        barItems.add(new GYBarItem("首页", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("视频", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("资讯", R.mipmap.ic_launcher));
        barItems.add(new GYBarItem("我的", R.mipmap.ic_launcher));
    }

    @Override
    public void onSelected(int position) {
        Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
    }
}

