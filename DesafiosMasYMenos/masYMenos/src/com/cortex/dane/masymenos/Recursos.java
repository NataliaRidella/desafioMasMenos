package com.cortex.dane.masymenos;

import java.util.ArrayList;
import java.util.Collections;

import android.content.res.Configuration;
import android.content.res.Resources;

import com.cortex.dane.masymenos.nivel1.Fruta;

public class Recursos {
	
	public static final String a =  "\u00e1";
	public static final String e =  "\u00E9";
	public static final String i =  "\u00ED";
	public static final String o =  "\u00F3";
	public static final String u =  "\u00FA";
	public static final String n =  "\u00F1";
	public static final String signo =  "\u00BF";
	
	private static Resources resources;
	private static int arrayPos = -1;
	private static Fruta[] frutas = { 
									  new Fruta(R.drawable.nivel1_banana,"banana"),
									  new Fruta(R.drawable.nivel1_manzana,"manzana"),
									  new Fruta(R.drawable.nivel1_durazno,"durazno"),
									  new Fruta(R.drawable.nivel1_frutilla,"frutilla"), 
									  new Fruta(R.drawable.nivel1_pera,"pera"),
									  new Fruta(R.drawable.nivel1_naranja,"naranja")};
	
	
	public static void setResources(Resources recursos){
		if(getResources() == null)
			resources = recursos;
	}
	
	public static Resources getResources(){
		
		return resources;
	}
	
	public static EnumTipoPantalla getTamanioDePantalla() {
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)  
		    return EnumTipoPantalla.LARGE;
		else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)    
		    return EnumTipoPantalla.NORMAL;
		else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL)    
		    return EnumTipoPantalla.SMALL;
	    else 
	    	return EnumTipoPantalla.UNIDENTIFIED;
	}
	
	public static Fruta proximaFruta()
	{
		if(arrayPos == -1)
		{
			ArrayList<Fruta> auxList = new ArrayList<Fruta>();
			for(int i=0;i < frutas.length;i++)
				auxList.add(frutas[i]); 
			Collections.shuffle(auxList);
			frutas = auxList.toArray(new Fruta[frutas.length]);
			arrayPos = frutas.length-1;
		}
		Fruta aux = frutas[arrayPos]; 
		arrayPos--;
		return aux;
	}

	public String getA(){
		return a;
	}
	
	public String getE(){
		return e;
	}
	
	public String getI(){
		return i;
	}
	
	public String getO(){
		return o;
	}
	
	public String getU(){
		return u;
	}
	
	public String getSigno(){
		return signo;
	}

}
