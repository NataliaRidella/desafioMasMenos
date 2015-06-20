package com.cortex.dane.masymenos.nivel4;

import java.util.ArrayList;

public class Conmutativo implements Resultado{
	
	private ArrayList<String> resultados = new ArrayList<String>();
	
	public boolean esCorrecto(Object res) {
		
		if(resultados.contains(res))
		{
			resultados.remove(res);
			return true;
		}
		else
			return false;
	}
	
	public void ingresarResultado(Object resultado) {
		resultados.add(resultado.toString());
	}

}
