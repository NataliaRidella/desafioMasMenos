package com.cortex.dane.masymenos.nivel1;


public abstract class Circulo {
	
	// El centro siempre es relativo al bitmap de la pantalla
	private Punto centro;
	private float radius;
	
    //Metodo que me indica si la coordenada por parámetro se encuentra dentro del circulo
    public boolean inCircle(float x, float y) {
        double dx = Math.pow(x - this.getCentro().getPosX(), 2);
        double dy = Math.pow(y - this.getCentro().getPosY(), 2);

        if ((dx + dy) < Math.pow(this.getRadius(), 2)) {
            return true;
        } else {
            return false;
        }
    }
	
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Punto getCentro() {
		return centro;
	}

	public void setCentro(Punto centro) {
		this.centro = centro;
	}
}
