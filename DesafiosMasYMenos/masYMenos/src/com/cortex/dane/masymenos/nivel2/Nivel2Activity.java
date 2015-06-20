package com.cortex.dane.masymenos.nivel2;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.commons.EnumTipoNivel;
import com.cortex.dane.masymenos.commons.SonidoFelicitacion;
import com.cortex.dane.masymenos.commons.TipoNivel;
import com.cortex.dane.masymenos.sqlitereporte.AlumnoDataSource;
import com.cortex.dane.masymenos.utils.DaMagicNumber;
import com.cortex.dane.masymenos.utils.Fecha;
import com.cortex.dane.masymenos.utils.Utils;

public class Nivel2Activity extends Activity implements OnTouchListener {
	
	private TextView consigna;
	private TextView resultado;
	private Petalo petalo1;
	private Petalo petalo2;
	private Petalo petalo3;
	private ImageView florPunteada;
	private ImageView florPetaloPunteado;
	MediaPlayer mp;
	private int cantidadPetalosCorrectosSeleccionados = -1;
	private Lienzo lienzo;
	private ImageView petaloDerecha;
	private ImageView petaloIzquierda;
	private TipoNivel tipoNivel;
	
	private RelativeLayout rlJuego;
	private RelativeLayout rlFestejo;
	private AnimationDrawable animFestejo;
	private ImageView imgFestejo;
	private ImageView imgCorrecto;
	
	//SQLite
	private AlumnoDataSource datasource;
	private int puntaje=0;
	private int incorrectos=0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        //Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel2);
        
        Intent intent = getIntent();
        EnumTipoNivel tNivel = (EnumTipoNivel) intent.getSerializableExtra("TipoNivel");
        tipoNivel = TipoNivel.getTipoNivel(tNivel);
         
        //Cargar los valores seteados en el xml
        consigna = (TextView)findViewById(R.id.consigna);
        consigna.setText("\u00BF Cu" + Recursos.a + "les son los p\u00e9talos que faltan ?");	
        resultado = (TextView)findViewById(R.id.resultado);
		petaloDerecha = (ImageView)findViewById(R.id.petalo_derecha);
		petaloIzquierda = (ImageView)findViewById(R.id.petalo_izquierda);
		florPunteada = (ImageView)findViewById(R.id.flor_punteada);
   		florPetaloPunteado = (ImageView)findViewById(R.id.flor_petalo_punteado);
   		
   		//Inicio la base de datos
    	datasource = new AlumnoDataSource(this);
        datasource.open();  
   		
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        //Cargar el layout, dejarlo esperando el evento touch
        RelativeLayout layoutn1p2 = (RelativeLayout) findViewById(R.id.layoutn1p2);
        lienzo = new Lienzo(this);
        layoutn1p2.addView(lienzo);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");
    	consigna.setTypeface(font);
    	
        petalo1 = (Petalo)findViewById(R.id.petalo1);
        petalo2 = (Petalo)findViewById(R.id.petalo2);
        petalo3 = (Petalo)findViewById(R.id.petalo3);
        
        petalo1.setOnTouchListener(new PetaloListener());
        petalo2.setOnTouchListener(new PetaloListener());
        petalo3.setOnTouchListener(new PetaloListener());
        florPetaloPunteado.setOnDragListener(new FlorListener());
        
        //Cargo views para el festejo
        rlFestejo = (RelativeLayout)findViewById(R.id.rl_nivel2_festejo);
		rlJuego = (RelativeLayout)findViewById(R.id.rl_nivel2_juego);
		imgCorrecto = (ImageView)findViewById(R.id.nivel2_correcto);
        
