package com.cortex.dane.masymenos.nivel2;

import java.util.ArrayList;

import com.cortex.dane.masymenos.utils.DaMagicNumber;

public class Resta extends Operacion
{
	public Operacion generarOperacionCorrecta(int resultado, ArrayList<Operacion> operaciones) 
	{
		super.generarOperacionCorrecta(resultado, operaciones);
		valor1 = DaMagicNumber.randInt(resultado + 1, 9);
		valor2 = valor1 - resultado;
		
		while(this.verificarExistencia(operaciones))
		{
			valor1 = DaMagicNumber.randInt(resultado + 1, 9);
			valor2 = valor1 - resultado;
		}
		
		return this;
	}

	public Operacion generarOperacionIncorrecta(int resultado, ArrayList<Operacion> operaciones) {
		
		super.generarOperacionIncorrecta(resultado, operaciones);
		valor1 = DaMagicNumber.randInt(2, 9);
		valor2 = DaMagicNumber.randInt(2, 9);
		
		while(valor1 - valor2 == resultado || valor1 - valor2 < 1)
		{
			valor1 = DaMagicNumber.randInt(2, 9);
			valor2 = DaMagicNumber.randInt(2, 9);
		}
		
		return this;
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(valor1) + " - " + Integer.toString(valor2);
	}

}