package py.ande.sigedif;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by asu05894 on 15/9/2016.
 */
public class DataBaseManager {
    public static final String TABLE_NAME="itinerarios";
    public static final String CN_NIS="nis";
    public static final String CN_X="cordX";
    public static final String CN_Y="coordY";
    public static final String CN_DATE="fechaen";
    public static final String CN_LAT="coordLAT";
    public static final String CN_LON="coordLON";
    public static final String CN_LATen="coordLATen";
    public static final String CN_LONen="coordLONen";

    public static final String CREATE_TABLE= " create table " +TABLE_NAME+ " ("
            + CN_NIS + " integer primary key, "
            + CN_X + " integer, "
            + CN_Y + " integer, "
            + CN_DATE + " text, "
            + CN_LAT + " real, "
            + CN_LON + " real, "
            + CN_LATen + " real, "
            + CN_LONen + " real);";

    private DBHelper helper;
    private SQLiteDatabase db;
    public DataBaseManager(Context context) {

        helper = new DBHelper(context);
        db = helper.getWritableDatabase();

        db.insert()
        db.execSQL();
    }
}
