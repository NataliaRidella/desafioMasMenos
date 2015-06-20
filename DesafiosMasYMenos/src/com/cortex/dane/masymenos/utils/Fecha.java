package com.cortex.dane.masymenos.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha {
	public long fechaEnMilisegundos(){
		GregorianCalendar temp = new GregorianCalendar();
		long fechaMilli = new  GregorianCalendar(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH )).getTimeInMillis();
		return fechaMilli;
	}
	
	public String fechaMiliaGregoriano(long fechaMili){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = dateFormat.format(new Date(fechaMili));
		return stringDate;
	}

}
