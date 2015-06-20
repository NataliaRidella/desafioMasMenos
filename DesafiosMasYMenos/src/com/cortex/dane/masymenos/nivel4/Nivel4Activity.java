package com.cortex.dane.masymenos.nivel4;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.commons.EnumTipoNivel;
import com.cortex.dane.masymenos.commons.SonidoFelicitacion;
import com.cortex.dane.masymenos.commons.TipoNivel;
import com.cortex.dane.masymenos.nivel3.OperacionNivel3;
import com.cortex.dane.masymenos.nivel3.RestaNivel3;
import com.cortex.dane.masymenos.nivel3.SumaNivel3;
import com.cortex.dane.masymenos.sqlitereporte.AlumnoDataSource;
import com.cortex.dane.masymenos.utils.Fecha;
import com.cortex.dane.masymenos.utils.Utils;

@SuppressLint("ClickableViewAccessibility") 
public class Nivel4Activity extends Activity implements OnTouchListener {
	
	private TipoNivel tipoNivel;
	private RelativeLayout l_opcion;
	private RelativeLayout l_contenedor;
	private Rectangulo rectanguloSuperior; 
	private Rectangulo rectanguloInferior; 
	private Rectangulo rectanguloResultado; 
	private Cuadrado rectanguloOperacion; 
	private ImageView eje_horizontal;
	private ImageView eje_vertical;
	private TextView enunciado;
	private Lienzo lienzo;
	private int cantidadOpcionesCorrectasSeleccionadas = -1;
	
	private RelativeLayout rlJuego;
	private RelativeLayout rlFestejo;
	private AnimationDrawable animFestejo;
	private ImageView imgFestejo;
	private ImageView imgCorrecto;

	//SQLite
	private AlumnoDataSource datasource;
	private Nivel4DataSource nivel4datasource;
	private int puntaje = 0;
	private int incorrectos=0;
	
