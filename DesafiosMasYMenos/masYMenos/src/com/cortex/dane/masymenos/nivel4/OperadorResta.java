package com.cortex.dane.masymenos.nivel4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.utils.Utils;

public class OperadorResta extends Opcion{
	
	public OperadorResta(Context context) {
		super(context);
	}

	public OperadorResta(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void nuevoEjercicio(String pValor, View parent, Imagen imagen, RelativeLayout juego) {
    	
   		Utils.fade(super.opcionTexto, 1, 0, 10);
   		super.imagen.setImageResource(R.drawable.nivel4_menos);
   		super.hexaColor = "#e1cf1a";
   		super.opcionTexto.setText(pValor);
   		super.juego = juego;
    	
    	this.acomodate(parent);
    	
    	super.escalarImagen();
    	
		Utils.fade(this, 0, 1, 500);

    	seleccionadoAnteriormente = false;
    }
	      

}
