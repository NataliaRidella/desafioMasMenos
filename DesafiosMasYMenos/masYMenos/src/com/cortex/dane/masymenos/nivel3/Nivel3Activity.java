package com.cortex.dane.masymenos.nivel3;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.commons.EnumTipoNivel;
import com.cortex.dane.masymenos.commons.SonidoFelicitacion;
import com.cortex.dane.masymenos.commons.TipoNivel;
import com.cortex.dane.masymenos.sqlitereporte.AlumnoDataSource;
import com.cortex.dane.masymenos.utils.Fecha;
import com.cortex.dane.masymenos.utils.Utils;

@SuppressLint("ClickableViewAccessibility") public class Nivel3Activity extends Activity {
	
	private TextView consigna;
	private Globo globoIzquierda;
	private Globo globoDerecha;
	private RelativeLayout rl_globos;
	private ImageView signo;
	private ImageView payaso;
	private ResultadoNivel3 resultado1;
	private ResultadoNivel3 resultado2;
	private ResultadoNivel3 resultado3;
	private TipoNivel tipoNivel;
	private Lienzo lienzo;
	private boolean respuestaCorrecta = false;
	
	private RelativeLayout rlJuego;
	private RelativeLayout rlFestejo;
	private AnimationDrawable animFestejo;
	private ImageView imgFestejo;
	private ImageView imgCorrecto;
	
	private Animation animGloboIz;
	private Animation animGloboDer;

