package com.example.manoel.wolfchatapp.Activity;

import java.util.Map;

/**
 * Created by Manoel on 22/10/2017.
 */

public class MenssagensEnviadas extends Menssagem{

    private Map hora;


    public MenssagensEnviadas(){
    }

    public MenssagensEnviadas(Map hora) {
        this.hora = hora;
    }

    public MenssagensEnviadas(String mensagem, String nome, String fotoPerfil, String tipo_mensagem, Map hora) {
        super(mensagem, nome, fotoPerfil, tipo_mensagem);
        this.hora = hora;
    }

    public MenssagensEnviadas(String mensagem, String urlFoto, String nome, String fotoPerfil, String tipo_mensagem, Map hora) {
        super(mensagem, urlFoto, nome, fotoPerfil, tipo_mensagem);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
