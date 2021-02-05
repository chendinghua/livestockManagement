package com.kymjs.app.base_res.utils.view.kyindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class KyIndicator  extends HorizontalScrollView {
	private List<View> tabs = new ArrayList<>();
	private OnSelectedListener onSelectedListener;
	private boolean barVisibility = true;//是否显示滚动条
	private int barHeight = dip2px(this.getContext(), 3);//滚动条高度
	private int barColor = 0xFF1283EC;//滚动条颜色
	private int selectedIndex = 0;//当前选中的tab
	
	public KyIndicator(Context context) {
		super(context);
		this.setHorizontalScrollBarEnabled(false);
		this.setWillNotDraw(false);
	}
	public KyIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setHorizontalScrollBarEnabled(false);
		this.setWillNotDraw(false);
	}
	@Override
	public void onDraw(Canvas canvas) {
        System.out.print("kyIndicator:onDraw");
		super.onDraw(canvas);
		if(!barVisibility)
			return ;
		if(tabs.size() == 0)
			return ;
        View tab = tabs.get(this.selectedIndex);
        //计算tab的left
        int left = 0;
        LinearLayout root = (LinearLayout)this.getChildAt(0);
		for(int i = 0; i<this.selectedIndex; i++){
			View child = root.getChildAt(i);
			left += child.getWidth();
		}
		Paint p = new Paint();
		p.setAntiAlias(true);
		//画移动条背景
		p.setColor(0xFFD4D4D4); 
	    canvas.drawRect(0, this.getHeight()-barHeight/2, getTotalWidth(), this.getHeight(), p);
		//画移动条
		p.setColor(barColor); 
		RectF oval = new RectF(left, this.getHeight()-barHeight, left + tab.getWidth(), this.getHeight());
		canvas.drawRoundRect(oval, 1, 1, p);
	}
	/**
	 * 添加wrapper
	 */
	public void addTab(View tab){
		if(tabs == null)
			tabs = new ArrayList<View>();
		LinearLayout root = (LinearLayout)this.getChildAt(0);
		if(root == null){
			root = new LinearLayout(this.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			root.setLayoutParams(lp);
			root.setGravity(Gravity.CENTER);
			this.addView(root);
		}
		tabs.add(tab);
		root.addView(tab);
		final int position = tabs.size()-1;
		tab.setClickable(true);
		tab.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				setCurrentTab(position);
			}					
		});
	}
	public void clearTab(){
		if(tabs != null)
		tabs.clear();
		removeAllViews();
	}

	/**
	 * 设置当前tab
	 */
	public void setCurrentTab(int position){
		if(tabs.size() == 0)
			return ;
		LinearLayout root = (LinearLayout)this.getChildAt(0);
		int totalWidth = 0;
		for(int i = 0; i<position; i++){
			View child = root.getChildAt(i);
			totalWidth += child.getWidth();
		}
		int moveSize = totalWidth - (this.getWidth()/2 - root.getChildAt(position).getWidth()/2);		
		smoothScrollTo(moveSize, 0);
		if(selectedIndex == position)
			return ;
		selectedIndex = position;				
		if(onSelectedListener != null){
			for(int i = 0; i<tabs.size(); i++){
				if(i == position)
					continue;
				onSelectedListener.OnNoSelected(tabs.get(i), i);
			}
			onSelectedListener.OnSelected(tabs.get(position), position);
		}
	}
	/**
	 * 计算总宽度
	 */
	private int getTotalWidth(){
		int totalWidth = 0;
		LinearLayout root = (LinearLayout)this.getChildAt(0);
		for(int i = 0; i<tabs.size(); i++){
			View child = root.getChildAt(i);
			totalWidth += child.getWidth();
		}
		return totalWidth;
	}
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
	public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
		this.onSelectedListener = onSelectedListener;
	}
	public boolean isBarVisibility() {
		return barVisibility;
	}
	public void setBarVisibility(boolean barVisibility) {
		this.barVisibility = barVisibility;
	}
	public int getBarHeight() {
		return barHeight;
	}
	public void setBarHeight(int barHeight) {
		this.barHeight = barHeight;
	}
	public int getSelectedIndex() {
		return selectedIndex;
	}
	public int getBarColor() {
		return barColor;
	}
	public void setBarColor(int barColor) {
		this.barColor = barColor;
	}
}
