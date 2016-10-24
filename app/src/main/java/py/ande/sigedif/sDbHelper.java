package py.ande.sigedif;

/**
 * Created by asu05894 on 21/9/2016.
 */
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class sDbHelper extends SQLiteOpenHelper {

    //Informacion de la TABLA
    public static final String TABLE_NAME="itinerario1";
    public static final String CN_ID="_id";
    public static final String CN_NIS="nis";
    public static final String CN_X="coordX";
    public static final String CN_Y="coordY";
    public static final String CN_DATE="fechaen";
    public static final String CN_LAT="coordLAT";
    public static final String CN_LON="coordLON";
    public static final String CN_LATen="coordLATen";
    public static final String CN_LONen="coordLONen";
    public static final String CN_nombre="nombre";
    public static final String CN_direccion="direccion";
    public static final String CN_barrio="barrio";
    public static final String CN_ciudad="ciudad";
    public static final String CN_telefono="telefono";


    //Informacion de la Base de DAtos
    static final String DB_NAME="itinerario.db3";
    static final int DB_VERSION=1;
    static final String DB_PATH="/data/data/py.ande.sigedif/databases/";

    //Importación de la Tabla
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    //Establece el Context
    public sDbHelper (Context context){
        super(context, DB_NAME,null,DB_VERSION);
        this.myContext=context;
    }


    //Crea una Base de Datos y la reescribe con la mia.

    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getWritableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }




    //Metodo onCreate
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //metodo onUpdate
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
