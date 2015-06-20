package com.cortex.dane.masymenos.sqlitereporte;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.cortex.dane.masymenos.utils.DaMagicNumber;
import com.cortex.dane.masymenos.utils.Fecha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AlumnoDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
		  						  MySQLiteHelper.COLUMN_FECHA,
		  						  MySQLiteHelper.COLUMN_JUEGO,
		  						MySQLiteHelper.COLUMN_CORRECTOS,
		  						  MySQLiteHelper.COLUMN_INCORRECTOS };

  public AlumnoDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public AlumnoTabla createAlumno(long fecha, int juego, int correctos, int incorrectos) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_FECHA, fecha);
    values.put(MySQLiteHelper.COLUMN_JUEGO, juego);
    values.put(MySQLiteHelper.COLUMN_CORRECTOS, correctos);
    values.put(MySQLiteHelper.COLUMN_INCORRECTOS, incorrectos);
    long insertId = database.insert(MySQLiteHelper.TABLE_ALUMNO, null,values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_ALUMNO, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    AlumnoTabla newAlumno = cursorToAlumno(cursor);
    cursor.close();
    return newAlumno;
  }
  
  public void deleteAlumno(AlumnoTabla alumno) {
    long id = alumno.getId();
    System.out.println("Borrado entrada con id: " + id);
    database.delete(MySQLiteHelper.TABLE_ALUMNO, MySQLiteHelper.COLUMN_ID + " = " + id, null);
  }
  
  public List<AlumnoTabla> getAllAlumno() {
	    List<AlumnoTabla> alumnos = new ArrayList<AlumnoTabla>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_ALUMNO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      AlumnoTabla comment = cursorToAlumno(cursor);
	      alumnos.add(comment);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return alumnos;
  }

  public AlumnoTabla cursorToAlumno(Cursor cursor) {
	AlumnoTabla alumno = new AlumnoTabla();
	alumno.setId(cursor.getLong(0));
	alumno.setFecha(cursor.getLong(1));
	alumno.setJuego(cursor.getInt(2));
	alumno.setCorrectos(cursor.getInt(3));
	alumno.setIncorrectos(cursor.getInt(4));
    return alumno;
  }
  
  public Cursor readEntry() {

	  Cursor cursor = database.query(MySQLiteHelper.TABLE_ALUMNO, allColumns, null, null, null, null, null);

	  if (cursor != null) {
	   cursor.moveToFirst();
	  }
	  return cursor;
  }
  
  public Cursor buscarReporte(long fechaInicial, long fechaFinal, long nivel){
	  Cursor cursor;
	  if (nivel == 0){
		  cursor = database.rawQuery("SELECT fecha, sum(correctos), sum(incorrectos) FROM " + MySQLiteHelper.TABLE_ALUMNO + " WHERE fecha BETWEEN " + fechaInicial + " AND " + fechaFinal + " GROUP BY fecha", null);
	  }else{
		  cursor = database.rawQuery("SELECT fecha, sum(correctos), sum(incorrectos) FROM " + MySQLiteHelper.TABLE_ALUMNO + " WHERE juego=" + nivel + " AND fecha BETWEEN " + fechaInicial + " AND " + fechaFinal + " GROUP BY fecha", null);
	  }
	  cursor.moveToFirst();
	  return cursor;
  }
  
  public void cargarDummy(){ 		
	  int ran, ran2; 		
	  long fecha = new Fecha().fechaEnMilisegundos();
	  fecha-=5184000000L;
	  for(int i=1;i<61;i++){ 		
		  ran = DaMagicNumber.randInt(1, 10); 		
		  ran2 = DaMagicNumber.randInt(1, 10); 		
		  createAlumno(fecha, 1, ran, ran2); 		
		  fecha+=86400000; 		
	  } 		
}
  
  public void vaciarTabla(){
	  database.delete(MySQLiteHelper.TABLE_ALUMNO, null, null);
  }
}
