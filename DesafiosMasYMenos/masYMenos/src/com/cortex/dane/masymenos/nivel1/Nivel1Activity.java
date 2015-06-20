package com.cortex.dane.masymenos.nivel1;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.Nivel;
import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.commons.EnumTipoNivel;
import com.cortex.dane.masymenos.commons.SonidoFelicitacion;
import com.cortex.dane.masymenos.commons.TipoNivel;
import com.cortex.dane.masymenos.sqlitereporte.AlumnoDataSource;
import com.cortex.dane.masymenos.utils.DaMagicNumber;
import com.cortex.dane.masymenos.utils.Fecha;
import com.cortex.dane.masymenos.utils.Utils;

public class Nivel1Activity extends Activity implements Nivel,OnTouchListener {
	
	private TextView textView1;
	private TextView signo;
	
	private Contenedor contenedor1;
	private Contenedor contenedor2;
	private Contenedor contenedor3;
	
	private int valor;
	private Lienzo lienzo;
	
	private boolean respuestaCorrecta = false;
	
	//test
	private ImageView arbol1;
	private ImageView arbol2;
	private ImageView arbol3;
	private ImageView fruit;
	private ImageView masomenos;
	private ImageView imgFestejo;
	private ImageView imgCorrecto;

	private RelativeLayout rlJuego;
	private RelativeLayout rlFestejo;
	
	private AnimationDrawable animFestejo;
	
	//SQLite
	private AlumnoDataSource datasource;
	private int puntaje=0, incorrectos=0;	
	
	private TipoNivel tipoNivel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	   	      
        
    	//Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel1);
        arbol1 = (ImageView) findViewById(R.id.arbol1);
        arbol2 = (ImageView) findViewById(R.id.arbol2);
        arbol3 = (ImageView) findViewById(R.id.arbol3);
        
        masomenos = (ImageView) findViewById(R.id.masomenos);
        
        Intent intent = getIntent();
        EnumTipoNivel tNivel = (EnumTipoNivel) intent.getSerializableExtra("TipoNivel");
        tipoNivel = TipoNivel.getTipoNivel(tNivel);
        
        //Inicio la base de datos
    	datasource = new AlumnoDataSource(this);
        datasource.open();
        
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Setea el singleton para poder ser accedido de otras clases
        Recursos.setResources(this.getResources());
        
        //Cargar los valores seteados en el xml
        Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");
        fruit=(ImageView)findViewById(R.id.fruta);
    	textView1=(TextView)findViewById(R.id.consigna);
    	signo=(TextView)findViewById(R.id.signo);
    	textView1.setTypeface(font);
    	signo.setTypeface(font);
    	
