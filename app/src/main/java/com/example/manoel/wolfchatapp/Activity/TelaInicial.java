package com.example.manoel.wolfchatapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.manoel.wolfchatapp.Activity.TelaDeLogin;


public class TelaInicial extends AppCompatActivity {

    private Button BtnAbrirLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        BtnAbrirLogin = (Button) findViewById(R.id.btnFazerLogin);


        BtnAbrirLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAbrirTelaLogin = new Intent(TelaInicial.this, TelaDeLogin.class);
                startActivity(intentAbrirTelaLogin);
            }
        });
    }

}
