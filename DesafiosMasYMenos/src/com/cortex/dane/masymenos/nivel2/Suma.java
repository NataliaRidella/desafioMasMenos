package com.cortex.dane.masymenos.nivel2;

import java.util.ArrayList;

import com.cortex.dane.masymenos.utils.DaMagicNumber;

public class Suma extends Operacion
{

	@Override
	public Operacion generarOperacionCorrecta(int resultado, ArrayList<Operacion> operaciones) 
	{
		super.generarOperacionCorrecta(resultado, operaciones);
		valor1 = DaMagicNumber.randInt(1, resultado - 1);
		valor2 = resultado - valor1;
		
		while(this.verificarExistencia(operaciones))
		{
			valor1 = DaMagicNumber.randInt(1, resultado - 1);
			valor2 = resultado - valor1;
		}
		return this;
	}

	@Override
	public Operacion generarOperacionIncorrecta(int resultado, ArrayList<Operacion> operaciones) {
		
		super.generarOperacionIncorrecta(resultado, operaciones);
		valor1 = DaMagicNumber.randInt(1, 8);
		valor2 = DaMagicNumber.randInt(1, 9 - valor1);
		
		while(valor1 + valor2 == resultado || valor1 + valor2 > 9)
		{
			valor1 = DaMagicNumber.randInt(1, 8);
			valor2 = DaMagicNumber.randInt(1, 8);
		}
		return this;
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(valor1) + " + " + Integer.toString(valor2);
	}

}
