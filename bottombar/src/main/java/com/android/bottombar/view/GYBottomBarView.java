package com.android.bottombar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;;

import com.android.bottombar.R;
import com.android.bottombar.model.GYBarItem;

import java.util.ArrayList;
import java.util.List;

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

    public void setBarChangeListener(IGYBottomBarChangeListener barChangeListener) {
        this.barChangeListener = barChangeListener;
    }

    public GYBottomBarView(Context context) throws Exception {
        this(context, null);
    }

    public GYBottomBarView(Context context, AttributeSet attrs) throws Exception {
        this(context, attrs, 0);
    }

    public GYBottomBarView(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setBarItems(List<GYBarItem> barItems) throws Exception {
        this.barItems = barItems;
        init(this.getContext());
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context) throws Exception {
        if (barItems.size() != 0 && barItems.size() < barNumMin) {
            throw new Exception("底部栏菜单个数太少");
        }
        if (barItems.size() != 0 && barItems.size() > barNumMax) {
            throw new Exception("底部栏菜单个数太多");
        }
        for (int i = 0; i < barItems.size(); i++) {
            GYBarItem barItem = barItems.get(i);
            TextView textView = new TextView(context);
            textView.setText(barItem.getTitle());
            Drawable drawable = getResources().getDrawable(barItem.getIcon());
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            textView.setCompoundDrawablePadding(10);
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER);
            barViews.add(textView);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            this.addView(textView, i, params);
            this.setBackgroundResource(R.color.bottomColor);
            this.setPadding(6, 6, 6, 6);
            final int position = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    barChangeListener.onSelected(position);
                }
            });
        }
    }


    public interface IGYBottomBarChangeListener {
        void onSelected(int position);
    }
}
