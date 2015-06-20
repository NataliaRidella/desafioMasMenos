package com.cortex.dane.masymenos.sqlitereporte;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.achartengine.GraphicalView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.utils.Fecha;
import com.cortex.dane.masymenos.utils.Utils;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

public class Reporte extends Activity  {
	
	//SQLite
	private AlumnoDataSource datasource;
	
	//Selector de Fecha
	private TextView tFechaIni,tFechaFin;
    private Button bFechaIni,bFechaFin,bBorrarDatos,bMostrarDatos;
    private ImageView bcambiarGraficoRight,bcambiarGraficoLeft, bcambiarNivelRight, bcambiarNivelLeft;
    private int anoIni,anoFin;
    private int mesIni,mesFin;
    private int diaIni,diaFin;
    private int boton=0;
    private int cantidadDias=0, cantidadEntradas=0;
    private int vistaAnteriorID=-1, vistaAnteriorID2=-1, vistaAnteriorID3=-1, vistaAnteriorID4=-1;
    private int viendoGrafico=0;
    static final int DATE_DIALOG_ID = 0;
    static final int INI = 0;
    static final int FIN = 1;
    private long primerFecha, ultimaFecha, fechaMaximo;
    private TextView reporteConsigna, reporteTitulo, reporteTituloPrimero;
    private TextView selectorNivelTodos, selectorNivel1, selectorNivel2, selectorNivel3, selectorNivel4;
    
	TableLayout table_layout;
	
	//Viewflipper
	ViewFlipper viewflipper, viewflipperGrafico, viewflipperNivel;
	Animation slide_in_left, slide_out_right, slide_out_left, slide_in_right, fade_in, fade_out;
	LinearLayout layoutGrafico, linearlaygrafico;
	Context c;
	Recursos recursos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		c = this;
		recursos = new Recursos();
		
		//Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.reporte);
        
        reporteConsigna = (TextView) findViewById(R.id.reporteconsigna);
        reporteTitulo = (TextView) findViewById(R.id.reporteTitulo);
        reporteTituloPrimero = (TextView) findViewById(R.id.reporteTituloPrimero);
        bFechaIni = (Button) findViewById(R.id.fechaInicial);
        bFechaFin = (Button) findViewById(R.id.fechaFinal);
        bBorrarDatos = (Button) findViewById(R.id.borrarDatos);
        bMostrarDatos = (Button) findViewById(R.id.mostrarGrafico);
        bcambiarGraficoRight = (ImageView) findViewById(R.id.cambiarGraficoRight);
        bcambiarGraficoLeft = (ImageView) findViewById(R.id.cambiarGraficoLeft);
        viewflipper = (ViewFlipper) findViewById(R.id.viewflipperReporte);
        viewflipperGrafico = (ViewFlipper) findViewById(R.id.viewflipperGrafico);
        viewflipperNivel = (ViewFlipper) findViewById(R.id.viewFlipperNivel);
        bcambiarNivelRight = (ImageView) findViewById(R.id.cambiarNivelRight);
        bcambiarNivelLeft = (ImageView) findViewById(R.id.cambiarNivelLeft);
        linearlaygrafico = (LinearLayout) findViewById(R.id.linearLayGrafico);
        
        selectorNivelTodos = (TextView) findViewById(R.id.textTodos);
        selectorNivel1 = (TextView) findViewById(R.id.textNivel1);
        selectorNivel2 = (TextView) findViewById(R.id.textNivel2);
        selectorNivel3 = (TextView) findViewById(R.id.textNivel3);
        selectorNivel4 = (TextView) findViewById(R.id.textNivel4);
        
        final Typeface arch_rival = Typeface.createFromAsset(getAssets(),"sf_arch_rival.ttf");
        final Typeface avenir = Typeface.createFromAsset(getAssets(),"avenir.ttf");
        reporteTitulo.setTypeface(arch_rival);
        reporteTituloPrimero.setTypeface(arch_rival);
        bBorrarDatos.setTypeface(arch_rival);
        bMostrarDatos.setTypeface(arch_rival);
        
        selectorNivelTodos.setTypeface(arch_rival);
        selectorNivel1.setTypeface(arch_rival);
        selectorNivel2.setTypeface(arch_rival);
        selectorNivel3.setTypeface(arch_rival);
        selectorNivel4.setTypeface(arch_rival);
        
        bFechaIni.setTypeface(arch_rival);
        bFechaFin.setTypeface(arch_rival);
        
