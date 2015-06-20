package com.cortex.dane.masymenos.nivel2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cortex.dane.masymenos.R;
import com.cortex.dane.masymenos.utils.Utils;

public class Petalo extends LinearLayout {

	private Operacion operacion;
	private TextView cuenta;
	private boolean seleccionadoAnteriormente = false;
	
	public Petalo(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View petalo = inflater.inflate(R.layout.petalo, this, true);
	    cuenta = (TextView)petalo.findViewById(R.id.operacion);
	}
	
	public boolean sosElCorrecto() {
		boolean aux = isSeleccionadoAnteriormente();
		setSeleccionadoAnteriormente( operacion.esCorrecta );
		return operacion.esCorrecta && !aux;
	}
	
	public void nuevoEjercicio(Operacion p_operacion){
		operacion = p_operacion;
		setSeleccionadoAnteriormente(false);
	    cuenta.setText(operacion.toString());
	}

	public void visibilizate() {
		Utils.fade(this, 0, 1, 0);
	}
	
	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public boolean isSeleccionadoAnteriormente() {
		return seleccionadoAnteriormente;
	}

	public void setSeleccionadoAnteriormente(boolean seleccionadoAnteriormente) {
		this.seleccionadoAnteriormente = seleccionadoAnteriormente;
	}

	public TextView getCuenta() {
		return cuenta;
	}

	public void setCuenta(TextView cuenta) {
		this.cuenta = cuenta;
	}
}
