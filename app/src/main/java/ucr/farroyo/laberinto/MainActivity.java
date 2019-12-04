package ucr.farroyo.laberinto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Laberinto lab;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hay que buscar la interfax para colocar texto en el xml. para eso utilizamos su id.
        textView = findViewById(R.id.textbox);
        lab = new Laberinto(20);
        textView.setText(lab.toString());
        System.out.println(lab);
        AsyncTask<Void, String, Void> task = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                lab.encuentraQueso();
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                textView.setText(lab.toString());
            }
        };
        task.execute();
    }
}
