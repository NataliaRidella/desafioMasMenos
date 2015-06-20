package com.cortex.dane.masymenos.nivel4;

public class NoConmutativo implements Resultado {
	
	protected String resultado;
	
	protected NoConmutativo(Object pResultado) {
		resultado = pResultado.toString();
	}
	
	public boolean esCorrecto(Object res) {
		return res.equals(resultado);
	}
	
	public Object getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		this.resultado = resultado.toString();
	}
	
}
