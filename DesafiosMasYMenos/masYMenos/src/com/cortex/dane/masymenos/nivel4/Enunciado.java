package com.cortex.dane.masymenos.nivel4;

import java.util.ArrayList;

public class Enunciado {
	
	private ArrayList<Imagen> imagenes;
	private String enunciadoMas;
	private String enunciadoMenos;
	private String singular;
	private String plural;
	
	public Enunciado(ArrayList<Imagen> imagenes, String enunciadoMas,String enunciadoMenos, String singular, String plural) {
		super();
		this.imagenes = imagenes;
		this.enunciadoMas = enunciadoMas;
		this.enunciadoMenos = enunciadoMenos;
		this.singular = singular;
		this.plural = plural;
	}

	public String getEnunciadoMas() {
		return enunciadoMas;
	}

	public void setEnunciadoMas(String enunciadoMas) {
		this.enunciadoMas = enunciadoMas;
	}

	public String getEnunciadoMenos() {
		return enunciadoMenos;
	}

	public void setEnunciadoMenos(String enunciadoMenos) {
		this.enunciadoMenos = enunciadoMenos;
	}

	public ArrayList<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(ArrayList<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String getSingular() {
		return singular;
	}

	public void setSingular(String singular) {
		this.singular = singular;
	}

	public String getPlural() {
		return plural;
	}

	public void setPlural(String plural) {
		this.plural = plural;
	}

}
