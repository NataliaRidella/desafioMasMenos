package com.cortex.dane.masymenos.nivel4;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.EnumTipoPantalla;
import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.utils.Utils;

public class Opcion extends LinearLayout {
	
	private Float offsetX;
	private Float offsetY;
	protected TextView opcionTexto;
	protected ImageView imagen;
	protected String hexaColor;
	protected RelativeLayout juego;
	protected boolean seleccionadoAnteriormente = false;
	
    public Opcion(Context context) {
        super(context);
      }

      public Opcion(Context context, AttributeSet attrs) {
          super(context, attrs);
          TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OpcionTextView, 0, 0);

		   try {
		       offsetX = a.getFloat(R.styleable.OpcionTextView_offsetX, 0f);
		       offsetY = a.getFloat(R.styleable.OpcionTextView_offsetY, 0f);
		   } finally {
		       a.recycle();
		   }
		   
		   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   View opcion = inflater.inflate(R.layout.opcion, this, true);
		   imagen = (ImageView)opcion.findViewById(R.id.imagen);
		   opcionTexto = (TextView)opcion.findViewById(R.id.opcionTexto);
		   this.setOnTouchListener(new OpcionListener());
      }
      
      public void acomodate(View parent){
    	  Utils.acomodarView(parent, this, offsetX == 0f ? null : offsetX, 
    			  						   offsetY == 0f ? null : offsetY );
      }

      protected void onDraw (Canvas canvas) {
          super.onDraw(canvas);
      }

    public void nuevoEjercicio(String pValor, View parent, Imagen imagen, RelativeLayout juego) {
    	
    	this.opcionTexto.setText(pValor);
        this.imagen.setImageResource(imagen.getSrc());
        this.acomodate(parent);
        this.hexaColor = imagen.getColor();
        this.juego = juego;
        
        this.escalarImagen();
    	
		Utils.fade(this, 0, 1, 500);
		
		seleccionadoAnteriormente = false;
    }
    
    public void escalarImagen()
    {
    	if(Recursos.getTamanioDePantalla().equals(EnumTipoPantalla.NORMAL))
    	{    	
	    	final ScaleAnimation zoom = new ScaleAnimation(0, 0.7f, 0, 0.7f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			zoom.setStartOffset(0);
			zoom.setDuration(0);
			zoom.setFillAfter(true);
			
			this.imagen.startAnimation(zoom);
    	}
    }
      
  	public void visibilizate() {
		Utils.fade(this, 0, 1, 0);
	}
  	
	public Float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(Float offsetX) {
		this.offsetX = offsetX;
	}

	public Float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(Float offsetY) {
		this.offsetY = offsetY;
	}
    
	public boolean isSeleccionadoAnteriormente() {
		return seleccionadoAnteriormente;
	}

	public void setSeleccionadoAnteriormente(boolean seleccionadoAnteriormente) {
		this.seleccionadoAnteriormente = seleccionadoAnteriormente;
	}

	public ImageView getImagen() {
		return imagen;
	}

	public void setImagen(ImageView imagen) {
		this.imagen = imagen;
	}

	public TextView getOpcionTexto() {
		return opcionTexto;
	}

	public void setOpcionTexto(TextView opcionTexto) {
		this.opcionTexto = opcionTexto;
	}

	public String getHexaColor() {
		return hexaColor;
	}

	public void setHexaColor(String hexaColor) {
		this.hexaColor = hexaColor;
	}

	private class OpcionListener implements View.OnTouchListener  {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Opcion opcion = (Opcion)v;
			switch (event.getAction())
	    	{
	    		case MotionEvent.ACTION_DOWN:
	    		{
	    			if(juego.getTag().equals(Utils.VISIBLE) && !opcion.isSeleccionadoAnteriormente()) 
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
      

}
