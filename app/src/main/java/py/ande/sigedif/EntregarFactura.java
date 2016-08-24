package py.ande.sigedif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by asu05894 on 3/8/2016.
 */
public class EntregarFactura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrega);

        final Button entregara = (Button) findViewById(R.id.entregar);

        entregara.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent4 = new Intent(EntregarFactura.this, ConsultarSuministro.class);
                startActivity(intent4);

            }
        });


    }


}