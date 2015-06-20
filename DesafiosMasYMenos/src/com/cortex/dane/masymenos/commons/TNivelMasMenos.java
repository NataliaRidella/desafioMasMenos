package com.cortex.dane.masymenos.commons;

import com.cortex.dane.masymenos.utils.DaMagicNumber;

public class TNivelMasMenos extends TipoNivel {

	@Override
	public boolean definirOperacion() {
		return DaMagicNumber.randInt(0, 1) == 0;
	}

}
