package com.cortex.dane.masymenos.nivel4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.utils.Utils;

public class Rectangulo extends Cuadrado {
	
	protected TextView decena;

	public Rectangulo(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
	
	@Override
	protected void inicializarViews(Context context)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.rectangulo, this, true);
	    operando = (TextView)view.findViewById(R.id.nombre_operando);
	    unidad = (TextView)view.findViewById(R.id.tv_operador_unidad);
	    decena = (TextView)view.findViewById(R.id.tv_operador_decena);
	    operando.setTextColor(Color.parseColor("#008080"));
	    layout = (RelativeLayout)view.findViewById(R.id.rl);
	    
	    inicializarRectangulo();
	}
	
	@Override
	public void inicializarRectangulo()
	{
		Utils.setInvisible(unidad, 1, 0);
		Utils.setInvisible(decena, 1, 0);
		Utils.setVisible(operando, 1, 0);
		operando.setTextColor(Color.parseColor("#008080"));
	}
	
	@Override 
	public void llenarRectangulo(Opcion opcion)
	{
		int numero = Integer.parseInt(opcion.getOpcionTexto().getText().toString());
		Integer unidadCorrecta = (int) numero % 10;
		unidad.setText(unidadCorrecta.toString());
		Utils.setVisible(unidad, 0, 500);
		Integer decenaCorrecta = (int) numero/10;
		if(decenaCorrecta > 0)
		{
			decena.setText(decenaCorrecta.toString());
			Utils.setVisible(decena, 0, 500);
		}
		
		unidad.setTextColor(Color.parseColor(opcion.getHexaColor()));
		decena.setTextColor(Color.parseColor(opcion.getHexaColor()));
	}
	
	@Override
	public void drag(Activity a) {
		if(operando.getTag() != null && !operando.getTag().equals(Utils.INVISIBLE)){
			Animation animation1 = AnimationUtils.loadAnimation(a,R.anim.nivel4_rectangulo_rotation);
			layout.startAnimation(animation1);
		}
	}
	
}
