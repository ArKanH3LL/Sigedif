package py.ande.sigedif;

/**
 * Created by asu05894 on 21/9/2016.
 */
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
}