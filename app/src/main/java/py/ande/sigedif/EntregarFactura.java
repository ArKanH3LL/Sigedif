package py.ande.sigedif;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.view.View;

import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by asu05894 on 3/8/2016.
 */
public class EntregarFactura extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    public static final String TAG = "WEAVER_";
    TextView longitude;
    TextView latitude;
    EditText registrar;
    DateFormat fechaen;
    String buscado;
    double latEN, lonEN, lonENcorrected;

    Cursor D;
    sSQLController sqlcon;
    ProgressDialog PD;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public LocationManager mLocationManager;
    int updates;

    MapView mapView;
    GoogleMap mMap;
    LatLng suministro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrega);

        // Get the map and register for the ready callback
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(EntregarFactura.this);


        fechaen = new SimpleDateFormat("dd MM yyyy, HH:mm");

        sqlcon = new sSQLController(this);
        sqlcon.open();
        D = sqlcon.readEntry();

        Log.v(TAG, "OnCreate");
        updates = 0;
        handlePermissionsAndGetLocation();

        longitude = (TextView) findViewById(R.id.latMostrar);
        latitude = (TextView) findViewById(R.id.lonMostrar);
        registrar = (EditText) findViewById(R.id.nisregistrar);

        final Button entregara = (Button) findViewById(R.id.entregar);

        entregara.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                longitude.setText("Longitude:" + latEN);
                latitude.setText("Latitude:" + lonENcorrected);
                buscado = registrar.getText().toString();
                new BuscarTask().execute();

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Accepted
                    getLocation();
                } else {
                    // Denied
                    Toast.makeText(EntregarFactura.this, "LOCATION Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void handlePermissionsAndGetLocation() {
        Log.v(TAG, "handlePermissionsAndGetLocation");
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        getLocation();//if already has permission
    }

    protected void getLocation() {
        Log.v(TAG, "GetLocation");
        int LOCATION_REFRESH_TIME = 1000;
        int LOCATION_REFRESH_DISTANCE = 5;

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
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
            latEN = location.getLatitude();
            lonEN = location.getLongitude();
            lonENcorrected = lonEN * -1;

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

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class BuscarTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


            PD = new ProgressDialog(EntregarFactura.this);
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
            sqlcon.actualizar(buscado, date, LatEnt, LonEnt);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            PD.dismiss();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        suministro = new LatLng(latEN, lonENcorrected);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(suministro));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }
}