package py.ande.sigedif;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Cursor D;
    sSQLController sqlcon;
    int rows;
    String nronis;
    LatLng suministro;

    String Equis, Ye;
    Integer X,Y;
    double a,e,e2,eZ,h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,LatLin,LonLin;

    double Lat=0;
    double Lon=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        String buscado = intent.getExtras().getString("buscado");

        sqlcon = new sSQLController(this);
        sqlcon.open();
        if (buscado==null) {
            D = sqlcon.readEntry();
            rows = D.getCount();
            D.moveToFirst();
        }else{
            D=sqlcon.buscarSuministro(buscado);
            rows = D.getCount();
            D.moveToFirst();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < rows; i++) {

            nronis= D.getString(D.getColumnIndex("nis"));
            Equis =D.getString(D.getColumnIndex("coordX"));
            Ye = D.getString(D.getColumnIndex("coordY"));
            X= Integer.parseInt(Equis);
            Y= Integer.parseInt(Ye);
            LatLin=Lat;
            LonLin=Lon;

            convertUTMtoGEO(X,Y);
            Lat=h10;
            Lon=h11*-1;
            suministro = new LatLng(Lat, Lon);
            MarkerOptions marker = new MarkerOptions().position(suministro).flat(true).title(nronis);
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconito));
            String entregado = D.getString(6);
            if (entregado==null) {
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconito2));
            }
            mMap.addMarker(marker);


            D.moveToNext();
            if (i < 1 ) {
                LatLin = Lat;
                LonLin = Lon;
            }
            else{
            //La Linea!
            Polyline line = mMap.addPolyline(
                    new PolylineOptions().add(
                            new LatLng(LatLin, LonLin),
                            new LatLng(h10, h11 * -1)
                    ).width(2).color(Color.BLUE).geodesic(true));
            }
        }

        sqlcon.close();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(suministro));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }

    public void convertUTMtoGEO (Integer X, Integer Y) {
        e2 = 0.00672267;
        e = Math.sqrt(e2);
        a = 6378388;
        eZ = e2 / (1 - e2);
        h1 = (1-(Math.pow((1-e2),0.5)))/(1+(Math.pow((1-e2),0.5)));
        h3 = (Y - 10000000) / 0.9996;
        h2 = h3 / (a * (1 - e2 / 4 - 3 * (Math.pow(e, 4)/64) - 5 * Math.pow(e, 6)/256));
        h4 = h2 + (3 * h1 / 2 - 27 * Math.pow(h1, 3)/32) * Math.sin((2 * h2)) + (21 * Math.pow(h1, 2)/16 - 55 * Math.pow(h1, 4)/32) * Math.sin((4 * h2)) + (151 * Math.pow(h1, 3)/96) * Math.sin((6 * h2)) + (1097 * Math.pow(h1, 4)/512) * Math.sin((8 * h2));
        h5 = eZ * Math.pow((Math.cos(h4)),2);
        h6 = Math.pow(Math.tan(h4), 2);
        h7 = a / Math.pow((1 - e2 * Math.pow((Math.sin(h4)), 2)), 0.5);
        h8 = a * (1 - e2) / (Math.pow((1 - e2 * Math.pow(Math.sin(h4), 2)), 1.5));
        h12 = ((30 - Math.abs(-21)) * 6 + 3);
        h13 = h7 * 0.9996;
        h14 = X - 500000;
        h9 = h14 / h13;
        h15 = h9 - (1 + 2 * h6 + h5) * Math.pow(h9, 3)/6;
        h16 = (5 - 2 * h5 + 28 * h6 - 3 * Math.pow(h5, 2) + 8 * eZ + 24 * Math.pow(h6, 2)) * Math.pow(h9, 5)/120;
        h10 = (h4 - (h7 * Math.tan(h4) / h8) * ((Math.pow(h9, 2) / 2 - (5 + 3 * h6 + 10 * h5 - 4 * Math.pow(h5, 2) - 9 * eZ) * Math.pow(h9, 4)/24 + (61 + 90 * h6 + 298 * h5 + 45 * Math.pow(h6, 2) - 252 * eZ - 3 * Math.pow(h5, 2)) * Math.pow(h9, 6)/720))) * 180 / Math.PI;
        h11 = ((-1 * h12) + ((h15 + h16) / Math.cos(h4)) * 180 / Math.PI) * -1;


    }
}
