package com.cortex.dane.masymenos.nivel1;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.Nivel;
import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.commons.EnumTipoNivel;
import com.cortex.dane.masymenos.commons.TipoNivel;
import com.cortex.dane.masymenos.sqlitereporte.AlumnoDataSource;
import com.cortex.dane.masymenos.utils.DaMagicNumber;
import com.cortex.dane.masymenos.utils.Fecha;
import com.cortex.dane.masymenos.utils.Utils;

public class Nivel1HardActivity extends Activity  implements Nivel{
	
	
	private TextView textView1;
	
	private Contenedor contenedor1;
	private Contenedor contenedor2;
	private Contenedor contenedor3;
	
	private int valor;
	private Lienzo lienzo;
	
	private boolean respuestaCorrecta = true;
	
	//test
	private ImageView arbol1;
	private ImageView arbol2;
	private ImageView arbol3;
	private ImageView fruit;
	private ImageView masomenos;
	private TextView puntaje;
	private TextView puntos;
	private ProgressBar progressBar;
	private CountDownTimer countDown; 
	
	private RelativeLayout cartelDePuntos;
	private LinearLayout arboles;
	
	//SQLite
	private AlumnoDataSource datasource;
	private int correctosHistoricos=0;
	private int correctos =0;
	private int incorrectos=0;
	private int speed = 10000;
	private int timer = 100;
	
	private TipoNivel tipoNivel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	   	      
        
    	//Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel1_hard);
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
    	puntaje=(TextView)findViewById(R.id.puntaje);
    	puntos=(TextView)findViewById(R.id.puntos);
		progressBar=(ProgressBar)findViewById(R.id.progressBar1);

		progressBar.setIndeterminate(false);
    	
    	puntaje.setTypeface(font);
    	textView1.setTypeface(font);
    	
