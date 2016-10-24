package py.ande.sigedif;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by asu05894 on 3/8/2016.
 */
public class ConsultarSuministro extends AppCompatActivity {


    sSQLController sqlcon;
    Cursor c;
    ProgressDialog PD;
    EditText et_buscarSum;
    Button bt_buscarsum, verenmapa;
    TextView nombre, direccion, barrio, ciudad, telefono, entregado;


    public static final String TAG = "WEAVER_";

    EditText registrar;
    DateFormat fechaen;
    String buscado;
    double latEN, lonEN, lonENcorrected;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    public LocationManager mLocationManager;
    int updates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultarsum);

        Log.v(TAG, "OnCreate");
        updates = 0;
        handlePermissionsAndGetLocation();

        registrar = (EditText) findViewById(R.id.nisregistrar);

        fechaen = new SimpleDateFormat("dd MM yyyy, HH:mm");

        sqlcon = new sSQLController(this);

        nombre = (TextView)findViewById(R.id.nomCliente);
        direccion = (TextView)findViewById(R.id.dirCliente);
        barrio = (TextView)findViewById(R.id.barrioCliente);
        ciudad = (TextView)findViewById(R.id.ciudadCliente);
        telefono = (TextView)findViewById(R.id.telefonoCliente);
        entregado = (TextView)findViewById(R.id.Entregado);

        et_buscarSum = (EditText) findViewById(R.id.consulSumi);
        bt_buscarsum = (Button) findViewById(R.id.consulCliente);
        verenmapa = (Button) findViewById(R.id.irASuministro);

        bt_buscarsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscado=et_buscarSum.getText().toString();
                new BuscarTask().execute();
            }
        });

        final Button entregara = (Button) findViewById(R.id.EntregarAhora);

        entregara.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lonENcorrected=lonEN*-1;
                new actualizarEntrega().execute();

            }
        });

        verenmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ConsultarSuministro.this, MapsActivity.class);
                intent1.putExtra("buscado",buscado);
                startActivity(intent1);
            }
        });


    }

    private void handlePermissionsAndGetLocation() {
        Log.v(TAG, "handlePermissionsAndGetLocation");
        int hasWriteContactsPermission = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        getLocation();//if already has permission
    }

    protected void getLocation() {
        Log.v(TAG, "GetLocation");
        int LOCATION_REFRESH_TIME = 1000;
        int LOCATION_REFRESH_DISTANCE = 5;

        if (!(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Log.v("WEAVER_", "Has permission");
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        } else {
            Log.v("WEAVER_", "Does not have permission");
        }

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.v("WEAVER_", "Location Change");
            latEN=location.getLatitude();
            lonEN=location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private class BuscarTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            PD = new ProgressDialog(ConsultarSuministro.this);
            PD.setTitle("Um momento...");
            PD.setMessage("Buscando...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            // inserting data
            sqlcon.open();
            c = sqlcon.consultarSuministro(buscado);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            String Entregado;
                nombre.setText(c.getString(9));
                direccion.setText(c.getString(10));
                barrio.setText(c.getString(11));
                ciudad.setText(c.getString(12));
                telefono.setText(c.getString(13));
                Entregado=c.getString(6);
            if (Entregado==null){
                entregado.setText("NO");
                entregado.setTextColor(Color.RED);
            }else{
                entregado.setText("SI");
                entregado.setTextColor(Color.BLUE);
            }

            PD.dismiss();
        }
    }

    private class actualizarEntrega extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            PD = new ProgressDialog(ConsultarSuministro.this);
            PD.setTitle("Um momento...");
            PD.setMessage("Registrando...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String date = fechaen.format(Calendar.getInstance().getTime());
            // inserting data

            String LatEnt = String.valueOf(latEN);
            String LonEnt = String.valueOf(lonENcorrected);
            sqlcon.open();
            sqlcon.actualizar(buscado,date,LatEnt,LonEnt);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            PD.dismiss();
        }
    }


}