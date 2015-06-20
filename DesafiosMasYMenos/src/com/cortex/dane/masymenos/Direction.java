package com.cortex.dane.masymenos;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.widget.LinearLayout;

public abstract class Direction {
	
	private int duration = 1000;
	protected int displayWidth;
	
	public Direction(int displayWidth) {
		setDisplayWidth(displayWidth);
	}
	
	public void changePanelSrc(LinearLayout gsPanel, int src) {
		gsPanel.setBackgroundResource(src);
	}
	
	protected void movete(LinearLayout gsPanel, ArrayList<Animator> ass, int iMov, int fMov) {

		Animator mov = ObjectAnimator.ofFloat(gsPanel,"translationX",iMov, fMov);
		mov.setDuration(duration);
		
		ass.add(mov);
	}
	
	public void abrite(LinearLayout gsPanel, ArrayList<Animator> ass) {}
	
	public void cerrate(LinearLayout gsPanel, ArrayList<Animator> ass) {}
		
	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}
	
}
