package py.ande.sigedif;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by asu05894 on 7/9/2016.
 */
public class ConvertUTMtoLatLong extends AppCompatActivity {

    Integer X,Y;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversorutmlatlong);

        final Button convertidor = (Button) findViewById(R.id.convertidor);
        final EditText ValorDeX = (EditText)findViewById(R.id.valorX);
        final EditText ValorDeY = (EditText)findViewById(R.id.valorY);
        final TextView ValorLAT = (TextView)findViewById(R.id.ValorLAT);
        final TextView ValorLONG = (TextView)findViewById(R.id.ValorLONG);

        convertidor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                X=Integer.valueOf(ValorDeX.getText().toString());
                Y=Integer.valueOf(ValorDeY.getText().toString());

                double e2 = 0.00672267;
                double e = Math.sqrt(e2);
                double a = 6378388;
                double eZ = e2 / (1 - e2);
                double h1 = (1-(Math.pow((1-e2),0.5)))/(1+(Math.pow((1-e2),0.5)));
                double h3 = (Y - 10000000) / 0.9996;
                double h2 = h3 / (a * (1 - e2 / 4 - 3 * (Math.pow(e, 4)/64) - 5 * Math.pow(e, 6)/256));
                double h4 = h2 + (3 * h1 / 2 - 27 * Math.pow(h1, 3)/32) * Math.sin((2 * h2)) + (21 * Math.pow(h1, 2)/16 - 55 * Math.pow(h1, 4)/32) * Math.sin((4 * h2)) + (151 * Math.pow(h1, 3)/96) * Math.sin((6 * h2)) + (1097 * Math.pow(h1, 4)/512) * Math.sin((8 * h2));
                double h5 = eZ * Math.pow((Math.cos(h4)),2);
                double h6 = Math.pow(Math.tan(h4), 2);
                double h7 = a / Math.pow((1 - e2 * Math.pow((Math.sin(h4)), 2)), 0.5);
                double h8 = a * (1 - e2) / (Math.pow((1 - e2 * Math.pow(Math.sin(h4), 2)), 1.5));
                double h12 = ((30 - Math.abs(-21)) * 6 + 3);
                double h13 = h7 * 0.9996;
                double h14 = X - 500000;
                double h9 = h14 / h13;
                double h15 = h9 - (1 + 2 * h6 + h5) * Math.pow(h9, 3)/6;
                double h16 = (5 - 2 * h5 + 28 * h6 - 3 * Math.pow(h5, 2) + 8 * eZ + 24 * Math.pow(h6, 2)) * Math.pow(h9, 5)/120;
                double h10 = (h4 - (h7 * Math.tan(h4) / h8) * ((Math.pow(h9, 2) / 2 - (5 + 3 * h6 + 10 * h5 - 4 * Math.pow(h5, 2) - 9 * eZ) * Math.pow(h9, 4)/24 + (61 + 90 * h6 + 298 * h5 + 45 * Math.pow(h6, 2) - 252 * eZ - 3 * Math.pow(h5, 2)) * Math.pow(h9, 6)/720))) * 180 / Math.PI;
                double h11 = ((-1 * h12) + ((h15 + h16) / Math.cos(h4)) * 180 / Math.PI) * -1;

                ValorLAT.setText(String.valueOf(h10));
                ValorLONG.setText(String.valueOf(h11));

            }
        });


    }












}
