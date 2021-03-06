package com.example.valium;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Ricette extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button richiediRicettaButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricette);
        String username = MappaUtenti.getUtenteAttuale();
        richiediRicettaButton = findViewById(R.id.richiediRicetta);
        final Context c = this;
        final ListView mylist = (ListView) findViewById(R.id.listView1);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, MappaRicette.ricetteUtente(username));
        mylist.setAdapter(adapter);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id){
                // recupero il titolo memorizzato nella riga tramite l'ArrayAdapter
                final String titoloriga = (String) adattatore.getItemAtPosition(pos);
                final char a = ' ';
                int nr = 0;
                StringBuilder dio = new StringBuilder();
                for(int i = 1; i < titoloriga.length() && titoloriga.charAt(i) != a; i++) {
                    dio.append(titoloriga.charAt(i));
                }
                Log.d("List", "Ho cliccato sull'elemento con id" + dio);
                nr = Integer.parseInt(dio.toString());
                String urlString = MappaRicette.ricettaPerId(nr).getFilePath();
                if (urlString != null) {
                    Thread thread = new Thread(new DownloadRunnable(urlString,c,titoloriga));
                    thread.start();
                }
            }
        });
        richiediRicettaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                richiediRicetta();
            }
        });
    }

    public void richiediRicetta(){
        Intent intent = new Intent(this, RichiediRicetta.class);
        startActivity(intent);
    }
}


