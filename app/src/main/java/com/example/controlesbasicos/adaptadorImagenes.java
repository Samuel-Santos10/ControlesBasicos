package com.example.controlesbasicos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorImagenes extends BaseAdapter {

    Context context;
    ArrayList<productos> datos;
    LayoutInflater layoutInflater;
    productos producto;

    public adaptadorImagenes(Context context, ArrayList<productos> dato){
        this.context = context;
        try {
            this.datos = dato;
        }catch (Exception ex){}
    }

    @Override
    public int getCount() {
        try {
            return datos.size();
        }catch (Exception ex) {
            return 0;
        }
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.item_mostrar, viewGroup, false);
        TextView tvTituMarca = (TextView)itemView.findViewById(R.id.TVlmarca);
        TextView tvTituDescripcion = (TextView)itemView.findViewById(R.id.TVldescripcion);
        TextView tvTituPrecio = (TextView)itemView.findViewById(R.id.TVlprecio);

        ImageView imageView = (ImageView)itemView.findViewById(R.id.img);
        try {
            producto = datos.get(i);
            tvTituMarca.setText(producto.getNombre());
            tvTituDescripcion.setText(producto.getMarca());
            tvTituPrecio.setText(producto.getPrecio());

            Bitmap imageBitmap = BitmapFactory.decodeFile(producto.getUrlImg());

            imageView.setImageBitmap(imageBitmap);
        }catch (Exception ex){ }
        return itemView;
    }
}
