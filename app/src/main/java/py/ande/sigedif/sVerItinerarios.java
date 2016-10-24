package py.ande.sigedif;

/**
 * Created by asu05894 on 21/9/2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    Button verenmapa, limpiarcampo;
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
        verenmapa = (Button) findViewById(R.id.vermapa);
        limpiarcampo = (Button) findViewById(R.id.limpiar);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);


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

        verenmapa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(sVerItinerarios.this, MapsActivity.class);
                intent1.putExtra("buscado",buscado);
                startActivity(intent1);
            }
        });

        limpiarcampo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_buscarSum.setText(null);
                sqlcon.open();
                c = sqlcon.readEntry();
                BuildTable();
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
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(10);
                tv.setPadding(0, 2, 0, 2);
                tv.setBackgroundResource(R.drawable.bordesimple);
                String entregado = c.getString(6);
                if (entregado==null) {
                    tv.setTextColor(Color.RED);
                }
                else{
                    tv.setTextColor(Color.BLACK);
                }
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
            PD.setTitle("Un momento...");
            PD.setMessage("Buscando...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // inserting data
            sqlcon.open();
            c = sqlcon.buscarSuministro(buscado);
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
