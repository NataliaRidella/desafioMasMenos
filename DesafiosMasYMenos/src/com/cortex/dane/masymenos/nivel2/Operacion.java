package com.cortex.dane.masymenos.nivel2;

import java.util.ArrayList;

public abstract class Operacion {
	
	protected int valor1;
	protected int valor2;
	protected boolean esCorrecta;
	
	protected Operacion generarOperacionCorrecta(int resultado, ArrayList<Operacion> operaciones){
		esCorrecta = true;
		return this;
	}
	
	protected Operacion generarOperacionIncorrecta(int resultado, ArrayList<Operacion> operaciones){
		esCorrecta = false;
		return this;
	}

	protected boolean verificarExistencia(ArrayList<Operacion> operaciones)
	{
		for (Operacion operacion : operaciones) 
		{
			if(operacion.valor1 == this.valor1 && operacion.valor2 == this.valor2)
			{
				return true;
			}
		}		
		return false;
	}
}
