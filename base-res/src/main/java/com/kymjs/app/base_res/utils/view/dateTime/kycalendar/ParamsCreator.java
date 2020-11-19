package com.kymjs.app.base_res.utils.view.dateTime.kycalendar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ParamsCreator {
	private Context context;
    private int screenWidth;//屏幕宽度
    private int screenHeight;//屏幕高度
    private int densityDpi;//像素密度
    public ParamsCreator(Context context){
    	this.context = context;
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	screenWidth = wm.getDefaultDisplay().getWidth();
    	screenHeight = wm.getDefaultDisplay().getHeight();
    	DisplayMetrics metric = new DisplayMetrics();
    	wm.getDefaultDisplay().getMetrics(metric);
    	densityDpi = metric.densityDpi;
    }
    
    /**
	 * 获得width
	 */
	public int getWidth(){
    	if(screenWidth >= 1400){//1440
    		return 900;
    	}
    	if(screenWidth >= 1000){//1080
    		if(densityDpi >=480)
        		return 700;
        	if(densityDpi >= 320)
        		return 700;
        	return 700;
    	}
    	if(screenWidth >= 700){//720
        	if(densityDpi >= 320)
        		return 460;
        	if(densityDpi >= 240)
        		return 460;
        	if(densityDpi >= 160)
        		return 460;
        	return 460;
    	}
    	if(screenWidth >= 500){//540
        	if(densityDpi >= 320)
        		return 400;
        	if(densityDpi >= 240)
        		return 400;
        	if(densityDpi >= 160)
        		return 400;
        	return 400;
    	}
    	return 300;
	}
	/**
	 * 获得height
	 */
	public int getHeight(){    	
    	if(screenWidth >= 1400){//1440
    		return 880;
    	}
    	if(screenWidth >= 1000){//1080
    		if(densityDpi >=480)
        		return 680;
        	if(densityDpi >= 320)
        		return 680;
        	return 680;
    	}
    	if(screenWidth >= 700){//720
        	if(densityDpi >= 320)
        		return 450;
        	if(densityDpi >= 240)
        		return 450;
        	if(densityDpi >= 160)
        		return 450;
        	return 450;
    	}
    	if(screenWidth >= 500){//540
        	if(densityDpi >= 320)
        		return 380;
        	if(densityDpi >= 240)
        		return 380;
        	if(densityDpi >= 160)
        		return 380;
        	return 380;
    	}
    	return 280;
	}
    /**
     * 获得border
     */
    public int getBorder(){
    	if(this.screenWidth >= 1400){//1440
    		return 4;
    	}
    	if(this.screenWidth >= 1000){//1080
    		if(this.densityDpi >=480)
        		return 3;
        	if(this.densityDpi >= 320)
        		return 3;
        	return 2;
    	}
    	if(this.screenWidth >= 700){//720
        	if(this.densityDpi >= 320)
        		return 2;
        	if(this.densityDpi >= 240)
        		return 2;
        	if(this.densityDpi >= 160)
        		return 2;
        	return 2;
    	}
    	if(this.screenWidth >= 500){//540
        	if(this.densityDpi >= 320)
        		return 3;
        	if(this.densityDpi >= 240)
        		return 2;
        	if(this.densityDpi >= 160)
        		return 2;
        	return 1;
    	}
    	if(this.screenWidth >= 400){//480
        	if(this.densityDpi >= 320)
        		return 2;
        	if(this.densityDpi >= 240)
        		return 1;
        	return 1;
    	}
    	return 1;
    }
    /**
     * 获得字体大小
     */
    public int getTextSize(){
    	if(this.screenWidth >= 1400){//1440
    		return 45;
    	}
    	if(this.screenWidth >= 1000){//1080
    		if(this.densityDpi >=480)
        		return 42;
        	if(this.densityDpi >= 320)
        		return 40;
        	return 40;
    	}
    	if(this.screenWidth >= 700){//720
        	if(this.densityDpi >= 320)
        		return 30;
        	if(this.densityDpi >= 240)
        		return 28;
        	if(this.densityDpi >= 160)
        		return 25;
        	return 25;
    	}
    	if(this.screenWidth >= 500){//540
        	if(this.densityDpi >= 320)
        		return 25;
        	if(this.densityDpi >= 240)
        		return 25;
        	if(this.densityDpi >= 160)
        		return 25;
        	return 25;
    	}
    	if(this.screenWidth >= 400){//480
        	if(this.densityDpi >= 320)
        		return 2;
        	if(this.densityDpi >= 240)
        		return 18;
        	return 17;
    	}
    	return 15;
    }
    /**
     * 获得星期字体大小
     */
    public int getWeekTextSize(){
    	if(this.screenWidth >= 1400){//1440
    		return 40;
    	}
    	if(this.screenWidth >= 1000){//1080
    		if(this.densityDpi >=480)
        		return 38;
        	if(this.densityDpi >= 320)
        		return 36;
        	return 36;
    	}
    	if(this.screenWidth >= 700){//720
        	if(this.densityDpi >= 320)
        		return 25;
        	if(this.densityDpi >= 240)
        		return 24;
        	if(this.densityDpi >= 160)
        		return 20;
        	return 20;
    	}
    	if(this.screenWidth >= 500){//540
        	if(this.densityDpi >= 320)
        		return 20;
        	if(this.densityDpi >= 240)
        		return 20;
        	if(this.densityDpi >= 160)
        		return 20;
        	return 20;
    	}
    	if(this.screenWidth >= 400){//480
        	if(this.densityDpi >= 320)
        		return 15;
        	if(this.densityDpi >= 240)
        		return 15;
        	return 13;
    	}
    	return 15;
    }
}
