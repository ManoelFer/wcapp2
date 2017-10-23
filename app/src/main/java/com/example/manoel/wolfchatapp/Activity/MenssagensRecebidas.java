package com.example.manoel.wolfchatapp.Activity;

/**
 * Created by Manoel on 22/10/2017.
 */

public class MenssagensRecebidas extends Menssagem {

    private Long hora;

    public MenssagensRecebidas() {
    }

    public MenssagensRecebidas( Long hora) {
        this.hora = hora;
    }

    public MenssagensRecebidas(String mensagem, String urlFoto, String nome, String fotoPerfil, String tipo_mensagem,  Long hora) {
        super(mensagem, urlFoto, nome, fotoPerfil, tipo_mensagem);
        this.hora = hora;
    }

    public  Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