    	imgFestejo = (ImageView)findViewById(R.id.nivel2_nena_festejo);
	  	imgFestejo.setImageResource(R.anim.anim_festejo_nena_1);
	  	animFestejo = (AnimationDrawable)imgFestejo.getDrawable();
    	Utils.setInvisible(rlFestejo, 1, 0);
        
    }
    
    public void petaloCorrectoSeleccionado(Petalo petalo){
    	
    	
    	if(cantidadPetalosCorrectosSeleccionados == 0)
    	{
//    		SonidoFelicitacion.Felicitame(this);
    		florPunteada.setVisibility(View.GONE);
        	Utils.fade(petaloIzquierda, 0.3f, 1, 500);
    		Utils.fade(petalo, 0, 0.3f, 500);

    		cantidadPetalosCorrectosSeleccionados++;
    	}
    	else 
    	{
    		puntaje++;
    		//Cambiar la flor con los petalos faltantes por una con todos los petalos
    		florPetaloPunteado.setVisibility(View.INVISIBLE);
    		Utils.fade(petalo, 0, 0.3f, 500);
    		Utils.fade(petaloDerecha, 0.3f, 1, 500);
    		
    		//Muestro Festejo
    		felicitacion(this);
    	}	
    }
    
    public void felicitacion(final Nivel2Activity activity) {
    	
    	(new Handler()).postDelayed(new Runnable() {
			public void run() {
				//Muestro Festejo
				Utils.setVisible(rlFestejo, 0, 500);
			  	Utils.setInvisible(rlJuego, 1, 500);
			  	System.gc();
			  	animFestejo.stop();
			  	animFestejo.start();
			  	
				Utils.zoomIN(imgCorrecto, 2000);
				
				SonidoFelicitacion.felicitameConDelay(activity, 1500);
				Utils.resetGame(lienzo, 3000); 	
			}
    	}, 500);
    }
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}
	
	@Override
	protected void onDestroy() {
		if (puntaje>0 || incorrectos>0){
			datasource.createAlumno(new Fecha().fechaEnMilisegundos(), 2, puntaje, incorrectos);
		}
		super.onDestroy();
		if (mp != null) {
			mp.release();
			mp = null; 
		}
	 }

	@Override
	protected void onPause() {
		super.onPause();
		if (mp != null) {
			mp.release();
			mp = null; 
		}
	}
	
	class Lienzo extends View {

	        public Lienzo(Context context) {
	            super(context);
	        }
	        
	        @SuppressLint("DrawAllocation")
			protected void onDraw(Canvas canvas) {
	           	
	        	ArrayList<Operacion> operaciones = new ArrayList<Operacion>();
	            cantidadPetalosCorrectosSeleccionados = 0;
	            
	            int res;
	            
	            if(tipoNivel.definirOperacion())
	            {	
	            	// Mas
	            	//Si es suma el resultado puede ser de 3 a 9.
	            	res = DaMagicNumber.randInt(3, 9);
	            	
	            	operaciones.add(new Suma().generarOperacionCorrecta(res, operaciones));
		            operaciones.add(new Suma().generarOperacionCorrecta(res, operaciones));
		            operaciones.add(new Suma().generarOperacionIncorrecta(res, operaciones));
	            }
	            else
	            {
	            	// Menos
	            	//Si es resta el resultado puede ser de 1 a 7.
	            	res = DaMagicNumber.randInt(1, 7);
	            	
	            	operaciones.add(new Resta().generarOperacionCorrecta(res, operaciones));
		            operaciones.add(new Resta().generarOperacionCorrecta(res, operaciones));
		            operaciones.add(new Resta().generarOperacionIncorrecta(res, operaciones));
	            }
	            
	            
	            Collections.shuffle(operaciones);
            	
	            resultado.setText(Integer.toString(res));
	            
	    		florPetaloPunteado.setVisibility(View.VISIBLE);
	    		florPunteada.setVisibility(View.VISIBLE);
	            
	            // No se porque no me funciona 	petaloOculto2.setVisibility(View.INVISIBLE); asique uso esto
	    		Utils.fade(petaloDerecha, 0, 0, 0);
	    		Utils.fade(petaloIzquierda, 0, 0, 0);
	            
            	petalo1.nuevoEjercicio(operaciones.get(0));
            	petalo2.nuevoEjercicio(operaciones.get(1));
            	petalo3.nuevoEjercicio(operaciones.get(2));
	    	    Utils.fade(petalo1, 0.3f, 1, 500);
            	Utils.fade(petalo2, 0.3f, 1, 500);
            	Utils.fade(petalo3, 0.3f, 1, 500);
            	
            	// Alinea el resultado de la consigna con el centro de la flor
            	Utils.acomodarView(florPunteada, resultado, 0.617f, 0.32f);   
            	
            	Utils.setInvisible(rlFestejo, 1, 0);
	          	Utils.setVisible(rlJuego, 0, 500);
	          	rlJuego.setVisibility(View.VISIBLE);
	        }
	    }
	
	private class PetaloListener implements View.OnTouchListener  {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Petalo petalo = (Petalo)v;
			switch (event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    		{
	    			if(rlJuego.getTag().equals(Utils.VISIBLE) && !petalo.isSeleccionadoAnteriormente()) 
	    			{
		    	        ClipData data = ClipData.newPlainText("", "");
		    	        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
		    	        v.startDrag(data, shadowBuilder, v, 0);
		    	        Utils.fade(v, 1, 0, 0);
	    			}
	    			break;
	    		} 	
	    	}
	    		
	    	return false;
		}
	}
	
	private class FlorListener implements View.OnDragListener  {

		@Override
	    public boolean onDrag(View v, DragEvent event) {
			Petalo petalo = (Petalo) event.getLocalState();
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					// do nothing
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					// do nothing
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					// do nothing
					break;
				case DragEvent.ACTION_DROP:
					if(petalo.sosElCorrecto())
						petaloCorrectoSeleccionado(petalo);
					else
					{
						petalo.visibilizate();
						incorrectos++;
					}
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					if(!petalo.isSeleccionadoAnteriormente())
						petalo.visibilizate();
					break;
				default:
					break;
		  }
	      return true;
	    }
	}
}
