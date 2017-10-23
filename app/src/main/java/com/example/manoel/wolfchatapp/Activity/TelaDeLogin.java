package com.example.manoel.wolfchatapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Entidades.Usuario;

public class TelaDeLogin extends AppCompatActivity {

    private Button email_sign_in_button;
    private Button registrar;
    private Usuario usuarios;
    private FirebaseAuth mAuth;
    private EditText mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_login);

            mAuth = FirebaseAuth.getInstance();

            mEmailView = (EditText) findViewById(R.id.email);
            mPasswordView = (EditText)findViewById(R.id.password);
            email_sign_in_button = (Button)findViewById(R.id.email_sign_in_button);
            registrar = (Button)findViewById(R.id.registrar);


            mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    abrirTelaPrincipal();
                }
            }
        };

            email_sign_in_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        startSignIn();
                }


            });
            registrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TelaDeLogin.this,CadastroActivity.class);
                    startActivity(intent);
                }
            });
        }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn() {

        usuarios = new Usuario();
        usuarios.setEmail(mEmailView.getText().toString());
        usuarios.setSenha(mPasswordView.getText().toString());

        if(TextUtils.isEmpty(usuarios.getEmail()) || TextUtils.isEmpty(usuarios.getSenha()))
        {
            Toast.makeText(TelaDeLogin.this,"Pocha Lobo :( ... Preenche os campos ae!!!",Toast.LENGTH_SHORT).show();

        }
        else {

            mAuth.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(TelaDeLogin.this, "Deu certo "+usuarios.getEmail()+" " +
                                        "De senha: "+usuarios.getSenha(), Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(TelaDeLogin.this, "Deu errado "+usuarios.getEmail()+" " +
                                        "De senha: "+usuarios.getSenha(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
    }
    private void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal = new Intent(this,MainActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }
}
