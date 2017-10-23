package com.example.manoel.wolfchatapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;



public class TelaDeChat extends AppCompatActivity {

    private AdapterMenssagens adapterMenssagens;

    private CircleImageView fotoPerfil;
    private TextView nome;
    private RecyclerView rvMenssagens;
    private EditText txtMenssagem;
    private Button btnEnviarMenssagem;
    private ImageButton btnEnviarFoto;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoDePerfil;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_chat);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nome = (TextView) findViewById(R.id.nome);
        rvMenssagens = (RecyclerView) findViewById(R.id.rvMensagens);
        txtMenssagem = (EditText) findViewById(R.id.textoMenssagem);
        btnEnviarMenssagem = (Button) findViewById(R.id.btnEnviarMenssagem);
        btnEnviarFoto = (ImageButton)findViewById(R.id.btnEnviarFoto);
        fotoDePerfil = "";

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("wolfchat");//Sala de chat (nome)

        firebaseStorage = FirebaseStorage.getInstance();

        adapterMenssagens = new AdapterMenssagens(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMenssagens.setLayoutManager(l);
        rvMenssagens.setAdapter(adapterMenssagens);


        btnEnviarMenssagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new MenssagensEnviadas(txtMenssagem.getText().toString(), nome.getText().toString(),"","1"
                        , ServerValue.TIMESTAMP));//Função de horas, ele envia para um servidor que se encarrega de voltar a hora certa
                txtMenssagem.setText("");
            }
        });

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT); //Obter Todas as fotos da galeria
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecione uma imagem"),PHOTO_PERFIL);//Código para armazenar a Imagem, retorna 1 se a mensagem for inserida corretamente!
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT); //Obter Todas as fotos da galeria
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecione uma imagem"),PHOTO_SEND);//Código para armazenar a Imagem, retorna 1 s
            }
        });

        //Função de rolagem de acordo com o que a lista atualiza
        adapterMenssagens.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MenssagensRecebidas m = dataSnapshot.getValue(MenssagensRecebidas.class);
                adapterMenssagens.addMenssagem(m);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setScrollbar(){
        rvMenssagens.scrollToPosition(adapterMenssagens.getItemCount()-1);
    }
    //Termina Aqui

    @Override
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
                    MenssagensEnviadas m = new MenssagensEnviadas("Manoel te enviou uma foto!",u.toString(),nome.getText().toString(),
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
                    MenssagensEnviadas m = new MenssagensEnviadas("Manoel alterou sua foto de perfil!!",
                            u.toString(), nome.getText().toString(),fotoDePerfil, "2", ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(m);
                    Glide.with(TelaDeChat.this).load(u.toString()).into(fotoPerfil);
                }
            });
        }
    }
}
