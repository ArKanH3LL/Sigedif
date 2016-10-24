package py.ande.sigedif;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crear la Base de Datos
        sDbHelper myDbHelper;
        myDbHelper = new sDbHelper(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }


        final Button verItitnerario = (Button) findViewById(R.id.verItinerarios);

        verItitnerario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent1 = new Intent(MainActivity.this, sVerItinerarios.class);
                startActivity(intent1);

                }
            });

        final Button verSuministros = (Button) findViewById(R.id.verSuministros);

        verSuministros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent1 = new Intent(MainActivity.this, VerSuministros.class);
                startActivity(intent1);

            }
        });

        final Button entregarFactura = (Button) findViewById(R.id.entregarFacturas);

        entregarFactura.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent2 = new Intent(MainActivity.this, EntregarFactura.class);
                startActivity(intent2);

            }
        });

        final Button registrarIncidencia = (Button) findViewById(R.id.registrarIncidencia);

        registrarIncidencia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent3 = new Intent(MainActivity.this, RegistrarIncidencia.class);
                startActivity(intent3);

            }
        });

        final Button consultarSuministro = (Button) findViewById(R.id.consultarSuministro);

        consultarSuministro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent4 = new Intent(MainActivity.this, ConsultarSuministro.class);
                startActivity(intent4);

            }
        });

        final Button actualizardato = (Button) findViewById(R.id.actualizarDatos);

        actualizardato.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
                // Perform action on click

            //    Intent intent5 = new Intent(MainActivity.this, ActualizarDatos.class);
              //  startActivity(intent5);
              AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
              dlgAlert.setMessage("Actualizando Datos al Servidor");
              dlgAlert.setTitle("Conectado a OPEN");
              dlgAlert.setPositiveButton("OK", null);
              dlgAlert.setCancelable(true);
              dlgAlert.create().show();

            }
        });

        final Button conversorUTMtoGEO = (Button) findViewById(R.id.conversor);

        conversorUTMtoGEO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent5 = new Intent(MainActivity.this, ConvertUTMtoLatLong.class);
                startActivity(intent5);

            }
        });


    }
}