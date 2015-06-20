package com.cortex.dane.masymenos.commons;

public abstract class TipoNivel {
	
	public abstract boolean definirOperacion();
	
	public static TipoNivel getTipoNivel(EnumTipoNivel t)
	{
		TipoNivel tNivel;
		
		switch(t)
		{
			case MAS: 		tNivel = new TNivelMas();
							break;
			
			case MENOS:		tNivel = new TNivelMenos();
							break;
			
			case MASYMENOS: tNivel = new TNivelMasMenos();
							break;
			
			default: 		tNivel = new TNivelMasMenos();
							break;
		}
		
		return tNivel;
	}
	

	
}
