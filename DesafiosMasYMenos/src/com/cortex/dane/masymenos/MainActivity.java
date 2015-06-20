package com.cortex.dane.masymenos;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cortex.dane.masymenos.about.AboutActivity;
import com.cortex.dane.masymenos.nivel4.Nivel4DataSource;
import com.cortex.dane.masymenos.sqlitereporte.Reporte;

public class MainActivity extends Activity {
	
	private TextView textViewNuevo, textViewReportes, textViewSalir;
	MediaPlayer mp;
	
	//SQLite
	private Nivel4DataSource datasource;
	
	    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Usar el layout definido por el xml
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
         
        //Setea el singleton para poder ser accedido de otras clases
        Recursos.setResources(this.getResources());
        
        //Cargar los valores seteados en el xml
    	Typeface font = Typeface.createFromAsset(getAssets(), "sf_arch_rival.ttf");

    	textViewNuevo=(TextView)findViewById(R.id.juegonuevo);
    	textViewReportes=(TextView)findViewById(R.id.reportes);
    	textViewSalir=(TextView)findViewById(R.id.salir);
    	
    	textViewNuevo.setTypeface(font);
    	textViewReportes.setTypeface(font);
    	textViewSalir.setTypeface(font);
    	
    	textViewNuevo.setOnTouchListener(new MenuListener(this, GameSelection.class));
    	textViewReportes.setOnTouchListener(new MenuListener(this, Reporte.class));
    	textViewSalir.setOnTouchListener(new MenuListener(this, AboutActivity.class));
    	
    	//SQLite       
        datasource = new Nivel4DataSource(this);
        datasource.open();
//        datasource.createTable(Nivel4SQLiteHelper.TABLE_NIVEL);
        datasource.llenadoDefault();
        
        //Trabar orientacion de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	
    	//TODO BORRAR: Solo para pruebas
//    	  int density= getResources().getDisplayMetrics().densityDpi;
//
//    	  switch(density)
//    	  {
//    	  case DisplayMetrics.DENSITY_LOW:
//    	     Toast.makeText(this, "LDPI", Toast.LENGTH_LONG).show();
//    	      break;
//    	  case DisplayMetrics.DENSITY_MEDIUM:
//    	       Toast.makeText(this, "MDPI", Toast.LENGTH_LONG).show();
//    	      break;
//    	  case DisplayMetrics.DENSITY_HIGH:
//    	      Toast.makeText(this, "HDPI", Toast.LENGTH_LONG).show();
//    	      break;
//    	  case DisplayMetrics.DENSITY_XHIGH:
//    	       Toast.makeText(this, "XHDPI", Toast.LENGTH_LONG).show();
//    	      break;
//    	  }
//    	  
//		  //Determine screen size
//		if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
//		    Toast.makeText(this, "Large screen",Toast.LENGTH_LONG).show();
//		
//		}
//		else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
//		    Toast.makeText(this, "Normal sized screen" , Toast.LENGTH_LONG).show();
//		
//		} 
//		else if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
//		    Toast.makeText(this, "Small sized screen" , Toast.LENGTH_LONG).show();
//		}
//		else {
//		    Toast.makeText(this, "Screen size is neither large, normal or small" , Toast.LENGTH_LONG).show();
//		}
//		
//    	Display display = getWindowManager().getDefaultDisplay();
//    	Point size = new Point();
//    	display.getSize(size);
//
//	    Toast.makeText(this, size.x + "px " + size.y + "px ", Toast.LENGTH_LONG).show();
	    


    }
    
	@Override
	protected void onDestroy() {
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
	
	private class MenuListener implements View.OnTouchListener {
		
		protected Activity menu;
		private Class<?> intentClass; 

		public MenuListener(Activity p_menu, Class<?> p_intentClass) {
			intentClass = p_intentClass;
			menu = p_menu;
		}
		
		public void doWhatYouShould() {
   			Intent myIntent = new Intent(menu, intentClass);
   	        menu.startActivity(myIntent);
		}
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
		
			switch (event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    		{
	    			((TextView) v).setTextColor(Color.parseColor("#fcd200"));
	    			break;
	    		} 
	    		case MotionEvent.ACTION_UP:
	    		{
	    			((TextView) v).setTextColor(Color.WHITE);
	    			doWhatYouShould();
	    			break;
	    		}
	    	}		    			
			return true;
		}
	} 
}