        //Cargar el layout, dejarlo esperando el evento touch
        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.nivel1_rl);
        lienzo = new Lienzo(this);
        lienzo.setOnTouchListener(this);
        layout1.addView(lienzo);
        
        rlFestejo = (RelativeLayout)findViewById(R.id.rl_nivel1_festejo);
		rlJuego = (RelativeLayout)findViewById(R.id.rl_nivel1_juego);
		imgCorrecto = (ImageView)findViewById(R.id.nivel1_correcto);
        
    	imgFestejo = (ImageView)findViewById(R.id.nivel1_nena_festejo);
	  	imgFestejo.setImageResource(R.anim.anim_festejo_nena_1);
	  	animFestejo = (AnimationDrawable)imgFestejo.getDrawable();
    	Utils.setInvisible(rlFestejo, 1, 0);
    	textView1.setVisibility(View.INVISIBLE);
    	signo.setVisibility(View.INVISIBLE);
    	fruit.setVisibility(View.INVISIBLE);
    	
    }
    
    @Override
	public boolean onTouch(View v, MotionEvent event) {
    	
    	float posX = event.getAxisValue(MotionEvent.AXIS_X);
    	float posY = event.getAxisValue(MotionEvent.AXIS_Y);

    	Contenedor contenedor = getTouchedCircleQuantity(posX, posY);
    	
    	if(contenedor != null)
    	{
	    	switch (event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    		{
					contenedor.getImageView().setImageResource(R.drawable.nivel1_arbol_selected);
	    			return true;
	    		}
	    		case MotionEvent.ACTION_UP:
	    		{
	    			contenedor1.getImageView().setImageResource(R.drawable.nivel1_arbol);
	    			contenedor2.getImageView().setImageResource(R.drawable.nivel1_arbol);
	    			contenedor3.getImageView().setImageResource(R.drawable.nivel1_arbol);
	    			
	    			
	    			if(valor == contenedor.getCantidad() && !contenedor.isSeleccionadoAnteriormente() )
	    	    	{
	    				if(event.getAction() == MotionEvent.ACTION_UP)
	    				{
	    						contenedor.setSeleccionadoAnteriormente(true);
	    						respuestaCorrecta = true;
	    		            	lienzo.invalidate();
	    				}
	    	    	}
	    			else
	    			{
	    				incorrectos++;
	    			}
	    			return true;
	    		}
	    	}
    	}
    	return true;
    }
    
    public void reset() {
    	Utils.resetGame(lienzo, 0);
    }
    
    public Point screenSize() {
    	 Display display = this.getWindowManager().getDefaultDisplay();
    	 Point size = new Point();
    	 display.getSize(size);
    	 return size;
    }
    
    
	private Contenedor getTouchedCircleQuantity(float posX, float posY) {		
		
		Contenedor contenedor= null ;
		
		if(contenedor1 != null && contenedor1.inCircle(posX, posY))
			contenedor = contenedor1;
		else if(contenedor2 != null && contenedor2.inCircle(posX, posY))
			contenedor = contenedor2;
		else if(contenedor3 != null && contenedor3.inCircle(posX, posY))
			contenedor = contenedor3;
		
		return contenedor;
	}
	
	@Override
	protected void onDestroy() {
		if (puntaje>0 || incorrectos>0){
			long fecha = new Fecha().fechaEnMilisegundos();
			datasource.createAlumno(fecha, 1, puntaje, incorrectos);
		}
		super.onDestroy();
	 }

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	class Lienzo extends View {

		 Canvas canvas;
		 Nivel1Activity nivel1Activity;
		 
	        public Lienzo(Nivel1Activity p_nivel1) {
	            super(p_nivel1);
	            nivel1Activity = p_nivel1;
	        }
	        
	        protected void onDraw(Canvas canvas) {
	        	
        		//Si el usuario respondio correctamente se dibuja un cartel felicitandolo por un cierto tiempo 
        		//Luego se redibuja la pantalla para cargar un nuevo ejercicio
	        	if(respuestaCorrecta) 
	        	{
	        		puntaje++;
	        		Utils.setVisible(rlFestejo, 0, 500);
	        	  	Utils.setInvisible(rlJuego, 1, 500);
	        	  	System.gc();
	        	  	animFestejo.stop();
	        	  	animFestejo.start();
	        	  	
					Utils.zoomIN(imgCorrecto, 2000);
	        		respuestaCorrecta = false;
	        		
	        		SonidoFelicitacion.felicitameConDelay(nivel1Activity, 1500);
	        		Utils.resetGame(this, 3000);

	        	}
	        	else
	        	{        		
		            this.canvas = canvas; 
		            
		            Integer[] cants = DaMagicNumber.getDifferentRandomInt(1, 9, 3);
		            
		            // Obtiene la próxima fruta
		            Fruta fruta  = Recursos.proximaFruta();
		            
		        	//Dibujar los 3 contenedores
		            contenedor1 = new Contenedor(canvas,cants[0].intValue(),fruta, arbol1,nivel1Activity);
		            contenedor2 = new Contenedor(canvas,cants[1].intValue(),fruta, arbol2,nivel1Activity);
		            contenedor3 = new Contenedor(canvas,cants[2].intValue(),fruta, arbol3,nivel1Activity);
		            
		            Arrays.sort(cants);
		            
		            if(tipoNivel.definirOperacion())
		            {
						textView1.setText("\u00BF D\u00F3nde hay M\u00C1S " );
						valor = cants[2];
						masomenos.setImageResource(R.drawable.nivel1_mas);
		            }
					else 
					{
						textView1.setText("\u00BF D\u00F3nde hay MENOS " );
						valor = cants[0];
						masomenos.setImageResource(R.drawable.nivel1_menos);
					}
		            
		            fruit.setImageResource(fruta.getImgSrc());

		          	Utils.setInvisible(rlFestejo, 1, 0);
		          	Utils.setVisible(rlJuego, 0, 0);
		        	textView1.setVisibility(View.VISIBLE);
		        	signo.setVisibility(View.VISIBLE);
		        	fruit.setVisibility(View.VISIBLE);
	        		
    				contenedor1.generate();
    				contenedor2.generate();
    				contenedor3.generate();
	        	}

	        }
	    }

}
