package com.cortex.dane.masymenos;

import java.util.ArrayList;

import android.animation.Animator;
import android.widget.LinearLayout;


public class Right extends Direction {

	public Right(int displayWidth) {
		super(displayWidth);
	}
	
	public void abrite(LinearLayout gsPanel, ArrayList<Animator> ass) {
		movete(gsPanel, ass, 0, displayWidth/2);
	}
	
	public void cerrate(LinearLayout gsPanel, ArrayList<Animator> ass) {
		movete(gsPanel, ass, displayWidth/2, 0);
	}
	
}
