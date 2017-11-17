package com.example.manoel.wolfchatapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import Entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Manoel on 15/11/2017.
 */

public class AdapterFotoPerfil extends ArrayAdapter<Usuario> {

    private Activity context;
    private int resource;
    private List<Usuario> listImage;

    public AdapterFotoPerfil(@NonNull Activity context, int resource, @NonNull List<Usuario> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resource, null);
        CircleImageView fotoP = v.findViewById(R.id.fotoPerfil);

        Glide.with(context).load(listImage.get(position).getUrl()).into(fotoP);

        return v;
    }
}
