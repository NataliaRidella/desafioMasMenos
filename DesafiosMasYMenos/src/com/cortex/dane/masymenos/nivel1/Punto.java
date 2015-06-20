package com.cortex.dane.masymenos.nivel1;

public class Punto {
	
	private Float posX;
	private Float posY;
	
	public Punto(){}
	
	public Punto(Float x, Float y)
	{
		this.setPosX(x);
		this.setPosY(y);
	}
	
	public Float getPosX() {
		return posX;
	}

	public void setPosX(Float posX) {
		this.posX = posX;
	}

	public Float getPosY() {
		return posY;
	}

	public void setPosY(Float posY) {
		this.posY = posY;
	}
}