        reporteConsigna.setTypeface(avenir);
        
               
        bFechaIni.setTextColor(Color.WHITE);
        bFechaFin.setTextColor(Color.WHITE);
        
		slide_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
		slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
		slide_in_right = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
		slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
		
		fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		
        prepararListenerBotones();
        calendarioFechaDefault();
             
        //SQLite       
        datasource = new AlumnoDataSource(this);
        datasource.open();        
        
        actualizarGrafico();

        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        
		
	}
	
	private void prepararListenerBotones() {
		bFechaIni.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	boton=0;
                showDialog(INI);
            }
        });
        
        bFechaFin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	boton=1;
                showDialog(FIN);
            }
        });
        bBorrarDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	        	break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	        	datasource.vaciarTabla();
                        	actualizarGrafico();
            	        	break;
            	        }
            	    }
            	};

            	AlertDialog.Builder builder = new AlertDialog.Builder(c);
            	//builder.setMessage("Esta seguro que desea borrar todos los datos de la tabla?").setNegativeButton("Si", dialogClickListener)
            	//    .setPositiveButton("No", dialogClickListener).show();
            	builder.setMessage(recursos.getSigno() + "Est" + recursos.getA() + " seguro que desea borrar todos los datos de la tabla?").setNegativeButton("S" + recursos.getI(), dialogClickListener)
        	    .setPositiveButton("No", dialogClickListener).show();
            	
            }
        });
        bMostrarDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	actualizarGrafico();
            	if (cantidadEntradas>1){
            		reporteTitulo.setText("Desde " + bFechaIni.getText() + "Hasta " + bFechaFin.getText());
            		viendoGrafico=1;
            		viewflipper.setInAnimation(fade_in);
            		viewflipper.setOutAnimation(fade_out);
            		viewflipper.showNext();
            		
            		bcambiarGraficoLeft.setVisibility(View.VISIBLE);
            		bcambiarGraficoRight.setVisibility(View.VISIBLE);
            		
            	}else if (cantidadEntradas==0){
            		AlertDialog.Builder builder = new AlertDialog.Builder(c);

            		builder.setMessage("No se encontr"+recursos.getO()+" ningun resultado")

            		.setTitle("Resultado de la busqueda")
            		
            		.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {  
            			   public void onClick(DialogInterface dialog, int id) {  
            				     dialog.cancel(); 
            				}  
            				});

            		AlertDialog dialog = builder.create();
            		dialog.show();
            	}else if(cantidadEntradas==1){
            		reporteTitulo.setText("Desde " + bFechaIni.getText() + "Hasta " + bFechaFin.getText());
            		viendoGrafico=1;
            		
            		
            		bcambiarGraficoLeft.setVisibility(View.INVISIBLE);
            		bcambiarGraficoRight.setVisibility(View.INVISIBLE);
            		
           		
            		viewflipper.setInAnimation(fade_in);
            		viewflipper.setOutAnimation(fade_out);
            		viewflipper.showNext();
            	}
            }
        });
        
        bcambiarGraficoRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	viewflipperGrafico.setInAnimation(slide_in_right);
            	viewflipperGrafico.setOutAnimation(slide_out_left);
            	viewflipperGrafico.showNext();
            }
        	
        });
        
        bcambiarGraficoLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	viewflipperGrafico.setInAnimation(slide_in_left);
            	viewflipperGrafico.setOutAnimation(slide_out_right);
            	viewflipperGrafico.showPrevious();
            }
        	
        });
        
        bcambiarNivelRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	viewflipperNivel.setInAnimation(slide_in_right);
            	viewflipperNivel.setOutAnimation(slide_out_left);
            	viewflipperNivel.showNext();
            }
        	
        });
        
        bcambiarNivelLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	viewflipperNivel.setInAnimation(slide_in_left);
            	viewflipperNivel.setOutAnimation(slide_out_right);
            	viewflipperNivel.showPrevious();
            }
        	
        });
	}
	
	private void calendarioFechaDefault() {
		final Calendar cal = Calendar.getInstance();
        anoFin = cal.get(Calendar.YEAR);
        mesFin = cal.get(Calendar.MONTH);
        diaFin = cal.get(Calendar.DAY_OF_MONTH);
 
        updateDisplay(FIN);
        
        cal.add(Calendar.MONTH, -1);
        
        anoIni = cal.get(Calendar.YEAR);
        mesIni = cal.get(Calendar.MONTH);
        diaIni = cal.get(Calendar.DAY_OF_MONTH);
 
        updateDisplay(INI);		
	}
	
	private DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			if (boton==0)
			{
				anoIni = year;
				mesIni = monthOfYear;
				diaIni = dayOfMonth;
				updateDisplay(INI);
				displayToast(INI);
			}
			else {
				anoFin = year;
				mesFin = monthOfYear;
				diaFin = dayOfMonth;
				updateDisplay(FIN);
				displayToast(FIN);
			}
			//CARGA DEFAULT PARA HOY, DESPUES SACAR
			if(anoIni==1971 && anoFin==1971 && mesIni == 10 && mesFin == 10 && diaIni == 15 && diaFin == 15){
				datasource.cargarDummy();
				Toast.makeText(c, "Look to La Luna",  Toast.LENGTH_SHORT).show();
				actualizarGrafico();
			}
			
		}
	};
	
	private void updateDisplay(int id) {
		if (id==INI) {
			bFechaIni.setText(
		            new StringBuilder()
		                    // Month is 0 based so add 1
		                    .append(diaIni).append("/")
		                    .append(mesIni + 1).append("/")
		                    .append(anoIni).append(" "));
		}
		else {
			bFechaFin.setText(
		            new StringBuilder()
		                    // Month is 0 based so add 1
		                    .append(diaFin).append("/")
		                    .append(mesFin + 1).append("/")
		                    .append(anoFin).append(" "));
		}
    }
	
	private void displayToast(int id) {
		if (id==INI) {
			Toast.makeText(this, new StringBuilder().append("La fecha elegida es ").append(bFechaIni.getText()),  Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(this, new StringBuilder().append("La fecha elegida es ").append(bFechaFin.getText()),  Toast.LENGTH_SHORT).show();
		}
             
    }
	
	public void actualizarGrafico(){
		//Quitar view anterior si existe
		
    	LinearLayout layout = (LinearLayout) findViewById(R.id.reporte);
    	if (vistaAnteriorID>-1){
    		View vistaAnterior = layout.findViewById(vistaAnteriorID);
    		viewflipperGrafico.removeView(vistaAnterior);
    		vistaAnteriorID=-1;
    	}
    	if (vistaAnteriorID2>-1){
    		View vistaAnterior = layout.findViewById(vistaAnteriorID2);
    		linearlaygrafico.removeView(vistaAnterior);
    		vistaAnteriorID2=-1;
    	}
    	if (vistaAnteriorID3>-1){
    		View vistaAnterior = layout.findViewById(vistaAnteriorID3);
    		viewflipperGrafico.removeView(vistaAnterior);
    		vistaAnteriorID3=-1;
    	}
    	if (vistaAnteriorID4>-1){
    		View vistaAnterior = layout.findViewById(vistaAnteriorID4);
    		viewflipperGrafico.removeView(vistaAnterior);
    		vistaAnteriorID4=-1;
    	}
    	
    	
		long fechaInicial = new  GregorianCalendar(anoIni, mesIni, diaIni).getTimeInMillis();
		long fechaFinal = new  GregorianCalendar(anoFin, mesFin, diaFin).getTimeInMillis();
		long nivel = Long.parseLong((String) viewflipperNivel.getCurrentView().getTag());
		
    	Cursor cursor = datasource.buscarReporte(fechaInicial, fechaFinal, nivel);
    	
    	
    	cursor.moveToFirst();
    	
    	cantidadEntradas=cursor.getCount();
    	

        final Typeface arch_rival = Typeface.createFromAsset(getAssets(),"sf_arch_rival.ttf");
    	if(cursor!=null && cursor.getCount()==1){
    		GraphViewData[] data = new GraphViewData[cursor.getCount()];
    		GraphViewData[] dataCorrectos = new GraphViewData[cursor.getCount()];
    		GraphViewData[] dataIncorrectos = new GraphViewData[cursor.getCount()];
    	
    		int maximaeficacia = 0;
    		int promedio = 0, total = 0;
    		
    		int correctosTotal=0;
    		int incorrectosTotal=0;
    		
    		primerFecha = cursor.getLong(0);
    		    	
    		for (int i=0; i<cursor.getCount(); i++) {
    			data[i] = new GraphViewData(cursor.getLong(0), ((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2))));
    			dataCorrectos[i] = new GraphViewData(cursor.getLong(0), (cursor.getInt(1)));
    			dataIncorrectos[i] = new GraphViewData(cursor.getLong(0), (cursor.getInt(2)));
    			correctosTotal+=cursor.getInt(1);
    			incorrectosTotal+=cursor.getInt(2);
    			if ((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2))>maximaeficacia){
    				maximaeficacia = (int)((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2)));
    				fechaMaximo = cursor.getLong(0);
    			}
    			total+=((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2)));
    			ultimaFecha = cursor.getLong(0);
    			cursor.moveToNext();
    		}
    		cantidadDias = (int) ((ultimaFecha-primerFecha) / (1000*60*60*24));;
    		
    		promedio = total/cursor.getCount();
    		cursor.moveToPrevious();
    	
    		GraphicalView chartView = PieChartView.getNewInstance(getApplicationContext(), correctosTotal, incorrectosTotal);
    		
    		TextView tv = new TextView(getApplicationContext());
    		tv.setText("Valor promedio de eficacia: " + String.valueOf(promedio) + "%.");
    		tv.setId(Utils.generateViewId());
    		tv.setTextColor(Color.WHITE);
    		tv.setTextSize(20);
    		tv.setTypeface(arch_rival);
    		vistaAnteriorID2=tv.getId();
    		linearlaygrafico.addView(tv);
    		
    		chartView.setId(Utils.generateViewId());
    		
    		vistaAnteriorID4=chartView.getId();
    		
    		viewflipperGrafico.addView(chartView);
    	}else if(cursor!=null && cursor.getCount()>0){
    	
    		GraphViewData[] data = new GraphViewData[cursor.getCount()];
    		GraphViewData[] dataCorrectos = new GraphViewData[cursor.getCount()];
    		GraphViewData[] dataIncorrectos = new GraphViewData[cursor.getCount()];
    	
    		int maximaeficacia = 0;
    		int promedio = 0, total = 0;
    		
    		int correctosTotal=0;
    		int incorrectosTotal=0;
    		
    		primerFecha = cursor.getLong(0);
    		    	
    		for (int i=0; i<cursor.getCount(); i++) {
    			data[i] = new GraphViewData(cursor.getLong(0), ((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2))));
    			dataCorrectos[i] = new GraphViewData(cursor.getLong(0), (cursor.getInt(1)));
    			dataIncorrectos[i] = new GraphViewData(cursor.getLong(0), (cursor.getInt(2)));
    			correctosTotal+=cursor.getInt(1);
    			incorrectosTotal+=cursor.getInt(2);
    			if ((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2))>maximaeficacia){
    				maximaeficacia = (int)((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2)));
    				fechaMaximo = cursor.getLong(0);
    			}
    			total+=((cursor.getInt(1)*100)/(cursor.getInt(1)+cursor.getInt(2)));
    			ultimaFecha = cursor.getLong(0);
    			cursor.moveToNext();
    		}
    		cantidadDias = (int) ((ultimaFecha-primerFecha) / (1000*60*60*24));;
    		
    		promedio = total/cursor.getCount();
    		cursor.moveToPrevious();
    	
    		LineGraphView graphView = new LineGraphView(
    				getApplicationContext()
    				, ""
    				);
    		
    		LineGraphView graphViewCorrIncorr = new LineGraphView(
    				getApplicationContext()
    				, ""
    				);
    		
    		GraphViewSeries serieCorrectos = new GraphViewSeries("Respuestas\r\ncorrectas", new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3), dataCorrectos);
    		GraphViewSeries serieIncorrectos = new GraphViewSeries("Respuestas\r\nincorrectas", new GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3), dataIncorrectos);
    	
    		GraphicalView chartView = PieChartView.getNewInstance(getApplicationContext(), correctosTotal, incorrectosTotal);
    	           	
    		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
    			@Override
    			public String formatLabel(double value, boolean isValueX) {
    				if (isValueX){
    					return new Fecha().fechaMiliaGregoriano((long)value);
    				}
    				return (int)value + "%";
    			}
    		});
    		
    		graphViewCorrIncorr.setCustomLabelFormatter(new CustomLabelFormatter() {
    			@Override
    			public String formatLabel(double value, boolean isValueX) {
    				if (isValueX){
    					return new Fecha().fechaMiliaGregoriano((long)value);
    				}
    				return String.valueOf((int)value);
    			}
    		});
    		
    		graphView.setManualYMaxBound(100);
    		graphView.setManualYMinBound(0);
    		graphView.setDrawDataPoints(true);
    		graphView.setDataPointsRadius(10f);
    		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.WHITE);
    		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.WHITE);
    		
    		graphViewCorrIncorr.setManualYMinBound(0);
    		graphViewCorrIncorr.setDrawDataPoints(true);
    		graphViewCorrIncorr.setDataPointsRadius(10f);
    		graphViewCorrIncorr.getGraphViewStyle().setHorizontalLabelsColor(Color.WHITE);
    		graphViewCorrIncorr.getGraphViewStyle().setVerticalLabelsColor(Color.WHITE);
    		graphViewCorrIncorr.setShowLegend(true);
    		graphViewCorrIncorr.setLegendAlign(LegendAlign.BOTTOM);
    		graphViewCorrIncorr.setLegendWidth(250);
    		
    		if (cantidadDias>30) {
    			graphView.setViewPort(primerFecha, 2592000000f);
        		graphView.setScrollable(true);
        		graphViewCorrIncorr.setViewPort(primerFecha, 2592000000f);
        		graphViewCorrIncorr.setScrollable(true);
    		}    		
    		
    		
    		TextView tv = new TextView(getApplicationContext());
    		tv.setText("Valor promedio de eficacia: " + String.valueOf(promedio) + "%.");
    		tv.setId(Utils.generateViewId());
    		tv.setTextColor(Color.WHITE);
    		tv.setTextSize(20);
    		tv.setTypeface(arch_rival);
    		vistaAnteriorID2=tv.getId();
    		linearlaygrafico.addView(tv);
    	
    		graphView.addSeries(new GraphViewSeries("", new GraphViewSeriesStyle(Color.RED, 3), data));
    		graphView.getGraphViewStyle().setTextSize(18);
    		graphView.setId(Utils.generateViewId());
    		graphView.setPadding(5, 0, 60, 5);
    		graphViewCorrIncorr.getGraphViewStyle().setTextSize(18);
    		graphViewCorrIncorr.setId(Utils.generateViewId());
    		graphViewCorrIncorr.setPadding(5, 0, 60, 5);
    		graphViewCorrIncorr.addSeries(serieCorrectos);
    		graphViewCorrIncorr.addSeries(serieIncorrectos);
    		
    		chartView.setId(Utils.generateViewId());
    		
    		vistaAnteriorID=graphView.getId();
    		vistaAnteriorID3=graphViewCorrIncorr.getId();
    		vistaAnteriorID4=chartView.getId();
    		
    		viewflipperGrafico.addView(graphView);
    		viewflipperGrafico.addView(graphViewCorrIncorr);
    		viewflipperGrafico.addView(chartView);
    		
    	}else{
    		TextView tv = new TextView(getApplicationContext());
    		tv.setText("No se encontr"+recursos.getO()+" ningun resultado");
    		tv.setId(Utils.generateViewId());
    		tv.setTextColor(Color.WHITE);
    		tv.setTextSize(20);
    		tv.setTypeface(arch_rival);
    		vistaAnteriorID2=tv.getId();
    		linearlaygrafico.addView(tv);
    	}
	}
	
	@Override
    public void onBackPressed(){
		if (viendoGrafico==1)
		{
			//Quitar view anterior si existe
			LinearLayout layout = (LinearLayout) findViewById(R.id.reporte);
	    	if (vistaAnteriorID>-1){
	    		View vistaAnterior = layout.findViewById(vistaAnteriorID);
	    		viewflipperGrafico.removeView(vistaAnterior);
	    		vistaAnteriorID=-1;
	    	}
	    	if (vistaAnteriorID2>-1){
	    		View vistaAnterior = layout.findViewById(vistaAnteriorID2);
	    		linearlaygrafico.removeView(vistaAnterior);
	    		vistaAnteriorID2=-1;
	    	}
	    	if (vistaAnteriorID3>-1){
	    		View vistaAnterior = layout.findViewById(vistaAnteriorID3);
	    		viewflipperGrafico.removeView(vistaAnterior);
	    		vistaAnteriorID3=-1;
	    	}
	    	if (vistaAnteriorID4>-1){
	    		View vistaAnterior = layout.findViewById(vistaAnteriorID4);
	    		viewflipperGrafico.removeView(vistaAnterior);
	    		vistaAnteriorID4=-1;
	    	}
	    	
			viendoGrafico=0;
    		viewflipper.setInAnimation(fade_in);
    		viewflipper.setOutAnimation(fade_out);
			viewflipper.showPrevious();			
		}
		else
		{
			super.onBackPressed();
		}
		
	}
	 	
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case INI:
            return new DatePickerDialog(this,
                        pDateSetListener,
                        anoIni, mesIni, diaIni);
        case FIN:
        	return new DatePickerDialog(this,
                    pDateSetListener,
                    anoFin, mesFin, diaFin);
        }
        return null;
    }
	
}
