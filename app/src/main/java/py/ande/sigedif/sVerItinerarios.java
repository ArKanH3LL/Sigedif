package py.ande.sigedif;

/**
 * Created by asu05894 on 21/9/2016.
 */
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.io.IOException;

public class sVerItinerarios extends AppCompatActivity {

    TableLayout table_layout;
    EditText et_buscarSum;
    String buscado;
    Button bt_buscarsum;
    LinearLayout linearT;
    Cursor c;


    sSQLController sqlcon;

    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veritinerarios);

        sqlcon = new sSQLController(this);

        linearT = (LinearLayout)findViewById(R.id.linearLayoutT);
        et_buscarSum = (EditText) findViewById(R.id.sumi);
        bt_buscarsum = (Button) findViewById(R.id.buscasum);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        //Crear la Base de Datos
        sDbHelper myDbHelper;
        myDbHelper = new sDbHelper(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
      //  try {
//
  //      }catch(SQLException sqle){
    //        throw sqle;
      //  }
        sqlcon.open();
        c = sqlcon.readEntry();
        BuildTable();

        bt_buscarsum.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            buscado=et_buscarSum.getText().toString();
                new BuscarTask().execute();
            }
        });

    }

    private void BuildTable() {

        int rows = c.getCount();
        int cols = c.getColumnCount();
        c.moveToFirst();

        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 0; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(10);
                tv.setPadding(0, 2, 0, 2);

                tv.setText(c.getString(j));

                row.addView(tv);

            }

            c.moveToNext();

            table_layout.addView(row);

        }
        sqlcon.close();
    }

    private class BuscarTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            table_layout.removeAllViews();

            PD = new ProgressDialog(sVerItinerarios.this);
            PD.setTitle("Um momento...");
            PD.setMessage("Buscando...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // inserting data
            sqlcon.open();
            c = sqlcon.buscarSuministro(buscado);
            // BuildTable();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            BuildTable();
            PD.dismiss();
        }
    }

}
