package com.cortex.dane.masymenos.nivel1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.utils.DaMagicNumber;

public class Imagen extends Circulo {
	
	private int src;
	private Bitmap bitmap;
	private Punto centroRelativo;
	private Punto posicionEnLaPantalla;

	public Imagen(int pSrc) {
		
		this.setSrc(pSrc);
		
		BitmapFactory.Options o = new BitmapFactory.Options();
        o.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
        setBitmap(BitmapFactory.decodeResource(Recursos.getResources(), src, o));
        
        Float w = (float) bitmap.getWidth();
        Float h = (float) bitmap.getHeight();
        
        // Centro relativo a la imagen
        setCentroRelativo(new Punto(w/2,h/2));
        setRadius(w > h ? w/2 * 1.1f : h/2 * 1.1f);
	}
	
	public void setCentroAbsoluto(Punto point){
		// Centro relativo al bitmap
		setCentro(new Punto(point.getPosX() - getCentroRelativo().getPosX(),point.getPosY() - getCentroRelativo().getPosY()));
	}
	
	private void dibujateTorcida(Canvas canvas) {
		
		Matrix matrix = new Matrix();
	
		matrix.reset();
		matrix.postTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2); // Centers image
		
		//Genera un numero aleatorio para el angulo
		int angle = DaMagicNumber.randInt(-45, 45);
		
		matrix.postRotate(angle);
		matrix.postTranslate(posicionEnLaPantalla.getPosX(), posicionEnLaPantalla.getPosY());
		
		canvas.drawBitmap(bitmap, matrix, null);
		
	}
	
	public void dibujateOtraVezTorcida(Canvas canvas) {
		if(posicionEnLaPantalla != null)
			this.dibujateTorcida(canvas);
	}
	
	public void dibujateEnUnPuntoTorcida(Canvas canvas,Punto point) {
		posicionEnLaPantalla = point;
		this.dibujateTorcida(canvas);
	}
	
	public void dibujateDerecho(Canvas canvas, Punto point) {
		this.setCentroAbsoluto(point);
		canvas.drawBitmap(bitmap,this.getCentro().getPosX(),this.getCentro().getPosY(), null);
	} 

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Punto getCentroRelativo() {
		return centroRelativo;
	}

	public void setCentroRelativo(Punto centroRelativo) {
		this.centroRelativo = centroRelativo;
	}

	public Punto getPosicionEnLaPantalla() {
		return posicionEnLaPantalla;
	}

	public void setPosicionEnLaPantalla(Punto posicionEnLaPantalla) {
		this.posicionEnLaPantalla = posicionEnLaPantalla;
	}

}
