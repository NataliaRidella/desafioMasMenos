package com.cortex.dane.masymenos.nivel4;

import java.util.ArrayList;

import com.cortex.dane.masymenos.Recursos;
import com.cortex.dane.masymenos.nivel4.Enunciado;
import com.cortex.dane.masymenos.utils.DaMagicNumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class Nivel4DataSource {
	
  //Recurso para acentos y signo
  Recursos recursos = new Recursos();

  // Database fields
  private SQLiteDatabase database;
  private Nivel4SQLiteHelper dbHelper;
  private String[] allColumns = { Nivel4SQLiteHelper.COLUMN_ID,
		  						  Nivel4SQLiteHelper.COLUMN_CONSIGNAMAS,
		  						  Nivel4SQLiteHelper.COLUMN_CONSIGNAMENOS,
		  						  Nivel4SQLiteHelper.COLUMN_IMAGEN1,
		  						  Nivel4SQLiteHelper.COLUMN_COLOR1,
		  						  Nivel4SQLiteHelper.COLUMN_IMAGEN2,
		  						  Nivel4SQLiteHelper.COLUMN_COLOR2,
		  						  Nivel4SQLiteHelper.COLUMN_IMAGEN3,
		  						  Nivel4SQLiteHelper.COLUMN_COLOR3,
		  						  Nivel4SQLiteHelper.COLUMN_SINGULAR,
		  						  Nivel4SQLiteHelper.COLUMN_PLURAL,
		  						  };
  
  Context c;

  public Nivel4DataSource(Context context) {
    dbHelper = new Nivel4SQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Nivel4Tabla createConsigna(String consignamas, String consignamenos, String imagen1, String color1, String imagen2, String color2, String imagen3, String color3, String singular, String plural) {
    ContentValues values = new ContentValues();
    values.put(Nivel4SQLiteHelper.COLUMN_CONSIGNAMAS, consignamas);
    values.put(Nivel4SQLiteHelper.COLUMN_CONSIGNAMENOS, consignamenos);
    values.put(Nivel4SQLiteHelper.COLUMN_IMAGEN1, imagen1);
    values.put(Nivel4SQLiteHelper.COLUMN_COLOR1, color1);
    values.put(Nivel4SQLiteHelper.COLUMN_IMAGEN2, imagen2);
    values.put(Nivel4SQLiteHelper.COLUMN_COLOR2, color2);
    values.put(Nivel4SQLiteHelper.COLUMN_IMAGEN3, imagen3);
    values.put(Nivel4SQLiteHelper.COLUMN_COLOR3, color3);
    values.put(Nivel4SQLiteHelper.COLUMN_SINGULAR, singular);
    values.put(Nivel4SQLiteHelper.COLUMN_PLURAL, plural);
    
    long insertId = database.insert(Nivel4SQLiteHelper.TABLE_NIVEL, null,values);
    
    Cursor cursor = database.query(Nivel4SQLiteHelper.TABLE_NIVEL,
    							   allColumns, 
    							   Nivel4SQLiteHelper.COLUMN_ID + " = " + insertId, 
    							   null,
    							   null,
    							   null,
    							   null);
    cursor.moveToFirst();
    Nivel4Tabla newConsigna = cursorToConsigna(cursor);
    cursor.close();
    return newConsigna;
  }
  
  public void deleteConsigna(Nivel4Tabla consigna) {
    long id = consigna.getId();
    System.out.println("Borrado entrada con id: " + id);
    database.delete(Nivel4SQLiteHelper.TABLE_NIVEL,
    				Nivel4SQLiteHelper.COLUMN_ID + " = " + id,
    				null);
  }

  public Nivel4Tabla cursorToConsigna(Cursor cursor) {
	  Nivel4Tabla consigna = new Nivel4Tabla();
	  consigna.setId(cursor.getLong(0));
	  consigna.setConsignaMas(cursor.getString(1));
	  consigna.setConsignaMenos(cursor.getString(2));
	  consigna.setImagen1(cursor.getString(3));
	  consigna.setColor1(cursor.getString(4));
	  consigna.setImagen2(cursor.getString(5));
	  consigna.setColor2(cursor.getString(6));
	  consigna.setImagen3(cursor.getString(7));
	  consigna.setColor3(cursor.getString(8));
	  consigna.setSingular(cursor.getString(9));
	  consigna.setPlural(cursor.getString(10));
	  return consigna;
  }
  
  public Cursor readEntry() {

	  Cursor cursor = database.query(Nivel4SQLiteHelper.TABLE_NIVEL,
			  						 allColumns,
			  						 null,
			  						 null,
			  						 null,
			  						 null,
			  						 null);

	  if (cursor != null) {
	   cursor.moveToFirst();
	  }
	  return cursor;
  }
  
  public void llenadoDefault(){
	  Cursor cursor;
	  cursor = readEntry();
	  if (cursor==null || cursor.getCount()==0){
		  createConsigna(	"Tengo dos canastas, una con # @ y otra con # @. " + recursos.getSigno() + "Cu"+recursos.getA()+"ntas manzanas tengo en total?", 
				  			"Tengo una canasta con # @ y le regalo a mi amigo #. " + recursos.getSigno() + "Cu"+recursos.getA()+"ntas manzanas me quedan?",
				  			"nivel4_manzana_m", "#8c005e",
				  			"nivel4_manzana_n","#e84f02",
				  			"nivel4_manzana_r","#a1000d",
				  			"manzana","manzanas");
		  createConsigna(	"Tengo dos floreros, uno con # @ y otro con # @. " + recursos.getSigno() +"Cu"+ recursos.getA() + "ntas flores tengo en total?", 
				  			"Una nena tiene un ramo con # @, le regala a otra nena # @. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas flores quedan en el ramo?",
		  					"nivel4_flor_m", "#8c005e",
		  					"nivel4_flor_n","#e84f02",
		  					"nivel4_flor_r","#a1000d",
		  					"flor","flores");
		  createConsigna(	"Bruno tiene un "+recursos.getA()+"rbol con # @ y otro con #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas naranjas tiene en total?", 
				  			"Bruno tiene un "+recursos.getA()+"rbol con # @. Pero la mam"+recursos.getA()+" corta #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas naranjas quedan en el "+recursos.getA()+"rbol?",
		  					"nivel4_naranja_m", "#8c005e",
		  					"nivel4_naranja_n","#e84f02",
		  					"nivel4_naranja_r","#a1000d",
		  					"naranja","naranjas");
		  createConsigna(	"Pablo tiene una bolsa con # @. La mam"+recursos.getA()+" le regala #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos caramelos tiene Pablo?", 
				  			"Pablo compra # @. Le regala # a Nicol"+recursos.getA()+"s. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos caramelos le quedan a Pablo?",
		  					"nivel4_caramelo_m", "#8c005e",
		  					"nivel4_caramelo_n","#e84f02",
		  					"nivel4_caramelo_r","#a1000d",
		  					"caramelo","caramelos");
		  createConsigna(	"Christian compr"+recursos.getO()+" # @ y Bruno compr"+recursos.getO()+" # @. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos helados tienen en total?", 
				  			"Christian fue a la helader"+recursos.getI()+"a y compr"+recursos.getO()+" # @. A la tarde se comi"+recursos.getO()+" #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos helados le quedan?",
		  					"nivel4_helado_m", "#8c005e",
		  					"nivel4_helado_n","#e84f02",
		  					"nivel4_helado_r","#a1000d",
		  					"helado","helados");
		  createConsigna(	"Hay # @ sobre el cesped de un jard"+recursos.getI()+"n. El viento sopla y arroja # @ de un "+recursos.getA()+"rbol. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas hojas hay sobre el cesped?", 
				  			"Hay # @ sobre el cesped de un jard"+recursos.getI()+"n. Mar"+recursos.getI()+"a barre #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas hojas quedan sobre el cesped?",
		  					"nivel4_hoja_m", "#8c005e",
		  					"nivel4_hoja_n","#e84f02",
		  					"nivel4_hoja_r","#a1000d",
		  					"hoja","hojas");
		  createConsigna(	"Bruno us"+recursos.getO()+" # @ para cocinar. Al d"+recursos.getI()+"a siguiente us"+recursos.getO()+" #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos huevos us"+recursos.getO()+" en total?", 
				  			"Christian tiene # @ en la heladera. Bruno le pidi"+recursos.getO()+" # para cocinar. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos huevos quedan en la heladera?",
		  					"nivel4_huevo_m", "#8c005e",
		  					"nivel4_huevo_n","#e84f02",
		  					"nivel4_huevo_r","#a1000d",
		  					"huevo","huevos");
		  createConsigna(	"Pablo trabaja en un kiosco. A la ma"+Recursos.n+"ana vendi"+recursos.getO()+" # @ y a la tarde # m"+recursos.getA()+"s. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos jugos vendi"+recursos.getO()+" en total?", 
				  			"Nicol"+recursos.getA()+"s compr"+recursos.getO()+" # @ a la tarde. A la noche se tom"+recursos.getO()+" #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntos jugos le quedan?",
		  					"nivel4_jugo_m", "#8c005e",
		  					"nivel4_jugo_n","#e84f02",
		  					"nivel4_jugo_r","#a1000d",
		  					"jugo","jugos");
		  createConsigna(	"Christian recogi"+recursos.getO()+" # @ de un "+recursos.getA()+"rbol y # de otro. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas peras recogi"+recursos.getO()+" Christian en total?", 
				  			"Christian llev"+recursos.getO()+" # @ a su casa. El hermano se comi"+recursos.getO()+" #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas peras quedan?",
		  					"nivel4_pera_m", "#8c005e",
		  					"nivel4_pera_n","#e84f02",
		  					"nivel4_pera_r","#a1000d",
		  					"pera","peras");
		  createConsigna(	"Nicol"+recursos.getA()+"s tiene un jard"+recursos.getI()+"n con # @. Un d"+ recursos.getI() +"a planta # m"+recursos.getA()+"s. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas flores tiene en total?", 
				  			"Nicol"+recursos.getA()+"s tiene un jard"+recursos.getI()+"n con # @. El viento corta #. "+recursos.getSigno()+"Cu"+recursos.getA()+"ntas flores tiene en total?", 
				  			"nivel4_flor_m", "#8c005e",
				  			"nivel4_flor_n","#e84f02",
				  			"nivel4_flor_r","#a1000d",
				  			"flor","flores");
	  }
  }
  
@SuppressWarnings("serial")
public Enunciado cursorToEnunciado(final Nivel4Tabla consigna, Context c){
	  final int imagen1 = c.getResources().getIdentifier(consigna.getImagen1(), "drawable", c.getPackageName());
	  final int imagen2 = c.getResources().getIdentifier(consigna.getImagen2(), "drawable", c.getPackageName());
	  final int imagen3 = c.getResources().getIdentifier(consigna.getImagen3(), "drawable", c.getPackageName());
	  Enunciado enunciado = new Enunciado(	new ArrayList<Imagen>() {{add(new Imagen(imagen1, consigna.getColor1()));add(new Imagen(imagen2, consigna.getColor2())); add(new Imagen(imagen3, consigna.getColor3()));}}, 
			  								consigna.getConsignaMas(),
			  								consigna.getConsignaMenos(),
			  								consigna.getSingular(),
			  								consigna.getPlural());
	  return enunciado;
  }
  
  public Enunciado getEnunciado(Context c){
	  Cursor cursor;
	  Enunciado enunciado;
	  cursor = readEntry();
	  int ran = DaMagicNumber.randInt(0, cursor.getCount() -1);
	  for(int i=0;i<ran;i++){
		  cursor.moveToNext();
	  }
	  enunciado = cursorToEnunciado(cursorToConsigna(cursor), c);
	  return enunciado;
  }
  
  public void createTable(String table){
      database.execSQL("DROP TABLE IF EXISTS " + Nivel4SQLiteHelper.TABLE_NIVEL);
      database.execSQL(Nivel4SQLiteHelper.DATABASE_CREATE);
  }
}
