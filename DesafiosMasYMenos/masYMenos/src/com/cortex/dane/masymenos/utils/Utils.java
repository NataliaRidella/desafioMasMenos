package com.cortex.dane.masymenos.utils;

import java.util.concurrent.atomic.AtomicInteger;

import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class Utils {
	
	public static final int INVISIBLE = 1;
	public static final int VISIBLE = 0;
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	
	public static void fade(View v, float startFade, float endFade, long duration) {
		Animation fade = new AlphaAnimation(startFade, endFade);
		fade.setInterpolator(new AccelerateInterpolator());
		fade.setStartOffset(0);
		fade.setDuration(duration);
		fade.setFillAfter(true);
				
		v.setAnimation(fade);
		v.startAnimation(fade);
	}
	
	public static void setInvisible(View v, float start, long duration)
	{
		fade(v, start, 0f, duration);
		v.setTag(INVISIBLE);
	}
	
	public static void setVisible(View v, float start, long duration)
	{
		fade(v, start, 1f, duration);
		v.setTag(VISIBLE);
	}
	
	public static void setFaded(View v, float start, long duration)
	{
		fade(v, start, 0.4f, duration);
		v.setTag(VISIBLE);
	}
	
    public static void acomodarView(View container, View view, Float offsetX, Float offsetY) {
    	
    	int widthContainer = container.getWidth();
        int heightContainer = container.getHeight();
        
        int widthView = view.getWidth();
        int heightView = view.getHeight();
        
        float centroXView = widthView/2;
        float centroYView = heightView/2;
        
        float puntoXContainer = offsetX == null ? centroXView : widthContainer * offsetX;
        float puntoYContainer = offsetY == null ? centroYView : heightContainer * offsetY;
        
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
        
        int top = (int)(puntoYContainer - centroYView); 
        int left = (int)(puntoXContainer - centroXView); 
        
        params.setMargins(left, top, 0, 0); 
        view.setLayoutParams(params);
    }
    
    public static void zoomOUT(View v, int duration){
    	
    	ScaleAnimation zoom = new ScaleAnimation(1, 0, 1, 0,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	zoom.setDuration(duration);  
    	zoom.setFillAfter( true );
    	v.setAnimation(zoom);
    	v.startAnimation(zoom);
    }
    
    public static void zoomIN(View v, int duration){
    	
    	ScaleAnimation zoom = new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    	zoom.setDuration(duration);  
    	zoom.setFillAfter( true );
    	v.setAnimation(zoom);
    	v.startAnimation(zoom);
    }
    
    public static void resetGame(View v, int delay){
    	final View view = v;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
	            	view.invalidate();
	            }
	        }, delay);
    }
    
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
