package com.cortex.dane.masymenos.nivel3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.utils.Utils;

public class Globo extends LinearLayout {

	private ImageView globo;
	private TextView operador;
	
	public Globo(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View petalo = inflater.inflate(R.layout.globo, this, true);
	    operador = (TextView)petalo.findViewById(R.id.operador_nivel3);
	    globo = (ImageView)petalo.findViewById(R.id.globo);
	}
	
	public void acomodarValor()
	{
       	Utils.acomodarView(globo, operador, null, 0.21f);
	}
	
	public void setearValor(String valor)
	{
		operador.setText(valor);
	}
	
	public void setearImagen(int src)
	{
		globo.setImageResource(src);
	}
}
	