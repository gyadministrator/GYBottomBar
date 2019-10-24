package com.android.bottombar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bottombar.R;
import com.android.bottombar.model.GYBarItem;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

;

/**
 * Description: GYBottomBar
 * Created by gy(1984629668@qq.com)
 * Created Time on 2019/6/12 14:45
 */
public class GYBottomBarView extends LinearLayout {
    private int barNumMin = 3;//底部栏菜单个数最小值
    private int barNumMax = 5;//底部栏菜单个数最大值
    private List<TextView> barViews = new ArrayList<>();//底部菜单布局
    private List<GYBarItem> barItems = new ArrayList<>();//底部菜单项
    private IGYBottomBarChangeListener barChangeListener;
    private Context mContext;
    private int container;//存放fragment的容器
    private List<Fragment> fragments = new ArrayList<>();//存放fragment的集合
    private FragmentManager fragmentManager;
    private int normalColor;
    private int selectColor;
    private List<Integer> icons = new ArrayList<>();
    private List<QBadgeView> qBadgeViews = new ArrayList<>();

    public void setBarChangeListener(IGYBottomBarChangeListener barChangeListener) {
        this.barChangeListener = barChangeListener;
    }

    public GYBottomBarView(Context context) {
        this(context, null);
    }

    public GYBottomBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GYBottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.GYBottomBarView);
        normalColor = typedArray.getColor(R.styleable.GYBottomBarView_normalTextColor, Color.BLACK);
        selectColor = typedArray.getColor(R.styleable.GYBottomBarView_selectTextColor, Color.RED);
        typedArray.recycle();
        init(context);
    }

    public void setBarItems(List<GYBarItem> barItems) {
        this.barItems = barItems;
        init(this.getContext());
    }

    public void updateFragment(int position) {
        if (position>fragments.size()-1){
            try {
                throw new Exception("position大于fragments的数量");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        switchFragment(position);
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context) {
        mContext = context;
        if (barItems.size() != 0 && barItems.size() < barNumMin) {
            try {
                throw new Exception("底部栏菜单个数太少");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (barItems.size() != 0 && barItems.size() > barNumMax) {
            try {
                throw new Exception("底部栏菜单个数太多");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        initQBView();
        for (int i = 0; i < barItems.size(); i++) {
            GYBarItem barItem = barItems.get(i);
            TextView textView = new TextView(context);
            textView.setText(barItem.getTitle());
            Drawable drawable = getResources().getDrawable(barItem.getIcon());
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            textView.setCompoundDrawablePadding(10);
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(normalColor);
            barViews.add(textView);

            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            this.addView(textView, i, params);
            this.setBackgroundResource(R.color.bottomColor);
            this.setPadding(6, 6, 6, 6);
            final int position = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    QBadgeView badgeView = qBadgeViews.get(position);
                    badgeView.hide(true);
                    setColor(position);
                    setIcon(position);
                    switchFragment(position);
                    barChangeListener.onSelected(position);
                }
            });
        }
    }

    private void initQBView() {
        for (int i = 0; i < barItems.size(); i++) {
            qBadgeViews.add(new QBadgeView(mContext));
        }
    }

    public void setSelectIcon(List<Integer> icons) {
        if (icons.size() != barViews.size()) {
            try {
                throw new Exception("icon个数和菜单个数不一致");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.icons = icons;
    }

    private void setIcon(int position) {
        for (int i = 0; i < barViews.size(); i++) {
            GYBarItem barItem = barItems.get(i);
            TextView textView = barViews.get(i);
            if (i == position) {
                Drawable drawable = getResources().getDrawable(icons.get(i));
                textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                textView.setCompoundDrawablePadding(10);
            } else {
                Drawable drawable = getResources().getDrawable(barItem.getIcon());
                textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                textView.setCompoundDrawablePadding(10);
            }
        }
    }

    private void setColor(int position) {
        for (int i = 0; i < barViews.size(); i++) {
            if (i == position) {
                barViews.get(i).setTextColor(selectColor);
            } else {
                barViews.get(i).setTextColor(normalColor);
            }
        }
    }

    /**
     * 切换fragment
     *
     * @param position 位置
     */
    private void switchFragment(int position) {
        if (fragments != null) {
            Fragment fragment = fragments.get(position);
            fragmentManager.executePendingTransactions();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if (fragmentList.contains(fragment)) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.replace(container, fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }


    public interface IGYBottomBarChangeListener {
        void onSelected(int position);
    }

    /**
     * 设置底部栏某一个的角标
     *
     * @param position 位置
     * @param num      数量
     */
    public void setPositionBadge(int position, int num) {
        if (position < 0) {
            try {
                throw new Exception("参数不合法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TextView textView = barViews.get(position);
        QBadgeView qBadgeView = qBadgeViews.get(position);
        if (textView != null) {
            qBadgeView.bindTarget(textView);
            qBadgeView.setBadgeNumber(num);
            qBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            if (num < 0) {
                qBadgeView.setGravityOffset(45, 1, true);
            } else if (num > 99) {
                qBadgeView.setGravityOffset(25, -3, true);
            } else {
                qBadgeView.setGravityOffset(35, -3, true);
            }
            qBadgeView.setExactMode(false);
        }
    }


    public void setFragments(FragmentManager fragmentManager, List<Fragment> fragments, int container) {
        if (fragments == null || fragments.size() == 0 || container == 0) {
            try {
                throw new Exception("参数不合法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.fragments = fragments;
        this.container = container;
        this.fragmentManager = fragmentManager;

        switchFragment(0);
        setColor(0);
        setIcon(0);
    }
}
