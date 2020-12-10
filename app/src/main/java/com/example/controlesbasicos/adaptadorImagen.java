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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adaptadorImagen extends BaseAdapter {
    Context context;
    ArrayList<usuarios> datos;
    LayoutInflater layoutInflater;
    usuarios user;

    public adaptadorImagen(Context context, ArrayList<usuarios> datos) {
        this.context = context;
        try {
            this.datos = datos;
        } catch (Exception ex) {
        }
    }

    @Override
    public int getCount() {
        try {
            return datos.size();
        } catch (Exception ex) {
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
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.listview_imagen, viewGroup, false);
        final TextView textView = (TextView)itemView.findViewById(R.id.txtTitulo);
        final ImageView imageView = (ImageView)itemView.findViewById(R.id.img);
        try {
            user = datos.get(i);
            textView.setText(user.getUserName());
            Glide.with(context).load(user.getUrlFotoFirestore()).into(imageView);
        }catch (Exception ex){
            textView.setText(ex.getMessage());
        }
        return itemView;
    }
}