package com.example.manoel.wolfchatapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import javax.security.auth.login.LoginException;

import Entidades.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button sair;
    private Button chat;
    public String nomeDoUsuario;
    private TelaDeLogin telaDeLogin;
    private Usuario usuarios;
    private TextView nomeUsu;
    private TelaDeLogin id;
    private FirebaseAuth mAuth;

// ...



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*
        mAuth = FirebaseAuth.getInstance();




        sair = (Button)findViewById(R.id.sair);
        chat = (Button)findViewById(R.id.entrarChat);
        nomeUsu = (TextView)findViewById(R.id.nomeUsuario);




        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReferenceFromUrl("https://wolfchatapp.firebaseio.com/");

        DatabaseReference novaRef = ref.child("usuario/amFvQGdtYWlsLmNvbg==");
// Attach a listener to read the data at our posts reference

        novaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map <String, String> map = (Map)dataSnapshot.getValue();

                String nomezinho = map.get("nome");

                nomeUsu.setText(nomezinho);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


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
