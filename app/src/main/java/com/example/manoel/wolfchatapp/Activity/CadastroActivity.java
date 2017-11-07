package com.example.manoel.wolfchatapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manoel.wolfchatapp.Helper.Base64Custom;
import com.example.manoel.wolfchatapp.Helper.Preferencias;
import com.example.manoel.wolfchatapp.Mascaras.Mask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import DAO.ConfiguracaoFirebase;
import Entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroActivity extends AppCompatActivity {

    private CircleImageView edtCadfotoPerfil;
    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadDataNascimento;
    private RadioButton edtCadMasculino;
    private RadioButton EdtCadFeiminino;
    private EditText edtCadEmail;
    private EditText EdtCadSenha;
    private EditText edtCadConfirmaSenha;
    private Button SALVAR;
    private Usuario usuarios;
    private FirebaseAuth autenticacao;
    private String fotoDePerfil;
    private DatabaseReference databaseReference;

    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCadfotoPerfil = (CircleImageView) findViewById(R.id.cadFotoPerfil);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadSobrenome = (EditText) findViewById(R.id.edtCadSobrenome);
        edtCadDataNascimento = (EditText) findViewById(R.id.edtCadAniversario);
        edtCadMasculino = (RadioButton) findViewById(R.id.rbFeiminino);
        EdtCadFeiminino = (RadioButton) findViewById(R.id.rbMasculino);
        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        EdtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtCadConfirmaSenha = (EditText) findViewById(R.id.edtConfirmarSenha);
        SALVAR = (Button) findViewById(R.id.btnSalvar);

        edtCadDataNascimento = (EditText) findViewById(R.id.edtCadAniversario);
        edtCadDataNascimento.addTextChangedListener(Mask.insert("##/##/####", edtCadDataNascimento));

        SALVAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EdtCadSenha.getText().toString().equals(edtCadConfirmaSenha.getText().toString()))
                {
                    usuarios = new Usuario();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setSobrenome(edtCadSobrenome.getText().toString());
                    usuarios.setDataNascimento(edtCadDataNascimento.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(EdtCadSenha.getText().toString());

                    if (edtCadMasculino.isChecked())
                    {
                        usuarios.setSexo("Feminino");
                    }
                    else
                    {
                        usuarios.setSexo("Masculino");
                    }

                    cadastrarUsuario();

                }
                else
                {
                    Toast.makeText(CadastroActivity.this, "As senhas não correspondem!", Toast.LENGTH_LONG).show();
                }
            }
        });

        edtCadfotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT); //Obter Todas as fotos da galeria
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecione uma imagem"),PHOTO_SEND);
            }
        });

    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword
                (
                        usuarios.getEmail(),
                        usuarios.getSenha()
                ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(CadastroActivity.this, "Você Entrou Para Alcatéia! Boa Caçada!", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Custom.codificandoBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLoginUsuario();

                }
                else
                {
                    String erroExcecao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    }catch (FirebaseAuthEmailException e){
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres de letras e números";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Este e-mail já está cadastrado no sistema";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,"ERRO: "+erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, TelaDeLogin.class);
        startActivity(intent);
        finish();
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK)
        {

            Uri u = data.getData();
            storageReference = firebaseStorage.getReference("imagens_chat");//imagens do meu chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());

            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u = taskSnapshot.getDownloadUrl();
                    MenssagensEnviadas m = new MenssagensEnviadas(edtCadNome.getText()+
                            " te enviou uma foto!",u.toString(),edtCadNome.getText().toString(),
                            fotoDePerfil,"2",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                }
            });

        }
        else if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = firebaseStorage.getReference("fotos_perfis");//imagens do meu chat
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());

            fotoReferencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri u = taskSnapshot.getDownloadUrl();
                    fotoDePerfil = u.toString();
                    MenssagensEnviadas m = new MenssagensEnviadas(edtCadNome.getText()+" alterou sua foto de perfil!!",
                            u.toString(), edtCadNome.getText().toString(),fotoDePerfil, "2", ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                    Glide.with(CadastroActivity.this).load(u.toString()).into(edtCadfotoPerfil);
                }
            });
        }
    }*/

}
