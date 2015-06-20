package com.cortex.dane.masymenos;

public class Tabla {
	int id;
    int fecha;
    int puntos;
 
    // constructors
    public Tabla() {
    }
 
    public Tabla(int fecha, int puntos) {
        this.fecha = fecha;
        this.puntos = puntos;
    }
 
    public Tabla(int id, int fecha, int puntos) {
        this.id = id;
        this.fecha = fecha;
        this.puntos = puntos;
    }
 
    // setters
    public void setId(int id) {
        this.id = id;
    }
 
    public void setFecha(int fecha) {
    	this.fecha = fecha;
    }
 
    public void setPuntos(int puntos) {
    	this.puntos = puntos;
    }
 
    // getters
    public long getId() {
        return this.id;
    }
 
    public int getNote() {
        return this.fecha;
    }
 
    public int getStatus() {
        return this.puntos;
    }
}