	//SQLite
	private AlumnoDataSource datasource;
	private int puntaje=0;
	public int incorrectos=0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel3);
        
        
        Intent intent = getIntent();
        EnumTipoNivel tNivel = (EnumTipoNivel) intent.getSerializableExtra("TipoNivel");
        tipoNivel = TipoNivel.getTipoNivel(tNivel);
        
        
        //Cargar los valores seteados en el xml
        consigna = (TextView)findViewById(R.id.consigna);
        globoIzquierda = (Globo)findViewById(R.id.globo_izquierda);
        globoDerecha = (Globo)findViewById(R.id.globo_derecha);
        signo = (ImageView)findViewById(R.id.signo);
        payaso = (ImageView)findViewById(R.id.payaso);
        rl_globos = (RelativeLayout)findViewById(R.id.rl_globos);
        
		resultado1 = new ResultadoNivel3(this, (ImageView)findViewById(R.id.marco1), (TextView)findViewById(R.id.resultado1));
		resultado2 = new ResultadoNivel3(this, (ImageView)findViewById(R.id.marco2), (TextView)findViewById(R.id.resultado2));
		resultado3 = new ResultadoNivel3(this, (ImageView)findViewById(R.id.marco3), (TextView)findViewById(R.id.resultado3));
		
		//Inicio la base de datos
    	datasource = new AlumnoDataSource(this);
        datasource.open();
        
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");
        consigna.setTypeface(font);
        
        //Cargar el layout, dejarlo esperando el evento touch
        RelativeLayout layoutn1p2 = (RelativeLayout) findViewById(R.id.layoutn1p2);
        lienzo = new Lienzo(this);
        layoutn1p2.addView(lienzo);
        
        //Cargo views para el festejo
        rlFestejo = (RelativeLayout)findViewById(R.id.rl_nivel3_festejo);
		rlJuego = (RelativeLayout)findViewById(R.id.rl_nivel3_juego);
		imgCorrecto = (ImageView)findViewById(R.id.nivel3_correcto);
        
    	imgFestejo = (ImageView)findViewById(R.id.nivel3_nena_festejo);
	  	imgFestejo.setImageResource(R.anim.anim_festejo_nena_1);
	  	animFestejo = (AnimationDrawable)imgFestejo.getDrawable();
    	Utils.setInvisible(rlFestejo, 1, 0);
        
    }
    
    
	@Override
	protected void onDestroy() {
		if (puntaje>0 || incorrectos>0){
			datasource.createAlumno(new Fecha().fechaEnMilisegundos(), 3, puntaje, incorrectos);
		}
		super.onDestroy();
	 }
	
    public void resultadoCorrectoSeleccionado() {
    	
    	respuestaCorrecta = true;
    	puntaje++;
    	
    	//Mando a volar a los globos
    	volareOhOhCantareOhOhOhOh();
    	
    	felicitacion(this);
    	
    }	
    
    public void volareOhOhCantareOhOhOhOh() {
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
	    TranslateAnimation anim = new TranslateAnimation( 0, 0 , 0, -size.y);
	    anim.setDuration(1200);
	    anim.setInterpolator(new AccelerateInterpolator(1.1f));
	    
	    ScaleAnimation zoom = new ScaleAnimation(1, 0, 1, 0,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		zoom.setDuration(1000);
		signo.setAnimation(zoom);
		
		signo.startAnimation(zoom);
		rl_globos.startAnimation(anim);
		
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				rl_globos.setVisibility(View.INVISIBLE);
				signo.setVisibility(View.INVISIBLE);
				animGloboIz.cancel();
				animGloboIz.reset();
				animGloboDer.cancel();
				animGloboDer.reset();
			}
    	}, 1200);

    }
    
    public void felicitacion(final Nivel3Activity activity) {
    	
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
    	}, 1000);
    }
    
	public boolean getRespuestaCorrecta() {
		return respuestaCorrecta;
	}
	
	public void setRespuestaCorrecta(boolean r) {
		respuestaCorrecta = r;
	}
	
	class Lienzo extends View {
		
		Nivel3Activity nivel3Activity;
		
		public Lienzo(Nivel3Activity p_nivel1) {
		    super(p_nivel1);
		    nivel3Activity = p_nivel1;
		}
		
		 
		@SuppressLint("DrawAllocation")
		protected void onDraw(Canvas canvas) 
		{
			respuestaCorrecta = false;
			
			OperacionNivel3 operacion;
			
			if(tipoNivel.definirOperacion())
			{	
				// Mas
				operacion = new SumaNivel3();
				consigna.setText("Selecciona el resultado de la SUMA.");
				signo.setImageResource(R.drawable.nivel3_mas);
			}
			else
			{
				// Menos
				operacion = new RestaNivel3();
				consigna.setText("Selecciona el resultado de la RESTA.");	
				signo.setImageResource(R.drawable.nivel3_menos);
			}
			
			//Incluye a 0
			operacion.generarValores(40,true);

			globoIzquierda.setearValor(operacion.valor1.toString());
			globoDerecha.setearValor(operacion.valor2.toString());
			
			globoDerecha.setearImagen(R.drawable.nivel3_globo_derecho);
			
			// Alinea los globos y el signo con la im‡gen del payaso 
		   	Utils.acomodarView(payaso, globoIzquierda, 0.2f, null);
	    	Utils.acomodarView(payaso, globoDerecha, 0.8f, null);
	       	Utils.acomodarView(payaso, signo, null, 0.218f);	
	       	
			globoIzquierda.acomodarValor();
			globoDerecha.acomodarValor();
			
			//Obtiene 3 posibles resultados
			ArrayList<Integer> resultados = operacion.getResultadosPosiblesNivel3();
			
			resultado1.setearValor(resultados.get(0), operacion);
			resultado2.setearValor(resultados.get(1), operacion);
			resultado3.setearValor(resultados.get(2), operacion);
			
			final Animation animation = AnimationUtils.loadAnimation(nivel3Activity,R.anim.nivel3_globo_rotation);
			final Animation animation2 = AnimationUtils.loadAnimation(nivel3Activity,R.anim.nivel3_globo_rotation2);
			final ScaleAnimation zoom = new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			zoom.setStartOffset(500);
			zoom.setDuration(800);
			zoom.setFillAfter(true);
			
			
        	Utils.setInvisible(rlFestejo, 1, 0);
          	Utils.setVisible(rlJuego, 0, 500);
          	rlJuego.setVisibility(View.VISIBLE);
          	rl_globos.setVisibility(View.VISIBLE);
          	
    		animGloboIz = animation;
    		animGloboDer = animation2;
    	    globoIzquierda.startAnimation(animation);
    	    globoDerecha.startAnimation(animation2);
    		signo.startAnimation(zoom);
		}
	}
}
