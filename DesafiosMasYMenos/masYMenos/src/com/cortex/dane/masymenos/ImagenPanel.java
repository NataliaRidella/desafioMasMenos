package com.cortex.dane.masymenos;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImagenPanel implements IconoPanel{

	private int img;
	private String imageTag = "imagen";
	private String textTag = "textoImagen";
	private String texto;
	
	public ImagenPanel(int p_img, String p_texto) {
		img = p_img;
		texto = p_texto;
	}
	
	public void visibilizate(LinearLayout gsPanel) {
		ImageView iv = (ImageView)gsPanel.findViewWithTag(imageTag);
		TextView tv = (TextView)gsPanel.findViewWithTag(textTag);
		iv.setImageResource(img);
		tv.setText(texto);
	}
	
}
