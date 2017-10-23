package com.example.manoel.wolfchatapp.Activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Manoel on 21/10/2017.
 */

public class HolderMenssagens extends RecyclerView.ViewHolder {

    private TextView nome;
    private TextView mensagem;
    private TextView hora;
    private CircleImageView FotoMenssagemPerfil;
    private ImageView fotoMessagem;

    public HolderMenssagens(View itemView){
        super(itemView);

        nome = (TextView) itemView.findViewById(R.id.nomeMenssagem);
        mensagem = (TextView) itemView.findViewById(R.id.menssagemMenssagem);
        hora = (TextView) itemView.findViewById(R.id.horaMenssagem);
        FotoMenssagemPerfil = (CircleImageView) itemView.findViewById(R.id.fotoPerfilMenssagem);
        fotoMessagem = (ImageView) itemView.findViewById(R.id.menssagemFoto);
    }

    public TextView getNome() {
        return nome;
    }

    public void setNome(TextView nome) {
        this.nome = nome;
    }

    public TextView getMensagem() {
        return mensagem;
    }

    public void setMensagem(TextView mensagem) {
        this.mensagem = mensagem;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoMenssagemPerfil() {
        return FotoMenssagemPerfil;
    }

    public void setFotoMenssagemPerfil(CircleImageView FotoMenssagemPerfil) {
        this.FotoMenssagemPerfil = FotoMenssagemPerfil;
    }

    public ImageView getFotoMessagem() {
        return fotoMessagem;
    }

    public void setFotoMessagem(ImageView fotoMessagem) {
        this.fotoMessagem = fotoMessagem;
    }
}
