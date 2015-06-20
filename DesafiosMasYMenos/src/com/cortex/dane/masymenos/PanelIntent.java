package com.cortex.dane.masymenos;

import java.util.ArrayList;

import android.animation.Animator;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

public class PanelIntent extends Panel {

	private Intent intent;
	private String consigna;
	private int audio;
	private int animacion;
	
	public PanelIntent(LinearLayout p_gsPanel, int p_fondoSrc, IconoPanel p_icono, GameSelection p_gaySelection, Direction p_direction,String p_consigna, Intent p_intent, int p_audio, int p_animacion) {
		super(p_gsPanel, p_fondoSrc, p_icono, p_gaySelection, p_direction);
		intent = p_intent;
		consigna = p_consigna;
		animacion = p_animacion;
		audio = p_audio;
	}
	
	public void youHaveBeenTouched(ArrayList<Animator> ass) {
		super.youHaveBeenTouched(ass);
		final GameSelection gs = super.getGaySelection();
		gs.getConsigna().setText(consigna);
		
		gs.startAnimation(audio,animacion);
		
		gs.getJugar().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent.putExtra("TipoNivel", gs.getOperacion());
				gs.iniciaEjercicio(intent);
			}
		});
	} 
	
	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public String getConsigna() {
		return consigna;
	}

	public void setConsigna(String consigna) {
		this.consigna = consigna;
	}
	
	public int getAudio() {
		return audio;
	}

	public void setAudio(int audio) {
		this.audio = audio;
	}

	public int getAnimacion() {
		return animacion;
	}

	public void setAnimacion(int animacion) {
		this.animacion = animacion;
	}
}
