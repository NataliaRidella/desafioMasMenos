package com.cortex.dane.masymenos;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextoPanel implements IconoPanel {

	private String tag = "texto";
	private String texto;
	private String color;
	
	public TextoPanel(String p_texto, String p_color){
		texto = p_texto;
		color = p_color;
	}
	
	public void visibilizate(LinearLayout gsPanel) {
		TextView tv = (TextView)gsPanel.findViewWithTag(tag);
		tv.setText(texto);
		tv.setTextColor(Color.parseColor(color));
	}
}
