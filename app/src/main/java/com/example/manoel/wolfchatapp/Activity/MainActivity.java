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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
    private int chaves = 0;

// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();


        sair = (Button) findViewById(R.id.sair);
        chat = (Button) findViewById(R.id.entrarChat);
        nomeUsu = (TextView) findViewById(R.id.nomeUsuario);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            final String emailUser = user.getEmail();


            System.out.println(emailUser);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReferenceFromUrl("https://wolfchatapp.firebaseio.com/").child("usuario");

            ref.orderByChild("email").equalTo(emailUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Usuario u = d.getValue(Usuario.class);
                        System.out.println(u);
                        String dataNascimento = u.getDataNascimento();
                        System.out.println(dataNascimento);
                        String emailDoUsuario = u.getEmail();
                        System.out.println(emailDoUsuario);
                        String idDoUsuario = u.getId();
                        System.out.println(idDoUsuario);
                        String nomeDoUsuario = u.getNome();
                        System.out.println(nomeDoUsuario);
                        String senhaDoUsuario = u.getSenha();
                        String sexoDoUsuario = u.getSexo();
                        System.out.println(sexoDoUsuario);
                        String sobrenomeDoUsuario = u.getSobrenome();
                        System.out.println(sobrenomeDoUsuario);

                        if(sexoDoUsuario == "Feminino") {
                            nomeUsu.setText("Seja bem vinda: " + nomeDoUsuario);
                        }else{
                            nomeUsu.setText("Seja bem vindo: " + nomeDoUsuario);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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
    }
    public void abrirLoginUsuario() {
        Intent intent = new Intent(MainActivity.this, TelaDeLogin.class);
        startActivity(intent);
        finish();
    }

    public void abrirSalaDeBatePapo() {
        Intent intent = new Intent(MainActivity.this, TelaDeChat.class);
        startActivity(intent);
        finish();
    }

    }
