package com.cortex.dane.masymenos.nivel3;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;

public class ResultadoNivel3 {
	
	private TextView texto;
	private ImageView imagen;
	private Integer valor;
	private Nivel3Activity activity;
	private OperacionNivel3 operacion;
	
	public ResultadoNivel3(Nivel3Activity r_activity, ImageView r_img, TextView r_texto){
		activity = r_activity;
		texto = r_texto;
		imagen = r_img;
        this.setearListener();
	}
	
	public void setearValor(Integer res, OperacionNivel3 op)
	{
		operacion = op;
		valor = res;
		texto.setText(res.toString());
	}
	
	public void setearListener()
	{
		imagen.setOnTouchListener(new View.OnTouchListener() {
		
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			
				if(activity.getRespuestaCorrecta())
				{
					return true;
				}
				
				switch (event.getAction())
		    	{
		    		case MotionEvent.ACTION_DOWN:
		    		{
		    			imagen.setImageResource(R.drawable.nivel3_nube_selected);
		    			break;
		    		} 
		    		case MotionEvent.ACTION_UP:
		    		{
		    			imagen.setImageResource(R.drawable.nivel3_nube);
		    			
						if(valor == operacion.getResultadoCorrecto())
						{
							activity.setRespuestaCorrecta(true);
							activity.resultadoCorrectoSeleccionado();
						}else
						{
							activity.incorrectos++;
						}
		    			
		    			break;
		    		}
		    	}
		    			
				return true;

			}
		}); 
	}

}
