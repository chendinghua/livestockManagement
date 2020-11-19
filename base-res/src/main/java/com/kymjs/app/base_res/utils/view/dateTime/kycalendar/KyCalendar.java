package com.kymjs.app.base_res.utils.view.dateTime.kycalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KyCalendar  extends View implements OnGestureListener{
	private ParamsCreator paramsCreator = new ParamsCreator(this.getContext());
    private int selfWidth;
    private int selfHeight;
    //结构
    private int monthLayouHeight;//
    private int weekLayoutHeight;
    private int dateLayoutHeight;//
    private int bottomLayoutHeight;
    
    private Date currentTime = new Date();
    private int currentYear = currentTime.getYear()+1900;
    private int currentMonth = currentTime.getMonth()+1;   
    
    //事件
    private OnSelectedListener onSelectedListener;
    private GestureDetector mGestureDetector;	
    private String format = "yyyy/MM/dd";//日期格式
    
	private Paint paint = new Paint();
    private List<InnerWrapper> wrappers = new ArrayList<InnerWrapper>();    
    
	public KyCalendar(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(this);
	}
	public KyCalendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(this);
	}
	public KyCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mGestureDetector = new GestureDetector(this);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		this.selfWidth = this.paramsCreator.getWidth();
		this.selfHeight = this.paramsCreator.getHeight(); 
		
		int border = paramsCreator.getBorder();
		int height = this.selfHeight - border * 5;
		double per = height/20.0;
		this.monthLayouHeight = (int)(per * 3);
		this.weekLayoutHeight = (int)(per * 2);
		this.bottomLayoutHeight = (int)(per * 3);
		this.dateLayoutHeight = height - this.monthLayouHeight - this.weekLayoutHeight - this.bottomLayoutHeight;
		int tdDateHeight = (this.dateLayoutHeight - border*5)/6;
		this.dateLayoutHeight = tdDateHeight * 6 + border*5;
		this.selfHeight = this.monthLayouHeight+this.weekLayoutHeight+this.dateLayoutHeight+this.bottomLayoutHeight+border * 5;
		
		int pers = (this.selfWidth - border * 8)/7;
		this.selfWidth = border*8 + pers*7;
		setMeasuredDimension(this.selfWidth, this.selfHeight);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xFFCFD1D2);
		if(wrappers.size() == 0)
		    createLayout();		
		drawLayout(canvas);
	}
	/**
	 * 画
	 */
	private void drawLayout(Canvas canvas){
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
		for(InnerWrapper wrapper : wrappers){
			RectF rectF = new RectF(wrapper.left, wrapper.top, wrapper.right , wrapper.bottom);
			paint.setColor(wrapper.currentBgColor);
			paint.setTextSize(wrapper.textSize);
			canvas.drawRect(rectF, paint);		
			int txtWidth = (int)this.paint.measureText(wrapper.showText);
			paint.setColor(wrapper.currentTextColor);
			canvas.drawText(wrapper.showText, wrapper.left + ((wrapper.right-wrapper.left)/2-txtWidth/2), wrapper.top+((wrapper.bottom-wrapper.top)-txtHeight)/2-fontMetrics.ascent, paint);	
		}
	}
	/**
	 * 创建布局
	 */
	private void createLayout(){
		wrappers.clear();
		createMonthLayout();
		createWeekLayout();
		createDateLayout();
		createBottomLayout();
	}
	/**
	 * 初始化month区域
	 */
	private void createMonthLayout(){
		int textSize = paramsCreator.getTextSize();
		paint.setAntiAlias(true);		
		paint.setTextSize(textSize);
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
		int border = paramsCreator.getBorder();
		int width = this.selfWidth - border*4;
		int per = width / 10;
		//画上一月				
		int lastMonthWidth = per * 3;		
		int top = border;
		int left = border;		
		int right = left + lastMonthWidth;
		int bottom = top + this.monthLayouHeight;
		InnerWrapper wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFA3D6E8);
		wrapper.showText = "上月";
		wrappers.add(wrapper);		
		wrapper.onClickListener = new OnClickListener(){
			@Override
			public void onClick() {
				turnToLastMonth();
			}			
		};
		
		//画当前月
		left = right;
		left += border;
		int currentMonthWidth = width -per * 6;
		right = left + currentMonthWidth;
		wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		String format = this.format.substring(0, this.format.indexOf("MM")+2);
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		date.setYear(this.currentYear - 1900);
		date.setMonth(this.currentMonth - 1);
		wrapper.showText = dateFormat.format(date);
		
		//画下一月
		left = right;
		left += border;
		int nextMonthWidth = per * 3;
		right = this.selfWidth - border;
		wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFA3D6E8);
		wrappers.add(wrapper);
		wrapper.showText = "下月";
		wrapper.onClickListener = new OnClickListener(){
			@Override
			public void onClick() {
				turnToNextMonth();
			}			
		};
	}
	/**
	 * 初始化week区域
	 */
	private void createWeekLayout(){
		int textSize = paramsCreator.getTextSize();
		int weekTextSize = paramsCreator.getWeekTextSize();
		paint.setAntiAlias(true);		
		paint.setTextSize(textSize);
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
		int border = paramsCreator.getBorder();
		int per = (this.selfWidth - border * 8) / 7;//每个星期间也存在border，只不过不显示
		//周一
		int left = border;
		int top = border * 2 + this.monthLayouHeight;
		int right = left + per;
		int bottom = top + this.weekLayoutHeight;
		InnerWrapper wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Mon";
		
		//周二
		left = right + border;
		right = left + per;
		wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Tues";
		//周三
		left = right + border;
		right = left + per;
		wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Wed";	
		//周四
		left = right + border;
		right = left + per;
		wrapper = new InnerWrapper(left, top, right, bottom,  0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Thur";	
		//周五
		left = right + border;
		right = left + per;
		wrapper = new InnerWrapper(left, top, right, bottom,  0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Fri";	
		//周六
		left = right + border;
		right = left + per;
		wrapper = new InnerWrapper(left, top, right, bottom,  0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Sat";	
		//周日
		left = right + border;
		right = this.selfWidth - border;
		wrapper = new InnerWrapper(left, top, right, bottom,  0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
		wrappers.add(wrapper);
		wrapper.textSize = weekTextSize;
		wrapper.showText = "Sun";			
		
	}
	/**
	 * 初始化date区域
	 */
	private void createDateLayout(){
		try {
			int textSize = paramsCreator.getTextSize();
			paint.setAntiAlias(true);		
			paint.setTextSize(textSize);
			FontMetricsInt fontMetrics = paint.getFontMetricsInt();
			int txtHeight = fontMetrics.bottom - fontMetrics.ascent;
			int border = paramsCreator.getBorder();
			
			int days = TimeUtil.getDays(this.currentYear, this.currentMonth);
			int perWidth = (this.selfWidth - border * 8)/7;
			int perHeight = (this.dateLayoutHeight - border * 5)/6; 
			int startTop = border * 3 + this.monthLayouHeight + this.weekLayoutHeight;
			List<InnerWrapper> daysWrappers = new ArrayList<InnerWrapper>();
			int index = 0;
			for(int i = 1; i<=6; i++){
				for(int h = 1; h<=7; h++){
					int left = h*border + (h-1)*perWidth; 
					int right = left + perWidth;
                    if(h == 7){
						left = border*7 + perWidth*6;
						right = this.selfWidth - border;
					}
					int top = startTop + (i-1)*border + (i-1)*perHeight;					
					int bottom = top + perHeight;
					
					InnerWrapper wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFA3D6E8, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFF0F0F0);
					wrapper.area = "date";
					index++;
					daysWrappers.add(wrapper);
					wrappers.add(wrapper);
				}
			}
			int week = TimeUtil.getWeek(this.currentYear, this.currentMonth, 1);
			index = 0;
			DateFormat format = new SimpleDateFormat(this.format);
			String currentDate = format.format(currentTime);
			Date currentTime = format.parse(currentDate);
			for(int i = week-1; i<daysWrappers.size()&&index<days; i++){
				InnerWrapper wrapper = daysWrappers.get(i);
				wrapper.value = index+1;
				wrapper.showText = wrapper.value+"";
				int txtWidth = (int)this.paint.measureText(wrapper.value+"");
				int rat = TimeUtil.compare(this.currentYear, this.currentMonth, wrapper.value, currentTime);
				
				if(rat==-1){
				    paint.setColor(0xFFF8F8F8);
				    wrapper.commonBgColor = 0xFFF8F8F8;
				    wrapper.currentBgColor = 0xFFF8F8F8;
				}
				if(rat == 0){
					paint.setColor(0xFFF8F8F8);
					wrapper.commonBgColor = 0xFFF8F8F8;
					wrapper.currentBgColor = 0xFFF8F8F8;
				}
				if(rat == 1){
					paint.setColor(0xFFF0F0F0);
					wrapper.commonBgColor = 0xFFF0F0F0;
					wrapper.currentBgColor = 0xFFF0F0F0;
				}
				
				if(rat==-1){
				    paint.setColor(0xFF025CAD);
				    wrapper.commonTextColor = 0xFF025CAD;
				    wrapper.currentTextColor = 0xFF025CAD;
				}
				if(rat == 0){
					paint.setColor(0xFFF0831E);
					wrapper.commonTextColor = 0xFFF0831E;
					wrapper.currentTextColor = 0xFFF0831E;
				}
				if(rat == 1){
					paint.setColor(0xFFCFD1D2);
					wrapper.commonTextColor = 0xFFCFD1D2;
					wrapper.currentTextColor = 0xFFCFD1D2;
				}
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化bottom区域
	 */
	private void createBottomLayout(){
		try {
			int textSize = paramsCreator.getTextSize();
			paint.setAntiAlias(true);		
			paint.setTextSize(textSize);
			FontMetricsInt fontMetrics = paint.getFontMetricsInt();
			int border = paramsCreator.getBorder();
			int tdWidth = (this.selfWidth - border*2)/2;
			int top = this.selfHeight - border - this.bottomLayoutHeight;
			int left = border;		
			int right = left + tdWidth;
			int bottom = this.selfHeight - border;
			InnerWrapper wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFA3D6E8);
			wrapper.showText = "清空";
			wrapper.area = "clear";
		//	wrappers.add(wrapper);
			wrapper.onClickListener = new OnClickListener(){
				@Override
				public void onClick() {
					
				}			
			};
			left = border * 2 + tdWidth;		
			right = this.selfWidth - border;
			wrapper = new InnerWrapper(left, top, right, bottom, 0xFF7B7B7B, 0xFF7B7B7B, 0xFF7B7B7B, 0xFFF0F0F0, 0xFFF0F0F0, 0xFFA3D6E8);
			wrapper.showText = "今日";
			wrapper.area = "today";
			wrappers.add(wrapper);		
			wrapper.onClickListener = new OnClickListener(){
				@Override
				public void onClick() {
					
				}			
			};
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 转到下一月
	 */
	private void turnToNextMonth(){
		this.currentMonth++;
		if(this.currentMonth == 13){
			this.currentYear++;
			this.currentMonth = 1;					
		}
		createLayout();	
		this.invalidate();
	}
	/**
	 * 转到上一月
	 */
	private void turnToLastMonth(){
		this.currentMonth--;
		if(this.currentMonth == 0){
			this.currentYear--;
			this.currentMonth = 12;					
		}
		createLayout();	
		this.invalidate();
	}
	/**
	 * 设置初始值
	 */
	public void setText(String date){
		try {
			if(date == null || date.trim().equals(""))
				return ;
			DateFormat format = new SimpleDateFormat(this.format);
			Date time = format.parse(date);
			setText(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置初始值
	 */
	public void setText(Date date){
		if(date == null)
			return ;
		this.currentYear = date.getYear() + 1900;
		this.currentMonth = date.getMonth() + 1;
	}
	/**
	 * 触碰事件
	 */
	@Override
    public boolean onTouchEvent(MotionEvent event) {    	
    	try {
    		mGestureDetector.onTouchEvent(event);
    		if(event.getAction() == MotionEvent.ACTION_UP){
    			this.upHandler();
    			this.invalidate();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return true;
    }
	///手势监听
	@Override
	public boolean onDown(MotionEvent event) {
		downHandler((int)event.getX(), (int)event.getY());
		return true;
	}
	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float arg2, float arg3) {
		FlingHandler((int)event1.getX(), (int)event2.getX());
		return true;
	}
	@Override
	public void onLongPress(MotionEvent event) {		
	}
	@Override
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float arg2, float arg3) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent event) {
	}
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		clickHandler((int)event.getX(), (int)event.getY());
		return true;
	}
	/**
	 * 手指按下事件
	 */
	private InnerWrapper downWrapper;
	private void downHandler(int x, int y){
		downWrapper = null;
		for(InnerWrapper wrapper : wrappers){
			if(x>=wrapper.left && x <= wrapper.right){
				if(y>=wrapper.top && y<=wrapper.bottom){
					downWrapper = wrapper;
					wrapper.currentBgColor = wrapper.downBgColor;
					wrapper.currentTextColor = wrapper.downTextColor;
					this.invalidate();
					break;
				}
			}
		}
	}
	/**
	 * 单击事件
	 */
	private void clickHandler(int x, int y){
		DateFormat format = new SimpleDateFormat(this.format);
        //执行本身click
		if(downWrapper != null && downWrapper.onClickListener != null){
			downWrapper.onClickListener.onClick();
		}
		if(downWrapper != null && onSelectedListener != null && downWrapper.area != null && downWrapper.area.equals("date")){//触发了日期部分
			if(downWrapper.value == 0)
				return ;
			Date date = new Date();
			date.setYear(this.currentYear-1900);
			date.setMonth(this.currentMonth-1);
			date.setDate(downWrapper.value);
			onSelectedListener.onSelected(format.format(date));
			this.invalidate();
			return ;
		}
		if(downWrapper != null && onSelectedListener != null && downWrapper.area != null && downWrapper.area.equals("clear")){//触发了clear部分
			onSelectedListener.onSelected("");
			this.invalidate();
			return ;
		}
		if(downWrapper != null && onSelectedListener != null && downWrapper.area != null && downWrapper.area.equals("today")){//触发了today部分
			Date current = new Date();
			this.currentYear = current.getYear() + 1900;
			this.currentMonth = current.getMonth() + 1;
			onSelectedListener.onSelected(format.format(current));
			createLayout();	
			this.invalidate();
			return ;
		}
	}
	/**
	 * Fling事件处理
	 * @param x1 起始点x坐标
	 * @param x2 终点x坐标
	 */
	private void FlingHandler(int x1, int x2){
		if(x1 == x2)
			return ;
		if(x1 > x2){//向左滑动
			turnToNextMonth();
			return ;
		}
		turnToLastMonth();
	}
	/**
	 * 手指抬起事件
	 */
	private void upHandler(){
		for(InnerWrapper wrapper : wrappers){
			wrapper.currentBgColor = wrapper.commonBgColor;
			wrapper.currentTextColor = wrapper.commonTextColor;
		}
	}
	/**
	 * 点击事件接口
	 */
	private interface OnClickListener{
		public void onClick();
	}
	/**
	 * 内部wrapper
	 */
	private class InnerWrapper{
		OnClickListener onClickListener;
		int value = 0;//用于记录日期
		String showText = "";//控件上画的文字
		String area = "";//用于标识哪个区域
		int textSize = paramsCreator.getTextSize();;
		int commonTextColor;//正常下文字颜色
		int downTextColor;//手指按下文字颜色
		int currentTextColor;//当前文字颜色
		int commonBgColor;//正常下背景
		int downBgColor;//手机按下背景
		int currentBgColor;//当前背景
		int left;
		int top;
		int right;
		int bottom;
		public InnerWrapper(int left, int top, int right, int bottom, int currentTextColor, int commonTextColor, int downTextColor, int currentBgColor, int commonBgColor, int downBgColor){
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
			this.currentTextColor = currentTextColor;
			this.commonTextColor = commonTextColor;
			this.downTextColor = downTextColor;
			this.commonBgColor = commonBgColor;
			this.downBgColor = downBgColor;			
			this.currentBgColor = currentBgColor;
		}
	}
	
	

	public void setFormat(String format) {
		this.format = format;
	}
	public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
		this.onSelectedListener = onSelectedListener;
	}
}
