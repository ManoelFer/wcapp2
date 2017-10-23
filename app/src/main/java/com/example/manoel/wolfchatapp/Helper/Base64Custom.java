package com.example.manoel.wolfchatapp.Helper;

import android.util.Base64;

/**
 * Created by Manoel on 21/10/2017.
 */

public class Base64Custom {

    public static  String codificandoBase64(String texto){
        return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
    public static  String descodificandoBase64(String textoCodificado){
        return new String(Base64.decode(textoCodificado,Base64.DEFAULT));
    }
}
