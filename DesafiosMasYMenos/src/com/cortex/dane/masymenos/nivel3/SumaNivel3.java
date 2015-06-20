package com.cortex.dane.masymenos.nivel3;

import java.util.ArrayList;
import java.util.Collections;

import com.cortex.dane.masymenos.utils.DaMagicNumber;

public class SumaNivel3 extends OperacionNivel3
{

	public void generarValores(int MAX, boolean incluyeCero)
	{
		this.MAX = MAX;
		
		int decenaMAX = MAX/10;
		
		valor1 = 0;
		valor2 = 0;
		
		if(incluyeCero)
		{
			theRapidMethod(decenaMAX);
		}
		else
		{	
			while(valor1 == 0 || valor2 == 0)
			{
				theRapidMethod(decenaMAX);
			}
		}
		resultadoCorrecto = valor1 + valor2;
		
	}
	
	public void theRapidMethod(int decenaMAX){
		
		int decena1 = DaMagicNumber.randInt(0, decenaMAX);
		int decena2 = DaMagicNumber.randInt(0, decenaMAX - decena1);
		
		int unidad1 = 0;
		int unidad2 = 0;
		

		unidad1 = DaMagicNumber.randInt(0, 9);
		unidad2 = DaMagicNumber.randInt(0, 9 - unidad1);
		
		ArrayList<Integer> decenas = new ArrayList<Integer>();
		decenas.add(decena1);
		decenas.add(decena2);
		Collections.shuffle(decenas);
		
		ArrayList<Integer> unidades = new ArrayList<Integer>();
		unidades.add(unidad1);
		unidades.add(unidad2);
		Collections.shuffle(unidades);
		
		// TODO PRUEBA
		valor1 = decenas.get(0)*10 + unidades.get(0);
		valor2 = decenas.get(1)*10 + unidades.get(1);	
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(valor1) + " + " + Integer.toString(valor2);
	}

}
