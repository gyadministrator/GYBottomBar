# GYBottomBar
方便的底部栏,已经迁移到AndroidX

废话不多说，先上图！！！！！：

![image](https://github.com/gyadministrator/GYBottomBar/blob/master/images/20191010092358.png)
![image](https://github.com/gyadministrator/GYBottomBar/blob/master/images/20191010092419.png)

怎样使用?

工程的Gradle引入方式：

    repositories {
            google()
            jcenter()
            mavenCentral()
        }

    allprojects {
        repositories {
            google()
            jcenter()
            maven { url 'https://jitpack.io' }
            mavenCentral()
        }
    }

  dependencies {
		implementation 'com.github.gyadministrator:GYBottomBar:1.2'
	}
  
  Maven引入方式:

第一步，添加到build文件中


  <repositories>

	<repository>

	    <id>jitpack.io</id>

	    <url>https://jitpack.io</url>

    </repository>

  </repositories>



第二步，添加依赖


  <dependency>

	  <groupId>com.github.gyadministrator</groupId>

	  <artifactId>GYBottomBar</artifactId>

	  <version>1.2</version>

  </dependency>


在activity使用，非常简单。
  
在xml中添加这个view。
  
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

 ![image](https://github.com/gyadministrator/GYBottomBar/blob/master/images/20191010084610.png)

 activity中使用
  
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


 bottomView.setPositionBadge(3, -1);
 num设置为小于0的时候，显示小圆点


