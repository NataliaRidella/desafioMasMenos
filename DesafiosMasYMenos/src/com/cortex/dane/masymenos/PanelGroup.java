package com.cortex.dane.masymenos;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorSet;

public class PanelGroup {

	private Panel panelLeft;
	private Panel panelRight;
	private boolean cerrado = true;
	
	public PanelGroup(Panel p_panelLeft, Panel p_panelRight){
		panelLeft = p_panelLeft;
		panelRight = p_panelRight;
	}
	
	public void leftTouch() {
		AnimatorSet as = new AnimatorSet();
		ArrayList<Animator> ass = new ArrayList<Animator>();
		
		panelLeft.youHaveBeenTouched(ass);
		panelRight.abrite(ass); 
		
		setCerrado(false);
		as.playTogether(ass);
		as.start();
	}

	public void rightTouch() {
		AnimatorSet as = new AnimatorSet();
		ArrayList<Animator> ass = new ArrayList<Animator>();
		
		panelRight.youHaveBeenTouched(ass);
		panelLeft.abrite(ass);
		
		setCerrado(false);
		as.playTogether(ass);
		as.start();
	}
	
	public void visibilizate() {
		panelLeft.visibilizate();
		panelRight.visibilizate();
	}
	
	public void cerrate() {
		AnimatorSet as = new AnimatorSet();
		ArrayList<Animator> ass = new ArrayList<Animator>();
		
		panelLeft.cerrate(ass);
		panelRight.cerrate(ass);
		
		setCerrado(true);
		as.playTogether(ass);
		as.start();
	}
	
	public Panel getPanelLeft() {
		return panelLeft;
	}

	public void setPanelLeft(Panel panelLeft) {
		this.panelLeft = panelLeft;
	}

	public Panel getPanelRight() {
		return panelRight;
	}

	public void setPanelRight(Panel panelRight) {
		this.panelRight = panelRight;
	}

	public boolean isCerrado() {
		return cerrado;
	}

	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}
}
