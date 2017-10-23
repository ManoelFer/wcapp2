package com.example.manoel.wolfchatapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.security.auth.login.LoginException;

import Entidades.Usuario;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button sair;
    private Button chat;
    private DatabaseReference mDatabase;
    public String nomeDoUsuario;
    private TelaDeLogin telaDeLogin;
    private Usuario usuarios;
// ...



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();




        sair = (Button)findViewById(R.id.sair);
        chat = (Button)findViewById(R.id.entrarChat);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                abrirLoginUsuario();
            }


        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirSalaDeBatePapo();
            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(MainActivity.this, TelaDeLogin.class);
        startActivity(intent);
        finish();
    }
    public void abrirSalaDeBatePapo(){
        Intent intent = new Intent(MainActivity.this, TelaDeChat.class);
        startActivity(intent);
        finish();
    }


}
