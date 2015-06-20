package com.cortex.dane.masymenos.nivel1;

public class Fruta {
	
	private int imgSrc;
	private String name;
	
	public Fruta(int pImgSrc, String pName){
		this.setImgSrc(pImgSrc);
		this.setName(pName);
	}
	 
	public int getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(int imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
