package com.cortex.dane.masymenos.nivel4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.utils.Utils;

public class Cuadrado extends LinearLayout  {

	private Resultado valorCorrecto;
	protected TextView operando;
	protected TextView unidad;
	private ImageView imagen;
	protected View view;
	protected RelativeLayout layout;

	public Cuadrado(Context context, AttributeSet attrs) {
		super(context,attrs);
		inicializarViews(context);
	}
	
	protected void inicializarViews(Context context)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.cuadrado, this, true);
	    operando = (TextView)view.findViewById(R.id.nombre_operando);
	    unidad = (TextView)view.findViewById(R.id.tv_operador_unidad);
	    imagen = (ImageView)view.findViewById(R.id.imagen_operando);
	    layout = (RelativeLayout)view.findViewById(R.id.rl);
	    
	    inicializarRectangulo();
	}
	
	public void inicializarRectangulo()
	{
		Utils.setInvisible(unidad, 1, 0);
		Utils.setInvisible(imagen, 1, 0);
		Utils.setVisible(operando, 1, 0);
		operando.setTextColor(Color.parseColor("#008080"));
	}
	
	public void llenarRectangulo(Opcion opcion)
	{
		
		if(getValorCorrecto().esCorrecto("+"))
		{
			imagen.setImageResource(R.drawable.nivel4_mas_s);
		}
		else
		{
			imagen.setImageResource(R.drawable.nivel4_menos_s);
		}
		
		Utils.setVisible(imagen, 0, 500);
	}
	
	public void exhibite(String color)
	{
		if(operando.getTag() != null && !operando.getTag().equals(Utils.INVISIBLE))
		{
			operando.setTextColor(Color.parseColor(color));
		}
	}
	
	public void drag(Activity a) {
		if(operando.getTag() != null && !operando.getTag().equals(Utils.INVISIBLE)){
			Animation animation1 = AnimationUtils.loadAnimation(a,R.anim.nivel4_cuadrado_rotation);
			layout.startAnimation(animation1);
		}
	}
	
	public void rellenar(Opcion opcion)
	{
		Utils.setInvisible(operando, 1, 500);
		this.llenarRectangulo(opcion);
		layout.clearAnimation();
	}
	
	public void tapate()
	{
		if(operando.getTag() != null && !operando.getTag().equals(Utils.INVISIBLE))
		{
			operando.setTextColor(Color.parseColor("#008080"));
//			layout.clearAnimation();
		}
	}
	
	public void endDrag() 
	{
		if(operando.getTag() != null && !operando.getTag().equals(Utils.INVISIBLE))
		{
			operando.setTextColor(Color.parseColor("#008080"));
			layout.clearAnimation();
//			Utils.fade(operando,1, 0.5f, 150);
		}
		
	}
	
	public TextView getOperando() {
		return operando;
	}

	public void setOperando(TextView operando) {
		this.operando = operando;
	}

	public Resultado getValorCorrecto() {
		return valorCorrecto;
	}

	public void setValorCorrecto(Resultado valorCorrecto) {
		this.valorCorrecto = valorCorrecto;
	}

}