        //Cargar el layout, dejarlo esperando el evento touch
        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.nivel1_rl);
        arboles = (LinearLayout) findViewById(R.id.nivel1_ll);
        lienzo = new Lienzo(this);
        lienzo.setOnTouchListener(new LienzoListener());
        layout1.addView(lienzo);
        
        cartelDePuntos = (RelativeLayout)findViewById(R.id.cartelDePuntos);
    }
    
	private Contenedor getTouchedCircleQuantity(float posX, float posY) {		
		
		Contenedor contenedor= null ;
		
		if(contenedor1.inCircle(posX, posY))
			contenedor = contenedor1;
		else if(contenedor2.inCircle(posX, posY))
			contenedor = contenedor2;
		else if(contenedor3.inCircle(posX, posY))
			contenedor = contenedor3;
		
		return contenedor;
	}
	
	public CountDownTimer newCountDownTimer(long speed) {
//		mProgressBar.setProgress(i);
		long vuelta = speed / 100;
	   	CountDownTimer ct = new CountDownTimer(speed,vuelta) {
	
	        @Override
	        public void onTick(long millisUntilFinished) {
	            timer--;
	            progressBar.setProgress(timer);
	
	        }
	
	        @Override
	        public void onFinish() {
				respuestaCorrecta = false;
				lienzo.invalidate();
	        	timer--;
	            progressBar.setProgress(timer);
	        }
	    };
	   	
	   	return ct;
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
	
	@Override
	protected void onDestroy() {
		if (correctosHistoricos>0 || incorrectos>0){
			long fecha = new Fecha().fechaEnMilisegundos();
			datasource.createAlumno(fecha, 1, correctosHistoricos, incorrectos);
		}
		super.onDestroy();
	 }

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	class Lienzo extends View {

		 Canvas canvas;
		 Nivel nivel1HardActivity;
		 
	        public Lienzo(Nivel1HardActivity p_nivel1) {
	            super(p_nivel1);
	            nivel1HardActivity = p_nivel1;
	        }
	        
	        
	        @SuppressLint("DrawAllocation")
			protected void onDraw(Canvas canvas) {
	        	
        		//Si el usuario respondio correctamente se dibuja un cartel felicitandolo por un cierto tiempo 
        		//Luego se redibuja la pantalla para cargar un nuevo ejercicio
	        	if(!respuestaCorrecta) 
	        	{
					arboles.setVisibility(View.INVISIBLE);
					progressBar.setVisibility(View.INVISIBLE);
					puntaje.setText("Puntaje: " + Integer.toString(correctos));
	        		cartelDePuntos.setVisibility(View.VISIBLE);
	        	}
	        	else
	        	{        		
		            this.canvas = canvas; 
		            
		            Integer[] cants = DaMagicNumber.getDifferentRandomInt(1, 9, 3);
		            
		            // Obtiene la próxima fruta
		            Fruta fruta  = Recursos.proximaFruta();
		            
		        	//Dibujar los 3 contenedores
		            contenedor1 = new Contenedor(canvas,cants[0].intValue(),fruta, arbol1,nivel1HardActivity);
		            contenedor2 = new Contenedor(canvas,cants[1].intValue(),fruta, arbol2,nivel1HardActivity);
		            contenedor3 = new Contenedor(canvas,cants[2].intValue(),fruta, arbol3,nivel1HardActivity);
		            
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
					
					cartelDePuntos.setVisibility(View.INVISIBLE);
					arboles.setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.VISIBLE);

					
					contenedor1.generate();
					contenedor2.generate();
					contenedor3.generate();
					
					if(speed > 1500)
						speed -= 500;
					
					countDown = newCountDownTimer(speed);
					countDown.start();
	        	}

	        }
	    }
	
	private class LienzoListener implements OnTouchListener {
		
	    @Override
		public boolean onTouch(View v, MotionEvent event) {
	    	
	    	float posX = event.getAxisValue(MotionEvent.AXIS_X);
	    	float posY = event.getAxisValue(MotionEvent.AXIS_Y);

	    	Contenedor contenedor = getTouchedCircleQuantity(posX, posY);
	    	
	    	switch (event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    		{
	    			if(contenedor != null)
	    			{
	    				contenedor.getImageView().setImageResource(R.drawable.nivel1_arbol_selected);
	    			}
	    			return true;
	    		}
	    		case MotionEvent.ACTION_UP:
	    		{
	    			contenedor1.getImageView().setImageResource(R.drawable.nivel1_arbol);
	    			contenedor2.getImageView().setImageResource(R.drawable.nivel1_arbol);
	    			contenedor3.getImageView().setImageResource(R.drawable.nivel1_arbol);
	    			
	    			if(contenedor != null && valor != contenedor.getCantidad() && respuestaCorrecta)
	    	    	{
	    				//Respuesta Incorrecta
						respuestaCorrecta = false;
						timer = 100;
						countDown.cancel();
						lienzo.invalidate();
	    	    	}
	    			else if(contenedor != null && valor == contenedor.getCantidad() && !contenedor.isSeleccionadoAnteriormente() && respuestaCorrecta )
	    			{
	    				//Respuesta Correcta
//		    					SonidoFelicitacion.Felicitame(this);
	    				contenedor.setSeleccionadoAnteriormente(true);
	    				correctos++;
	    				correctosHistoricos++;
	    				puntos.setText(Integer.toString(correctos));
	    				timer = 100;
	    				countDown.cancel();
	    				lienzo.invalidate();
	    				
	    			} 
	    			else if(!respuestaCorrecta ) 
	    			{
	    				//Salir del cartel de Puntaje
	    				respuestaCorrecta = true;
	    				correctos = 0;
	    				incorrectos++;
	    				puntos.setText(Integer.toString(correctos));
	    				timer = 100;
	    				speed = 10000;
	    				lienzo.invalidate();
	    			}
	    			return true;
	    		}
	    	}
	    	return true;
	    }
		
	}
}
