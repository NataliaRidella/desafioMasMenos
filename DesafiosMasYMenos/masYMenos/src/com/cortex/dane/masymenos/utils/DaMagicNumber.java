package com.cortex.dane.masymenos.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DaMagicNumber {
	
	
	/**
	 * Retorna un numero aleatorio entre min y max 
	 * @return
	 */
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	/**
	 * Retorna un array con n valores enteros aleatorios entre min y max 
	 * @return
	 */
	public static Integer[] getDifferentRandomInt(int min, int max, int n) {
	    List<Integer> list = new ArrayList<Integer>();
	    for(int i = min; i <= max; i++){
	        list.add(i);
	    }

	    Collections.shuffle(list);
	    Integer[] randomArray = list.subList(0, n).toArray(new Integer[n]);

	    return randomArray;
	}
	
	public static void shuffleArray(int[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
}