	// Nuevos patrones de disenio
	private boolean virgen = true;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		//Usar el layout definido por el xml
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.nivel4);
    	
        Intent intent = getIntent();
        EnumTipoNivel tNivel = (EnumTipoNivel) intent.getSerializableExtra("TipoNivel");
        tipoNivel = TipoNivel.getTipoNivel(tNivel);
        
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	
		RelativeLayout nivel4 = (RelativeLayout) findViewById(R.id.nivel4);
		lienzo = new Lienzo(this);
		nivel4.addView(lienzo);

   		//Inicio la base de datos
    	datasource = new AlumnoDataSource(this);
        datasource.open();  
        nivel4datasource = new Nivel4DataSource(this);
        nivel4datasource.open();
		
		l_opcion = (RelativeLayout)findViewById(R.id.l_opcion);
		l_contenedor = (RelativeLayout)findViewById(R.id.l_marco_interno);
		rectanguloSuperior = (Rectangulo)findViewById(R.id.operadorSuperior);
		rectanguloInferior = (Rectangulo)findViewById(R.id.operadorInferior);
		rectanguloResultado = (Rectangulo)findViewById(R.id.resultado);
		rectanguloOperacion = (Cuadrado)findViewById(R.id.operacion);
		eje_horizontal = (ImageView)findViewById(R.id.tabla_horizontal);
		eje_vertical = (ImageView)findViewById(R.id.tabla_vertical);
		enunciado = (TextView)findViewById(R.id.enunciado);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");
		enunciado.setTypeface(font);
		
		rectanguloResultado.getOperando().setText("RESULTADO");
		rectanguloOperacion.getOperando().setText("\u25A1");
		
		rectanguloSuperior.setOnDragListener(new RectanguloListener(this));
		rectanguloInferior.setOnDragListener(new RectanguloListener(this));
		rectanguloResultado.setOnDragListener(new RectanguloListener(this));
		rectanguloOperacion.setOnDragListener(new RectanguloListener(this));
		
		//Cargo views para el festejo
        rlFestejo = (RelativeLayout)findViewById(R.id.rl_nivel4_festejo);
		rlJuego = (RelativeLayout)findViewById(R.id.rl_nivel4_juego);
		imgCorrecto = (ImageView)findViewById(R.id.nivel4_correcto);
        
    	imgFestejo = (ImageView)findViewById(R.id.nivel4_nena_festejo);
	  	imgFestejo.setImageResource(R.anim.anim_festejo_nena_1);
	  	animFestejo = (AnimationDrawable)imgFestejo.getDrawable();
    	Utils.setInvisible(rlFestejo, 1, 0);
	}

	public void opcionCorrectaSeleccionada(){
    	
		cantidadOpcionesCorrectasSeleccionadas++;
		
		if(cantidadOpcionesCorrectasSeleccionadas >= 4)
		{
			puntaje++;
			felicitacion(this);
		}
	}
	
    public void felicitacion(final Nivel4Activity activity) {
    	
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (puntaje>0 || incorrectos>0){
			datasource.createAlumno(new Fecha().fechaEnMilisegundos(), 4, puntaje, incorrectos);
		}
	 }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
	
	class Lienzo extends View {

        public Lienzo(Context context) {
            super(context);
        }
        
        @SuppressLint("DrawAllocation")
		protected void onDraw(Canvas canvas) {
        	
        	Utils.acomodarView(l_contenedor,eje_horizontal, 0.5f, 0.62f);
        	Utils.acomodarView(l_contenedor,rectanguloSuperior, 0.5f, 0.25f);
        	Utils.acomodarView(l_contenedor,rectanguloInferior, 0.5f, 0.5f);
        	Utils.acomodarView(l_contenedor,rectanguloResultado, 0.5f, 0.74f);
        	Utils.acomodarView(l_contenedor,rectanguloOperacion, 0.1f, 0.38f);
        	
        	Utils.fade(eje_vertical, 0.3f, 0.3f, 0);
        	
        	cantidadOpcionesCorrectasSeleccionadas = 0;
        	
        	OperacionNivel3 operacion;
        	
        	rectanguloSuperior.inicializarRectangulo();
    		rectanguloInferior.inicializarRectangulo();
    		rectanguloResultado.inicializarRectangulo();
    		rectanguloOperacion.inicializarRectangulo();
    		

    		// Divide el enunciado para poder encajarle los numeros
			String delims = "[#]";
			String[] partesDeEnunciado;
			
			// Obtengo las imagenes de un enunciado random
			Enunciado e = nivel4datasource.getEnunciado(getApplicationContext());
			ArrayList<Imagen> imagenes = e.getImagenes();
			Imagen imagenGrande = imagenes.get(0);
			Imagen imagenMediana = imagenes.get(1);
			Imagen imagenChica = imagenes.get(2);

			if(tipoNivel.definirOperacion())
			{	
				// Mas
				operacion = new SumaNivel3();
				operacion.generarValores(40, false);
				//Elijo un enunciado de suma
				rectanguloOperacion.setValorCorrecto(new NoConmutativo("+"));
				rectanguloSuperior.getOperando().setText("OPERANDO");
				rectanguloInferior.getOperando().setText("OPERANDO");
				partesDeEnunciado = e.getEnunciadoMas().split(delims);
				
				Conmutativo conmutativo = new Conmutativo();
				conmutativo.ingresarResultado(operacion.getValor1());
				conmutativo.ingresarResultado(operacion.getValor2());
				
				rectanguloSuperior.setValorCorrecto(conmutativo);
				rectanguloInferior.setValorCorrecto(conmutativo);
			}
			else
			{
				// Menos
				operacion = new RestaNivel3();
				operacion.generarValores(40, false);
				//Elijo un enunciado de resta
				rectanguloOperacion.setValorCorrecto(new NoConmutativo("-"));
				rectanguloSuperior.getOperando().setText("OPERANDO");
				rectanguloInferior.getOperando().setText("OPERANDO");
				partesDeEnunciado = e.getEnunciadoMenos().split(delims);
				
				rectanguloSuperior.setValorCorrecto(new NoConmutativo(operacion.getValor1()));
				rectanguloInferior.setValorCorrecto(new NoConmutativo(operacion.getValor2()));
			}
			
			ArrayList<String> opciones = operacion.getResultadosPosiblesEnTesto(6);
			
			opciones.add(operacion.getValor1().toString());
			opciones.add(operacion.getValor2().toString());
			Collections.shuffle(opciones);
			
			((OperadorSuma) l_opcion.getChildAt(0)).nuevoEjercicio("+",l_opcion,imagenMediana,rlJuego); // 2
			((OperadorResta) l_opcion.getChildAt(1)).nuevoEjercicio("-",l_opcion,imagenChica,rlJuego); // 6
			((Opcion) l_opcion.getChildAt(2)).nuevoEjercicio(opciones.get(0),l_opcion,imagenMediana,rlJuego); // 1
			((Opcion) l_opcion.getChildAt(3)).nuevoEjercicio(opciones.get(1),l_opcion,imagenMediana,rlJuego); // 5
			((Opcion) l_opcion.getChildAt(4)).nuevoEjercicio(opciones.get(2),l_opcion,imagenChica,rlJuego); // 7
			((Opcion) l_opcion.getChildAt(5)).nuevoEjercicio(opciones.get(3),l_opcion,imagenChica,rlJuego); // 4
			((Opcion) l_opcion.getChildAt(6)).nuevoEjercicio(opciones.get(4),l_opcion,imagenGrande,rlJuego); // 3
			((Opcion) l_opcion.getChildAt(7)).nuevoEjercicio(opciones.get(5),l_opcion,imagenGrande,rlJuego); // 8
			((Opcion) l_opcion.getChildAt(8)).nuevoEjercicio(opciones.get(6),l_opcion,imagenMediana,rlJuego); // 9
			((Opcion) l_opcion.getChildAt(9)).nuevoEjercicio(opciones.get(7),l_opcion,imagenChica,rlJuego); // 10
			
			rectanguloResultado.setValorCorrecto(new NoConmutativo(operacion.getResultadoCorrecto()));
			
			String enunciadoAux = partesDeEnunciado[0] + operacion.getValor1() + partesDeEnunciado[1] + operacion.getValor2() + partesDeEnunciado[2];
			String enunciadoAuxAux = enunciadoAux.replaceFirst("@", operacion.getValor1().equals(1) ? e.getSingular() : e.getPlural());
			String enunciadoAuxAuxAuxPostaFinalNoQuieroProgramarMasEnMiPutaVidaAux = enunciadoAuxAux.replaceFirst("@", operacion.getValor2().equals(1) ? e.getSingular() : e.getPlural());
			enunciado.setText(enunciadoAuxAuxAuxPostaFinalNoQuieroProgramarMasEnMiPutaVidaAux);
			
			// TODO sacar esto, solamente lo puse porque no funciona bien en la kindle sino
//			Utils.fade(rectanguloSuperior.getOperando(), 0.99f, 1, 150);
//			Utils.fade(rectanguloInferior.getOperando(), 0.99f, 1, 150);
//			Utils.fade(rectanguloResultado.getOperando(), 0.99f, 1, 150);
//			Utils.fade(rectanguloOperacion.getOperando(), 0.99f, 1, 150);

			if(virgen)
			{
				virgen = false;
				this.invalidate();
			}
			
        	Utils.setInvisible(rlFestejo, 1, 0);
          	Utils.setVisible(rlJuego, 0, 500);
          	rlJuego.setVisibility(View.VISIBLE);
        }
    }
	
	private class RectanguloListener implements View.OnDragListener  {

		private Activity nivel4;
		
		public RectanguloListener(Activity a) {
			nivel4 = a;
		}
		
		@Override
	    public boolean onDrag(View v, DragEvent event) {
			Opcion opcion = (Opcion) event.getLocalState();
			Cuadrado cuadrado = (Cuadrado) v;
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
		        	rectanguloSuperior.drag(nivel4);
		    		rectanguloInferior.drag(nivel4);
		    		rectanguloResultado.drag(nivel4);
		    		rectanguloOperacion.drag(nivel4);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					cuadrado.exhibite(opcion.getHexaColor());
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					cuadrado.tapate();
					break;
				case DragEvent.ACTION_DROP:
					if(cuadrado.getValorCorrecto().esCorrecto(opcion.getOpcionTexto().getText()) && !opcion.isSeleccionadoAnteriormente())
					{
						cuadrado.rellenar(opcion);
						opcion.setSeleccionadoAnteriormente(true);
						opcionCorrectaSeleccionada();
					}
					else
					{
						opcion.visibilizate();
						incorrectos++;
					}
					
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					if(!opcion.isSeleccionadoAnteriormente())
						opcion.visibilizate();
					
		        	rectanguloSuperior.endDrag();
		    		rectanguloInferior.endDrag();
		    		rectanguloResultado.endDrag();
		    		rectanguloOperacion.endDrag();

					break;
				default:
					break;
		  }
	      return true;
	    }
	}
}
