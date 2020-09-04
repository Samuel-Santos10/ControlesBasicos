package com.example.controlesbasicos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Vector;

public class Reproductor extends AppCompatActivity {

    ListView ltsReproductor;
    ArrayList<String> stringArrayList;
    Uri uri; //Uri -> Identificador Unico de Recursos.
    Vector<canciones> cancionesVector =  new Vector<canciones>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        ltsReproductor = (ListView) findViewById(R.id.ltsReproductor);
        mostrarCanciones();
    }
    void mostrarCanciones(){
        stringArrayList = new ArrayList<String>();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
        ltsReproductor.setAdapter(stringArrayAdapter);

        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor listaCanciones = getContentResolver().query(uri, null, null, null, null);
        if( listaCanciones!= null && listaCanciones.moveToFirst() ){
            int columnTitle = listaCanciones.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int columnId = listaCanciones.getColumnIndex(MediaStore.Audio.Media._ID);
            canciones canciones;
            do{
                String title = listaCanciones.getString(columnTitle);
                Long id = Long.parseLong(listaCanciones.getString(columnId));
                stringArrayAdapter.add(title);

                canciones =  new canciones(title, id);
                cancionesVector.addElement(canciones);

            }while (listaCanciones.moveToNext());
            stringArrayAdapter.notifyDataSetChanged();
        }
        ltsReproductor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                MediaPlayer mp = new MediaPlayer();

                Uri uri2 = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cancionesVector.get(posicion).getId());
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mp.setDataSource(getApplicationContext(), uri2);
                    mp.prepare();
                }catch (Exception err){

                }
                mp.start();
            }
        });
    }
}
class canciones{
    String title;
    Long id;

    public canciones(String title, Long id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}