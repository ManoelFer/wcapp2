package com.example.manoel.wolfchatapp.Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Manoel on 21/10/2017.
 */

public class AdapterMenssagens extends RecyclerView.Adapter<HolderMenssagens> {

    private List<MenssagensRecebidas> listMenssagem  = new ArrayList<>();
    private Context c;

    public AdapterMenssagens( Context c) {
        this.c = c;
    }
    public void addMenssagem(MenssagensRecebidas m){
        listMenssagem.add(m);
        notifyItemInserted(listMenssagem.size());
    }

    @Override
    public HolderMenssagens onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_menssagens, parent, false);
        return new HolderMenssagens(v) ;
    }

    @Override
    public void onBindViewHolder(HolderMenssagens holder, int position) {
        holder.getNome().setText(listMenssagem.get(position).getNome());
        holder.getMensagem().setText(listMenssagem.get(position).getMensagem());

        if (listMenssagem.get(position).getTipo_mensagem().equals("2"))
        {

            holder.getFotoMessagem().setVisibility(View.VISIBLE);
            holder.getMensagem().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMenssagem.get(position).getUrlFoto()).into(holder.getFotoMessagem());

        }
        else if (listMenssagem.get(position).getTipo_mensagem().equals("1"))
        {

            holder.getFotoMessagem().setVisibility(View.GONE);
            holder.getMensagem().setVisibility(View.VISIBLE);

        }
        if (listMenssagem.get(position).getFotoPerfil().isEmpty())
        {
            holder.getFotoMenssagemPerfil().setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(c).load(listMenssagem.get(position).getFotoPerfil()).into(holder.getFotoMenssagemPerfil());
        }

        Long codigoHora = listMenssagem.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm ou am
        holder.getHora().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return listMenssagem.size();
    }
}
