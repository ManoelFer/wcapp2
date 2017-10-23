package com.example.manoel.wolfchatapp.Activity;

/**
 * Created by Manoel on 21/10/2017.
 */

public class Menssagem {

    private  String mensagem;
    private String urlFoto;
    private  String nome;
    private String fotoPerfil;
    private String tipo_mensagem;


    public Menssagem(){

    }

    public Menssagem(String mensagem, String nome, String fotoPerfil, String tipo_mensagem) {
        this.mensagem = mensagem;
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.tipo_mensagem = tipo_mensagem;

    }

    public Menssagem(String mensagem, String urlFoto, String nome, String fotoPerfil, String tipo_mensagem) {
        this.mensagem = mensagem;
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.tipo_mensagem = tipo_mensagem;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getTipo_mensagem() {
        return tipo_mensagem;
    }

    public void setTipo_mensagem(String tipo_mensagem) {
        this.tipo_mensagem = tipo_mensagem;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
