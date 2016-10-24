package py.ande.sigedif;

/**
 * Created by asu05894 on 21/9/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class sSQLController {

    private sDbHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public sSQLController(Context c) {
        ourcontext = c;
    }

    public sSQLController open() throws SQLException {
        dbhelper = new sDbHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbhelper.close();
    }

  //  public void insertData(String fecha, String laten, String lonen) {

    //    ContentValues cv = new ContentValues();
      //  cv.put(sDbHelper.fechaen, name);
        //cv.put(sDbHelper.coordLATen, laten);
        //cv.put(sDbHelper.coordLONen, lonen);
        //database.insert(sDbHelper.TABLE_NAME, null, cv);

    //}

    public Cursor readEntry() {
        String[] allColumns = new String[]{sDbHelper.CN_ID, sDbHelper.CN_NIS,sDbHelper.CN_X,sDbHelper.CN_Y,sDbHelper.CN_LAT,sDbHelper.CN_LON,sDbHelper.CN_DATE,sDbHelper.CN_LATen,sDbHelper.CN_LONen};
        Cursor c = database.query(sDbHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor buscarSuministro(String nis){
        String[] allColumns = new String[]{sDbHelper.CN_ID, sDbHelper.CN_NIS,sDbHelper.CN_X,sDbHelper.CN_Y,sDbHelper.CN_LAT,sDbHelper.CN_LON,sDbHelper.CN_DATE,sDbHelper.CN_LATen,sDbHelper.CN_LONen};
        Cursor c = database.query(sDbHelper.TABLE_NAME, allColumns,sDbHelper.CN_NIS+"=?",new String[]{nis}, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }

    public Cursor consultarSuministro(String nis){
        String[] allColumns = new String[]{sDbHelper.CN_ID, sDbHelper.CN_NIS,sDbHelper.CN_X,sDbHelper.CN_Y,sDbHelper.CN_LAT,sDbHelper.CN_LON,sDbHelper.CN_DATE,sDbHelper.CN_LATen,sDbHelper.CN_LONen,sDbHelper.CN_nombre,sDbHelper.CN_direccion,sDbHelper.CN_barrio,sDbHelper.CN_ciudad,sDbHelper.CN_telefono};
        Cursor c = database.query(sDbHelper.TABLE_NAME, allColumns,sDbHelper.CN_NIS+"=?",new String[]{nis}, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;


    }


    public boolean actualizar(String nis, String fecha, String latEN, String lonEN) {
        ContentValues args = new ContentValues();
        args.put("fechaEN", fecha);
        args.put("coordLATen", latEN);
        args.put("coordLONen", lonEN);
        return database.update(sDbHelper.TABLE_NAME, args, sDbHelper.CN_NIS + "=" + nis, null)>0;
    }

}