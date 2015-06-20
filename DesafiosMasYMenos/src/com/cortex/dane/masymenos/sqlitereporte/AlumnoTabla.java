package com.cortex.dane.masymenos.sqlitereporte;

public class AlumnoTabla {
	  private long id;
	  private long fecha;
	  private int juego;
	  private int correctos;
	  private int incorrectos;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public long getFecha() {
	    return fecha;
	  }

	  public void setFecha(long fecha) {
	    this.fecha = fecha;
	  }
	  
	  public int getJuego() {
		    return juego;
	  }

	  public void setJuego(int juego) {
		    this.juego = juego;
	  }
	  
	  public int getCorrectos() {
		    return correctos;
	  }

	  public void setCorrectos(int correctos) {
		    this.correctos = correctos;
	  }
	  
	  public int getIncorrectos() {
		    return incorrectos;
	  }

	  public void setIncorrectos(int incorrectos) {
		    this.incorrectos = incorrectos;
	  }
	  
} 
