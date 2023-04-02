package com.android.bottombar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.bottombar.R;
import com.android.bottombar.model.GYBarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: GYBottomBar
 * Created by gy(1984629668@qq.com)
 * Created Time on 2019/6/12 14:45
 */
public class GYBottomBarView extends LinearLayout implements View.OnClickListener {
    private final static int barNumMin = 3;//底部栏菜单个数最小值
    private final static int barNumMax = 6;//底部栏菜单个数最大值
    private final List<View> barViews = new ArrayList<>();//底部菜单布局
    private List<GYBarItem> barItems = new ArrayList<>();//底部菜单项
    private IGYBottomBarChangeListener barChangeListener;
    private Context mContext;
    private int container;//存放fragment的容器
    private List<Fragment> fragments = new ArrayList<>();//存放fragment的集合
    private FragmentManager fragmentManager;
    private final int normalColor;
    private final int selectColor;
    private List<Integer> icons = new ArrayList<>();
    private final List<BadgeView> qBadgeViews = new ArrayList<>();
    private boolean fixed = false;
    private boolean isPageRepeatLoading = false;

    private Fragment currentFragment;

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
        fixed = typedArray.getBoolean(R.styleable.GYBottomBarView_fixed, false);
        isPageRepeatLoading = typedArray.getBoolean(R.styleable.GYBottomBarView_isPageRepeatLoading, false);
        typedArray.recycle();
        init(context);
    }

    public void setBarItems(List<GYBarItem> barItems) {
        this.barItems = barItems;
        init(this.getContext());
    }

    public View getBottomViewPositionImageView(int position) {
        if (barViews != null && barViews.size() > 0) {
            if (position > barViews.size() - 1) {
                try {
                    throw new Exception("position大于barViews的数量");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return barViews.get(position).findViewById(R.id.iv_icon);
        }
        return null;
    }

    public View getBottomViewPositionTextView(int position) {
        if (barViews != null && barViews.size() > 0) {
            if (position > barViews.size() - 1) {
                try {
                    throw new Exception("position大于barViews的数量");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return barViews.get(position).findViewById(R.id.tv_txt);
        }
        return null;
    }

    public void updateFragment(int position) {
        if (position > fragments.size() - 1) {
            try {
                throw new Exception("position大于fragments的数量");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isPageRepeatLoading) {
            switchFragment(position);
        } else {
            switchFragment(fragments.get(position));
        }
    }

    @SuppressLint({"ResourceAsColor", "InflateParams"})
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
            View viewItem;
            if (fixed) {
                viewItem = LayoutInflater.from(context).inflate(R.layout.bottom_fixed_item, null);
            } else {
                viewItem = LayoutInflater.from(context).inflate(R.layout.bottom_item, null);
            }
            TextView tvTxt = viewItem.findViewById(R.id.tv_txt);
            tvTxt.setText(barItem.getTitle());
            tvTxt.setGravity(Gravity.CENTER);
            tvTxt.setTextColor(normalColor);
            ImageView ivIcon = viewItem.findViewById(R.id.iv_icon);
            ivIcon.setImageResource(barItem.getIcon());
            barViews.add(viewItem);

            final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
            this.addView(viewItem, i, params);
            this.setPadding(6, 6, 6, 6);
            viewItem.setTag(i);
            viewItem.setOnClickListener(this);
        }
    }


    private void initQBView() {
        for (int i = 0; i < barItems.size(); i++) {
            qBadgeViews.add(new BadgeView(mContext));
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
            View view = barViews.get(i);
            ImageView ivIcon = view.findViewById(R.id.iv_icon);
            if (i == position) {
                ivIcon.setImageResource(icons.get(i));
            } else {
                ivIcon.setImageResource(barItem.getIcon());
            }
        }
    }

    private void setColor(int position) {
        for (int i = 0; i < barViews.size(); i++) {
            if (i == position) {
                ((TextView) barViews.get(i).findViewById(R.id.tv_txt)).setTextColor(selectColor);
            } else {
                ((TextView) barViews.get(i).findViewById(R.id.tv_txt)).setTextColor(normalColor);
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

    private void showDefaultFragment() {
        currentFragment = fragments.get(0);
        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void switchFragment(Fragment to) {
        if (to != null) {
            fragmentManager.executePendingTransactions();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {
                fragmentTransaction.hide(currentFragment).add(container, to);
            } else {
                fragmentTransaction.hide(currentFragment).show(to);
            }
            currentFragment = to;
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
    public void setBadge(int position, int num) {
        hideBadge(position);
        if (position < 0) {
            try {
                throw new Exception("参数不合法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        View bottomViewPositionImageView = getBottomViewPositionImageView(position);
        BadgeView badgeView = qBadgeViews.get(position);
        badgeView.setTargetView(bottomViewPositionImageView);
        badgeView.setBadgeCount(num);
        badgeView.setTextSize(7f);
    }

    /**
     * 设置底部栏某一个的角标
     *
     * @param position 位置
     * @param num      数量
     */
    public void setBadgeWithBg(int position, int num, String color) {
        hideBadge(position);
        if (position < 0) {
            try {
                throw new Exception("参数不合法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        View bottomViewPositionImageView = getBottomViewPositionImageView(position);
        BadgeView badgeView = qBadgeViews.get(position);
        badgeView.setTargetView(bottomViewPositionImageView);
        badgeView.setBadgeCount(num);
        badgeView.setTextSize(7f);
        badgeView.setBackground(6, Color.parseColor(color));
    }

    public void hideBadge(int position) {
        if (position < 0) {
            try {
                throw new Exception("参数不合法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        View bottomViewPositionImageView = getBottomViewPositionImageView(position);
        BadgeView badgeView = qBadgeViews.get(position);
        badgeView.setTargetView(bottomViewPositionImageView);
        badgeView.setBadgeCount(0);
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

        if (isPageRepeatLoading) {
            switchFragment(0);
        } else {
            showDefaultFragment();
        }
        setColor(0);
        setIcon(0);
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag != null) {
            int position = Integer.parseInt(tag.toString());
            setColor(position);
            setIcon(position);
            if (isPageRepeatLoading) {
                switchFragment(position);
            } else {
                switchFragment(fragments.get(position));
            }
            barChangeListener.onSelected(position);
        }
    }
}
