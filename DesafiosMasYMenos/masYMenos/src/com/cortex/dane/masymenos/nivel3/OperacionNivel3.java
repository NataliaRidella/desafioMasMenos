package com.cortex.dane.masymenos.nivel3;

import java.util.ArrayList;
import java.util.Collections;

import com.cortex.dane.masymenos.utils.DaMagicNumber;

public abstract class OperacionNivel3 {
	
	protected Integer valor1;
	protected Integer valor2;
	protected Integer resultadoCorrecto;
	protected Integer MAX;
	
	public abstract void generarValores(int MAX, boolean incluyeCero);
	
	public Integer getResultadoCorrecto() 
	{
		return resultadoCorrecto;
	}
	
	public Integer getValor1() {
		return valor1;
	}

	public Integer getValor2() {
		return valor2;
	}
	
	//Retorna un array con 3 resultados posibles. Uno de ellos será el correcto. Otro cercano y otro aleatorio.
	public ArrayList<Integer> getResultadosPosiblesNivel3()
	{
		ArrayList<Integer> resultados = new ArrayList<Integer>();
		
		resultados.add(resultadoCorrecto);
		
		//Obtengo resultado cercano al correcto
		Integer cercano = generarResultadoCercano();
		resultados.add(cercano);
		
		//Compruebo que el aleatorio generado sea diferente al cercano
		Integer aleatorio = generarResultadoAleatorio();
		while (aleatorio == cercano)
		{
			aleatorio = generarResultadoAleatorio();
		}
		resultados.add(aleatorio);

		//El orden es al azar.
		Collections.shuffle(resultados);
		
		return resultados;
	}
	
	//Retorna un array con N resultados posibles. Uno de ellos será el correcto.
	public ArrayList<Integer> getResultadosPosiblesNivel4(int n)
	{
		ArrayList<Integer> resultados = new ArrayList<Integer>();
		
		resultados.add(resultadoCorrecto);
		resultados.add(generarResultadoCercano());
		for(int i = 1; i <= n-2; i++)
		{
			resultados.add(generarResultadoAleatorio());
		}
		
		//El orden es al azar.
		Collections.shuffle(resultados);
		
		return resultados;
	}
	
	public ArrayList<String> getResultadosPosiblesEnTesto(int n)
	{
		ArrayList<String> resultados = new ArrayList<String>();
		
		for(Integer resultado : this.getResultadosPosiblesNivel4(n)) {
			resultados.add(resultado.toString());
		}
		
		return resultados;
	}
	
	public Integer generarResultadoAleatorio()
	{
		int res = DaMagicNumber.randInt(0, MAX-1);
		
		while(res == resultadoCorrecto)
			res = DaMagicNumber.randInt(0, MAX-1);
		
		return res;
	}

	public Integer generarResultadoCercano()
	{
		int decenaCorrecta = (int) resultadoCorrecto/10;
		
		int unidad = DaMagicNumber.randInt(0, 9);
		int res = decenaCorrecta*10 + unidad;
		
		while(res == resultadoCorrecto)
		{
			unidad = DaMagicNumber.randInt(0, 9);
			res = decenaCorrecta*10 + unidad;
		}
		
		return res;
	}
	
}
