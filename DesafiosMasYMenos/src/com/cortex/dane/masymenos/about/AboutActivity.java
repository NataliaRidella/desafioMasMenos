package com.cortex.dane.masymenos.about;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.cortex.dane.masymenos.R;

public class AboutActivity extends Activity {
	
	ImageView buttonPrev, buttonNext;
	ViewFlipper viewflipper;

	Animation slide_in_left, slide_out_right, slide_out_left, slide_in_right;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        //Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
   		
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	
		buttonPrev = (ImageView) findViewById(R.id.prev);
		buttonNext = (ImageView) findViewById(R.id.next);
		viewflipper = (ViewFlipper) findViewById(R.id.viewflipper);
		
		slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
		slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
		slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
		slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");
		((TextView)findViewById(R.id.objetivo)).setTypeface(font);
		((TextView)findViewById(R.id.consideracionesPadres)).setTypeface(font);
		((TextView)findViewById(R.id.consideracionesDocentes)).setTypeface(font);
		((TextView)findViewById(R.id.creditos)).setTypeface(font);
		
		font = Typeface.createFromAsset(getAssets(), "avenir.ttf");
		((TextView)findViewById(R.id.texto1)).setTypeface(font);
		((TextView)findViewById(R.id.texto2)).setTypeface(font);
		((TextView)findViewById(R.id.texto3)).setTypeface(font);
		((TextView)findViewById(R.id.texto4)).setTypeface(font);
		
		buttonNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				viewflipper.setInAnimation(slide_in_right);
				viewflipper.setOutAnimation(slide_out_left);
				viewflipper.showNext();
			}
		});
		buttonPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				viewflipper.setInAnimation(slide_in_left);
				viewflipper.setOutAnimation(slide_out_right);
				viewflipper.showPrevious();
			}
		});        
    }    
}
