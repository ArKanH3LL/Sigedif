package py.ande.sigedif;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asu05894 on 15/9/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="itinerarios.sqlite";
    private static final int DB_SCHEME_VERSION=1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
