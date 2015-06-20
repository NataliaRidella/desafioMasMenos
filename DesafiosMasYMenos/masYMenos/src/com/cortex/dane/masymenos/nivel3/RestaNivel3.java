package com.cortex.dane.masymenos.nivel3;

import com.cortex.dane.masymenos.utils.DaMagicNumber;

public class RestaNivel3 extends OperacionNivel3
{

	public void generarValores(int MAX, boolean incluyeCero)
	{
		this.MAX = MAX;
		
		int decenaMAX = MAX/10;
		
		int decena1 = DaMagicNumber.randInt(0, decenaMAX);
		int decena2 = DaMagicNumber.randInt(0, decena1);
		
		int unidad1 = 0;
		int unidad2 = 0;
		
		if(incluyeCero)
		{
			unidad1 = DaMagicNumber.randInt(0, 9);
			unidad2 = DaMagicNumber.randInt(0, unidad1);
		}
		else
		{
			if(decena1 == 0)
			{
				unidad1 = DaMagicNumber.randInt(1, 9);
			}
			else
			{
				unidad1 = DaMagicNumber.randInt(0, 9);
			}
			
			if(decena2 == 0)
			{
				unidad2 = DaMagicNumber.randInt(1, unidad1);
			}
			else
			{
				unidad2 = DaMagicNumber.randInt(0, unidad1);
			}
		}

		//TODO PRUEBA
		valor1 = decena1*10 + unidad1;
		valor2 = decena2*10 + unidad2;
		
		resultadoCorrecto = valor1 - valor2;
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(valor1) + " - " + Integer.toString(valor2);
	}

}