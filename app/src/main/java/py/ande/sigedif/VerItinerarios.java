package py.ande.sigedif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by asu05894 on 3/8/2016.
 */
public class VerItinerarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        final Button navegara = (Button) findViewById(R.id.navegar);

        navegara.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent4 = new Intent(VerItinerarios.this, Navegar.class);
                startActivity(intent4);

            }
        });


    }


}