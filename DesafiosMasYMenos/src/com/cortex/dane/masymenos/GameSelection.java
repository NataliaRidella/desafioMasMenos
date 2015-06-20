package com.cortex.dane.masymenos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.commons.EnumTipoNivel;
import com.cortex.dane.masymenos.nivel1.Nivel1Activity;
import com.cortex.dane.masymenos.nivel2.Nivel2Activity;
import com.cortex.dane.masymenos.nivel3.Nivel3Activity;
import com.cortex.dane.masymenos.nivel4.Nivel4Activity;

public class GameSelection extends Activity {

	private PanelGroup panelGroup;
	private PanelGroup previousPanelGroup;
	private LinearLayout left;
	private LinearLayout right;
	private LinearLayout left2;
	private LinearLayout right2;
	private EnumTipoNivel operacion = EnumTipoNivel.MASYMENOS;
	private TextView jugar;
	private TextView consigna;
	private TextView concepto;
	private MediaPlayer media;
	private AnimationDrawable animation;
	    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_game_selection);
        
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
         
        //Setea el singleton para poder ser accedido de otras clases
        Recursos.setResources(this.getResources());
        
        //Cargar los valores seteados en el xml
    	Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");
    	
    	((TextView)findViewById(R.id.tv_nivel_left)).setTypeface(font);
    	((TextView)findViewById(R.id.tv_nivel_right)).setTypeface(font);
    	((TextView)findViewById(R.id.jugar)).setTypeface(font);
    	((TextView)findViewById(R.id.nivel_text_left)).setTypeface(font);
    	((TextView)findViewById(R.id.nivel_text_right)).setTypeface(font);
    	((TextView)findViewById(R.id.textoSubnivelIzquierda)).setTypeface(font);
    	((TextView)findViewById(R.id.textoSubnivelDerecha)).setTypeface(font);
    	concepto = (TextView)findViewById(R.id.operaciones);
    	concepto.setTypeface(font);
    	concepto.setOnClickListener(new OperacionListener());
    	
    	setConsigna((TextView)findViewById(R.id.consigna));
    	getConsigna().setTypeface(font);
    	
    	left = (LinearLayout)findViewById(R.id.left); 
    	right = (LinearLayout)findViewById(R.id.right); 
    	left2 = (LinearLayout)findViewById(R.id.left2); 
    	right2 = (LinearLayout)findViewById(R.id.right2); 
    	jugar = (TextView)findViewById(R.id.jugar); 
    	
    	String consigna1 = "Elije el \u00e1rbol con m\u00e1s o menos cantidad de frutas seg\u00fan lo indicado";
    	String consigna2 = "Arrastra los p\u00e9talos cuyos resultados sean iguales al n\u00famero de la flor";
    	String consigna3 = "Elije la nube que contenga el resultado de la operaci\u00f3n";
    	String consigna4 = "Lee el enunciado, elije la operaci\u00f3n y arrastra los n\u00fameros para resolver el problema";
    	
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	Left l = new Left(size.x);
    	Right r = new Right(size.x);
    	
    	// Panel correspondiente a cada ejercicio que cambia la pantalla que esta al fondo del xml del activity
    	PanelIntent ejercicio1 = new PanelIntent(left2,  R.drawable.gs_darkmaincolor,  new ImagenPanel(R.drawable.gs_arbol,"M"+Recursos.a+"s y Menos"), this, l, consigna1, new Intent(this, Nivel1Activity.class), R.raw.nivel1_audio, R.anim.anim_gs_nivel1);
    	PanelIntent ejercicio2 = new PanelIntent(right2, R.drawable.gs_lightmaincolor, new ImagenPanel(R.drawable.gs_flor,"Sumas y Restas"),  this, r, consigna2, new Intent(this, Nivel2Activity.class), R.raw.nivel2_audio, R.anim.anim_gs_nivel2);
    	PanelIntent ejercicio3 = new PanelIntent(left2,  R.drawable.gs_darkmaincolor,  new ImagenPanel(R.drawable.gs_globo,"Sumas y Restas"), this, l, consigna3, new Intent(this, Nivel3Activity.class), R.raw.nivel3_audio, R.anim.anim_gs_nivel3);
 		PanelIntent ejercicio4 = new PanelIntent(right2, R.drawable.gs_lightmaincolor, new ImagenPanel(R.drawable.gs_tabla, "Problemas"),   this, r, consigna4, new Intent(this, Nivel4Activity.class), R.raw.nivel4_audio, R.anim.anim_gs_nivel4);
 		    	
 		// Conjunto de paneles con los ejercicios de cada nivel
 		PanelGroup seleccionDeEjerciciosDelNivel1 = new PanelGroup(ejercicio1,ejercicio2);
 		PanelGroup seleccionDeEjerciciosDelNivel2 = new PanelGroup(ejercicio3,ejercicio4);
 		
 		// Panel correspondiente a cada nivel que conduce a la seleccion de ejercicios
 		Panelception nivel1 = new Panelception(left,  R.drawable.gs_lightmaincolor, new TextoPanel("NIVEL 1", "#008080"), this, l, seleccionDeEjerciciosDelNivel1); 
		Panelception nivel2 = new Panelception(right, R.drawable.gs_darkmaincolor,  new TextoPanel("NIVEL 2", "#30f0f0"), this, r, seleccionDeEjerciciosDelNivel2);
    	
		// Conjunto de paneles con ambos niveles
		panelGroup = new PanelGroup(nivel1,nivel2);
    	
    	getPanelGroup().visibilizate();
    	
    }
    
    public void onClick(View v){
    	
        switch (v.getId()) {
       		case R.id.left :
       	    	getPanelGroup().leftTouch();
       	        break;
       		case R.id.left2 :
       	    	getPanelGroup().leftTouch();
       	        break;
            case R.id.right:
            	getPanelGroup().rightTouch();
       	        break;
            case R.id.right2:
            	getPanelGroup().rightTouch();
       	        break;
          }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//Cuando vuelve de algun Juego
    	super.onActivityResult(requestCode, resultCode, data);
    	animation.start();
    	animation.stop();
    }
    
    @Override
    public void onBackPressed() {
    	//Cuando vuelve de algun panel abierto
    	
    	if(previousPanelGroup != null)
    	{
    		//Si esta seleccionando algun ejercicio de un nivel ya elegido (2do panelGroup)
    		if(panelGroup.isCerrado()) 
    		{
    			//Si quiere volver a los paneles de niveles y no abrio los de los ejercicios
    			previousPanelGroup.cerrate();
    			panelGroup = previousPanelGroup;
    			previousPanelGroup = null;
    		}
    		else
    		{
    			//Si quiere volver a los paneles de niveles y abrio los de los ejercicios
    			panelGroup.cerrate();
				operacion = EnumTipoNivel.MASYMENOS;
				concepto.setText("MAS Y MENOS");
				animation.stop();
				media.stop();
    		}
    	}
    	else
    		finish();
    }
    
    public void startAnimation(int audio,int animacion) {
    	ImageView myAnimation = (ImageView)findViewById(R.id.iv_nena);
    	myAnimation.setImageResource(animacion);
    	animation = (AnimationDrawable)myAnimation.getDrawable();
    	media = MediaPlayer.create(this, audio);
        media.setVolume(0.15f,0.15f);
    	
        // Retraso el sonido para que se sincronice con la animación 
        (new Handler()).postDelayed(new Runnable() {
			public void run() {
					media.start();
	            }
	        }, 2200);
        
        // No se porque hago esto, internet al parecer si sabe. No lo cuestionemos
    	myAnimation.post(
			new Runnable(){
				@Override
				public void run() {
					animation.stop();
					animation.start();
				}
		});
    }
    
    public void iniciaEjercicio(Intent intent){
    	media.stop();
    	animation.stop();
    	startActivityForResult(intent,0);
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();

	 }

	@Override
	protected void onPause() {
		super.onPause();

	}

	public PanelGroup getPanelGroup() {
		return panelGroup;
	}

	public void setPanelGroup(PanelGroup panelGroup) {
		this.panelGroup = panelGroup;
	}

	public LinearLayout getLeft() {
		return left;
	}

	public void setLeft(LinearLayout left) {
		this.left = left;
	}

	public LinearLayout getRight() {
		return right;
	}

	public void setRight(LinearLayout right) {
		this.right = right;
	}

	public LinearLayout getLeft2() {
		return left2;
	}

	public void setLeft2(LinearLayout left2) {
		this.left2 = left2;
	}

	public LinearLayout getRight2() {
		return right2;
	}

	public void setRight2(LinearLayout right2) {
		this.right2 = right2;
	}

	public PanelGroup getPreviousPanelGroup() {
		return previousPanelGroup;
	}

	public void setPreviousPanelGroup(PanelGroup previousPanelGroup) {
		this.previousPanelGroup = previousPanelGroup;
	}

	public TextView getJugar() {
		return jugar;
	}

	public void setJugar(TextView jugar) {
		this.jugar = jugar;
	}
	
	public EnumTipoNivel getOperacion() {
		return operacion;
	}

	public void setOperacion(EnumTipoNivel operacion) {
		this.operacion = operacion;
	}

	
	public TextView getConsigna() {
		return consigna;
	}

	public void setConsigna(TextView consigna) {
		this.consigna = consigna;
	}


	private class OperacionListener implements View.OnClickListener  {
		
		@Override
		public void onClick(View v) {
			
			if(operacion.equals(EnumTipoNivel.MASYMENOS)) {
				operacion = EnumTipoNivel.MAS;
				concepto.setText("MAS");
			}
			else if(operacion.equals(EnumTipoNivel.MAS)) {
				operacion = EnumTipoNivel.MENOS;
				concepto.setText("MENOS");
			}
			else if(operacion.equals(EnumTipoNivel.MENOS)) {
				operacion = EnumTipoNivel.MASYMENOS;
				concepto.setText("MAS Y MENOS");
			}
	    		
		}
	}		
}
