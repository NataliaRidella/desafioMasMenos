package com.cortex.dane.masymenos;

import java.util.ArrayList;

import android.animation.Animator;
import android.widget.LinearLayout;


public class Panelception extends Panel {

	private PanelGroup panelGroup;
	
	public Panelception(LinearLayout p_gsPanel, int p_fondoSrc, IconoPanel p_icono , GameSelection p_gaySelection, Direction p_direction, PanelGroup p_panelGroup) {
		
		super(p_gsPanel, p_fondoSrc, p_icono, p_gaySelection, p_direction);
		setPanelGroup(p_panelGroup);
	}

	public void youHaveBeenTouched(ArrayList<Animator> ass) {
		panelGroup.visibilizate(); 
		super.youHaveBeenTouched(ass);
		super.getGaySelection().setPreviousPanelGroup(super.getGaySelection().getPanelGroup());
		super.getGaySelection().setPanelGroup(panelGroup);
	}

	public PanelGroup getPanelGroup() {
		return panelGroup;
	}

	public void setPanelGroup(PanelGroup panelGroup) {
		this.panelGroup = panelGroup;
	}
}
