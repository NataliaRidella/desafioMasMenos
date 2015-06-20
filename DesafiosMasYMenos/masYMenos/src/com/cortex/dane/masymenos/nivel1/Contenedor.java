package com.cortex.dane.masymenos.nivel1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.cortex.dane.masymenos.EnumTipoPantalla;
import com.cortex.dane.masymenos.Nivel;
import com.cortex.dane.masymenos.Recursos;

@SuppressLint("UseValueOf")
public class Contenedor extends Circulo
{
	private int cantidad;
	private Canvas canvas;
	private ArrayList<Imagen> imagenes = new ArrayList<Imagen>();
	private Fruta fruta;
	private Float finalPosX;
	private Float finalPosY;
	private ImageView imageviewArbol;
	private boolean seleccionadoAnteriormente = false;
	private Nivel nivel1;
	
	public Contenedor(Canvas canvas, int cantidad, Fruta fruta, ImageView arbol, Nivel p_nivel1)
	{
		// El canvas es la parte de la pantalla que esta disponible para dibujar, el arbol.getHeight() toma tambiŽn el ‡rea del action bar
        int[] locations = new int[2];
        arbol.getLocationOnScreen(locations);
        int puntoXArbol  = locations[0];
        int puntoYArbol = locations[1];   
        float canvasHeight = canvas.getHeight();

        nivel1 = p_nivel1;
        Point screen = nivel1.screenSize();
        
        if(Recursos.getTamanioDePantalla().equals(EnumTipoPantalla.LARGE)&& Recursos.getResources().getDisplayMetrics().densityDpi == DisplayMetrics.DENSITY_HIGH)
        	setRadius(arbol.getWidth() * .4f);
        else
        	setRadius(arbol.getWidth() * .48f);
        	
        float puntoYCirculo = arbol.getHeight() * 0.38f;
        float puntoXCirculo = arbol.getWidth() * 0.5f;
		
		setCentro(new Punto(puntoXCirculo + puntoXArbol,(puntoYCirculo + puntoYArbol) - (screen.y - canvasHeight)));
		setCantidad(cantidad);
		setCanvas(canvas);
		setFruta(fruta);
		setImageView(arbol);
		setSeleccionadoAnteriormente(false);
	}
	
	public void generate(){
		//El nombre del método esta en español, no en inglish
		
		this.fillSelf();
		//Para Debug: dibuja el circulo que contendr‡ las frutas
//	     Paint paint = new Paint();
//	     paint.setColor(Color.parseColor("#b2ffffe0"));
//	     paint.setStyle(Paint.Style.FILL);
//		 canvas.drawCircle(this.getCentro().getPosX(),this.getCentro().getPosY(), this.getRadius(), paint);
	}
	
	//rellena el contenedor con imágenes
	public void fillSelf()
	{
        for(int i = 0;i<cantidad;i++)
        {
        	Imagen img = new Imagen(fruta.getImgSrc());
        	// Genero un punto dentro del contenedor y le seteo a la imagen el centro relativo al bitmap
    		Punto point = generatePoint(this.getRadius() - img.getRadius());
    		img.setCentroAbsoluto(point);
    		
    		int j = 0;
        	while(existeColisionEntreLasImagenes(img))
        	{
        		j++;
            	// Genero un punto dentro del contenedor y le seteo a la imagen el centro relativo al bitmap
        		point = generatePoint(this.getRadius() - img.getRadius());
        		img.setCentroAbsoluto(point);
        		if(j > 1000)
        		{
        			nivel1.reset();
        			break;
        		}
        	}
        	
    		getImagenes().add(img);
    		img.dibujateEnUnPuntoTorcida(this.canvas, point);
        }
	}

	private boolean existeColisionEntreLasImagenes(Imagen newImg){
		
		for(Imagen img : imagenes)
		{
			// Distancias cuadradas usando el teorema de pitagoras
			Punto delta = new Punto(Math.abs((img.getCentro().getPosX() - newImg.getCentro().getPosX())),Math.abs((img.getCentro().getPosY() - newImg.getCentro().getPosY())));
			double d = Math.sqrt(delta.getPosX() * delta.getPosX() + delta.getPosY() * delta.getPosY()); 
			if(d < (img.getRadius() + newImg.getRadius()))
				return true;
		}
		return false;
	}
	
	// Genera puntos dentro de la circunferencia
	private Punto generatePoint(float radius){    	
    		
        Float x = new Float((Math.random() * 2 * radius - radius));
		double ylim = Math.sqrt(radius * radius - x.doubleValue() * x.doubleValue());
        Float y = new Float(Math.random() * 2 * ylim - ylim);
        x += getCentro().getPosX();
        y += getCentro().getPosY();
        
        Punto point = new Punto(x, y);
        
        return point;
    }
	
	public void correcto()
	{
		 
		for(Imagen img : imagenes)
			img.dibujateOtraVezTorcida(canvas);
	}
	
	/*
	 * 
	 * Getters and setters
	 * 
	 * 
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public ArrayList<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(ArrayList<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public Fruta getFruta() {
		return fruta;
	}

	public void setFruta(Fruta fruta) {
		this.fruta = fruta;
	}
	
	public void setImageView(ImageView imageviewArbol) {
		this.imageviewArbol = imageviewArbol;
	}
	
	public ImageView getImageView() {
		return imageviewArbol;
	}

	public Float getFinalPosX() {
		return finalPosX;
	}

	public void setFinalPosX(Float finalPosX) {
		this.finalPosX = finalPosX;
	}

	public Float getFinalPosY() {
		return finalPosY;
	}

	public void setFinalPosY(Float finalPosY) {
		this.finalPosY = finalPosY;
	}

	public boolean isSeleccionadoAnteriormente() {
		return seleccionadoAnteriormente;
	}

	public void setSeleccionadoAnteriormente(boolean seleccionadoAnteriormente) {
		this.seleccionadoAnteriormente = seleccionadoAnteriormente;
	}
}
