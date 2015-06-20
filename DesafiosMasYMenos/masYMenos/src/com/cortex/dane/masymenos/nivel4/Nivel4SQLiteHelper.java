package com.cortex.dane.masymenos.nivel4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Nivel4SQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_NIVEL = "tablaNivel4";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_CONSIGNAMAS = "consignaMas";
  public static final String COLUMN_CONSIGNAMENOS = "consignaMenos";
  public static final String COLUMN_IMAGEN1 = "imagen1";
  public static final String COLUMN_COLOR1 = "color1";
  public static final String COLUMN_IMAGEN2 = "imagen2";
  public static final String COLUMN_COLOR2 = "color2";
  public static final String COLUMN_IMAGEN3 = "imagen3";
  public static final String COLUMN_COLOR3 = "color3";
  public static final String COLUMN_SINGULAR = "singular";
  public static final String COLUMN_PLURAL = "plural";

  private static final String DATABASE_NAME = "masymenosdbnivel4.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  public static final String DATABASE_CREATE = "create table " + TABLE_NIVEL + 
		  										"(" + 
		  											COLUMN_ID  + " integer primary key autoincrement, " +
		  											COLUMN_CONSIGNAMAS + " text, " + 
		  											COLUMN_CONSIGNAMENOS + " text, " + 
		  											COLUMN_IMAGEN1 + " text, " + 
		  											COLUMN_COLOR1 + " text, " +
		  											COLUMN_IMAGEN2 + " text, " +
		  											COLUMN_COLOR2 + " text, " +
		  											COLUMN_IMAGEN3 + " text, " +
		  											COLUMN_COLOR3 + " text," +
		  											COLUMN_SINGULAR + " text," +
		  											COLUMN_PLURAL + " text" +
		  										");";

  public Nivel4SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    Log.w(Nivel4SQLiteHelper.class.getName(),
//        "Upgrading database from version " + oldVersion + " to "
//            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NIVEL);
    onCreate(db);
  }

} 