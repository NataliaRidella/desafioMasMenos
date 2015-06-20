package com.cortex.dane.masymenos;

import java.util.ArrayList;

import android.animation.Animator;
import android.widget.LinearLayout;

public class Panel {

	private int fondoSrc;
	private GameSelection gaySelection;
	private Direction direction;
	private LinearLayout gsPanel;
	private IconoPanel icono;
	
	public Panel(LinearLayout p_gsPanel, int p_fondoSrc, IconoPanel p_icono, GameSelection p_gaySelection, Direction p_direction) {
		gsPanel = p_gsPanel;
		setFondoSrc(p_fondoSrc);
		gaySelection = p_gaySelection;
		direction = p_direction;
		icono = p_icono;
	}
	
	public void youHaveBeenTouched(ArrayList<Animator> ass) {
		this.abrite(ass);
	}
	
	public void visibilizate() {
		icono.visibilizate(gsPanel);
		direction.changePanelSrc(gsPanel,fondoSrc);
	}
	
	public void abrite(ArrayList<Animator> ass) {
		direction.abrite(gsPanel,ass);
	}
	
	public void cerrate(ArrayList<Animator> ass) {
		direction.cerrate(gsPanel,ass);
	}
	

	public GameSelection getGaySelection() {
		return gaySelection;
	}

	public void setGaySelection(GameSelection gaySelection) {
		this.gaySelection = gaySelection;
	}

	public int getFondoSrc() {
		return fondoSrc;
	}

	public void setFondoSrc(int fondoSrc) {
		this.fondoSrc = fondoSrc;
	}

	public LinearLayout getGsPanel() {
		return gsPanel;
	}

	public void setGsPanel(LinearLayout gsPanel) {
		this.gsPanel = gsPanel;
	}

	public IconoPanel getIcono() {
		return icono;
	}

	public void setIcono(IconoPanel icono) {
		this.icono = icono;
	}
}
