package com.cortex.dane.masymenos.nivel4;


public class Imagen {
	
	private String color;
	private int src;
	
	public Imagen(int src,String color) {
		super();
		this.color = color;
		this.src = src;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}

}
