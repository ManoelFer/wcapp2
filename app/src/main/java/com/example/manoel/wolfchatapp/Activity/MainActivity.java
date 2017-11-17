package com.example.manoel.wolfchatapp.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.LoginException;

import Entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private int i = 0;
    private Button sair;
    private Button chat;
    private CircleImageView fotoPerfil;
    public String nomeDoUsuario;
    private TelaDeLogin telaDeLogin;
    private Usuario usuarios;
    private TextView nomeUsu;
    private TelaDeLogin id;
    private FirebaseAuth mAuth;
    private int chaves = 0;

    private FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;
    private Uri imgUri;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;
    private boolean conferir = false;

    private List<Usuario> imgList;
    private AdapterFotoPerfil adapterFotoPerfil;
    private ProgressDialog progressDialog;
// ...


        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgList = new ArrayList<>();


        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        fotoPerfil = (CircleImageView) findViewById(R.id.FotoPerfil);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Aguarde enquanto a foto de perfil carrega...");
        progressDialog.show();

        sair = (Button) findViewById(R.id.sair);//recupera o valor do botão deslogar, da minha tela main activity
        chat = (Button) findViewById(R.id.entrarChat);//recupera o valor do botão entrar no chat.
        nomeUsu = (TextView) findViewById(R.id.nomeUsuario);//recupera a minha text view, onde apresenta as mensagens de boas vindas.

        databaseReference = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

        /*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Usuario u = snapshot.getValue(Usuario.class);
                    imgList.add(u);
                }

                adapterFotoPerfil = new AdapterFotoPerfil(MainActivity.this, R.layout.activity_main,imgList);

                //fotoPerfil.setAdapter(adapterFotoPerfil);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imgUri == null){
                    abrirFotoDePerfil();
            }

            }
        });*/

//------------------------------------------------------------------------------------------------------------------------------------

           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            final String emailUser = user.getEmail();// Função para recuperar o e-mail do usuário logado.

            FirebaseDatabase database = FirebaseDatabase.getInstance();//Função do Firebase para acesssar o banco de dados
            DatabaseReference ref = database.getReferenceFromUrl("https://wolfchatapp.firebaseio.com/").child(FB_DATABASE_PATH);//Referência
            //pela Url, para declarar, por qual caminho o sistema deve seguir para encontrar os dados desejados

            ref.orderByChild("email").equalTo(emailUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        Usuario u = d.getValue(Usuario.class);

                        String emailDoUsuario = u.getEmail();
                        String urlFoto = u.getUrl();

                       /* mStorageRef = firebaseStorage.getReferenceFromUrl("gs://wolfchatapp.appspot.com").child(FB_STORAGE_PATH + "/1510756995556.jpeg/");
                        long ONE_MEGA_BYTE = 1024 * 1024;
                        mStorageRef.getBytes(ONE_MEGA_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                fotoPerfil.setImageBitmap(bitmap);
                            }
                        });*/


                    }

                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

//-------------------------------------------------------------------------------------------------------------------------------------

            if (user != null) { // se o usuário for diferente de vazio, então o sistema passa para próxima etapa.



            database = FirebaseDatabase.getInstance();//Função do Firebase para acesssar o banco de dados
            ref = database.getReferenceFromUrl("https://wolfchatapp.firebaseio.com/").child("usuario");//Referência
            //pela Url, para declarar, por qual caminho o sistema deve seguir para encontrar os dados desejados

            ref.orderByChild("email").equalTo(emailUser).addValueEventListener(new ValueEventListener() {//Função para ordenar o filho,
                //também chamado de tabela, 'email', quando o mesmo for igual ao e-mail logado, ele lista os dados do usuário, através
                //do identificador 'email', assemelhando-se ao código sql 'Select'.
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {//Pega do início, todos valores contidos na child.

                        Usuario u = d.getValue(Usuario.class);//Seta todos os valores na minha entidade Usuário, onde se localiza
                        //Meus geters e seters;

                        //Popula as variáveis criadas, para receber os valores, setados em minha classe.
                        String dataNascimento = u.getDataNascimento();
                        String emailDoUsuario = u.getEmail();
                        String idDoUsuario = u.getId();
                        String nomeDoUsuario = u.getNome();
                        String senhaDoUsuario = u.getSenha();
                        String sexoDoUsuario = u.getSexo();
                        String sobrenomeDoUsuario = u.getSobrenome();
                        //final do penúltimo comentário


                        Calendar c1 = Calendar.getInstance();//Função para pegar a hora e a data do sistema.
                        int hora = c1.get(Calendar.HOUR_OF_DAY);//Popula a variável hora, com a hora do sistema.
                        int diaAtual = c1.get(Calendar.DAY_OF_MONTH);//Popula a variável diaAtual, com o dia do sistema.
                        int mesAtual = c1.get(Calendar.MONTH);//Popula a variável mesAtual, com o mês do sistema.
                        mesAtual = mesAtual + 1;//Como o java começa a contar os meses pelo 0, sendo que janeiro fica dado como mês
                        //0 no calendário, fevereiro mês 1, e assim por diante, devemos somar o mês atual, mais 1, para encontrarmos
                        //o valor real do mês!

                        //Como a data de nascimento do usuário, foi inserida como uma data completa, devemos dividi-la, em dia
                        //,mês e ano, para que assim, possamos saber, em que data o usuário, irá fazer aniversário.
                        String diaUsu = dataNascimento.substring(0, 2);//dia
                        String mesUsu = dataNascimento.substring(3, 5);//mês
                        String ano = dataNascimento.substring(8, 10);// Ano abreviado, com somente os dois últimos digitos.

                        //como o dia e o mês atual, são do tipo inteiro, fica mais fácil converter a data de nascimento, do que
                        // converter o dia atual do tipo date para String.
                        int diaUsu2 = Integer.parseInt(diaUsu);//conversão dia
                        int mesUsu2 = Integer.parseInt(mesUsu);//conversão mês


                        if (diaUsu2 == diaAtual && mesUsu2 == mesAtual) {//Se o dia atual e o mês atual, for igual ao dia do nascimento
                            //e o mês do nascimento, então, imprime uma mensagem parabernizando o usuário!
                            if (hora > 00 && hora < 12) {
                                if (sexoDoUsuario.equals("Feminino")) {// Se ele for do sexo feminino, ele imprime uma mensagem para mulher.
                                    nomeUsu.setText("Bom dia " + nomeDoUsuario + ", Feliz aniversário loba!");
                                } else {//Se não, ele imprime uma mensagem para homem.
                                    nomeUsu.setText("Bom dia " + nomeDoUsuario + ", Feliz aniversário lobo!");
                                }
                            } else if (hora > 12 && hora < 18) {
                                if (sexoDoUsuario.equals("Feminino")) {
                                    nomeUsu.setText("Boa tarde " + nomeDoUsuario + ", Feliz aniversário loba!");
                                } else {
                                    nomeUsu.setText("Boa tarde " + nomeDoUsuario + ", Feliz aniversário lobo!");
                                }
                            } else {

                                if (sexoDoUsuario.equals("Feminino")) {
                                    nomeUsu.setText("Boa noite " + nomeDoUsuario + ", Feliz aniversário loba!");
                                } else {
                                    nomeUsu.setText("Boa noite " + nomeDoUsuario + ", Feliz aniversário lobo!");
                                }

                            }


                        } else //Se não for a data de aniversário do usuário, ele imprime uma mensagem normal de bom dia, boa tarde e
                        //boa noite, de acordo com o horário do sistema.
                        {
                            if (hora > 00 && hora < 12) {
                                if (sexoDoUsuario.equals("Feminino")) {
                                    nomeUsu.setText("Bom dia " + nomeDoUsuario + ", seja bem vinda!");
                                } else {
                                    nomeUsu.setText("Bom dia " + nomeDoUsuario + ", seja bem vindo!");
                                }
                            } else if (hora > 12 && hora < 18) {
                                if (sexoDoUsuario.equals("Feminino")) {
                                    nomeUsu.setText("Boa tarde " + nomeDoUsuario + ", seja bem vinda!");
                                } else {
                                    nomeUsu.setText("Boa tarde " + nomeDoUsuario + ", seja bem vindo!");
                                }

                            } else {

                                if (sexoDoUsuario.equals("Feminino")) {
                                    nomeUsu.setText("Boa noite " + nomeDoUsuario + ", seja bem vinda!");
                                } else {
                                    nomeUsu.setText("Boa noite " + nomeDoUsuario + ", seja bem vindo!");
                                }

                            }
                        }
                    }
                }//Final do meu Override de sucesso!

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            //Se o usuário clicar no botão 'Deixar alcatéia', ele será deslogado e redirecionado para tela de
            // login, através da seguinte função!
            sair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    abrirLoginUsuario();
                }


            });

            //Se o usuário clicar no botão 'Huivar com outro Lobo', ele será redirecionado para sala de chat, através da seguinte função!
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrirSalaDeBatePapo();
                }
            });

        }
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

        }
        try {



            Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            fotoPerfil.setImageBitmap(bm);

            salvarFotoNoFirebase();


        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


        //Método para abrir tela de login

    public void abrirLoginUsuario() {
        Intent intent = new Intent(MainActivity.this, TelaDeLogin.class);
        startActivity(intent);
        finish();
    }

    //Método para abrir tela de bate papo
    public void abrirSalaDeBatePapo() {
        Intent intent = new Intent(MainActivity.this, TelaDeChat.class);
        startActivity(intent);
        finish();
    }



    public void abrirFotoDePerfil() {
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione a imagem!"), REQUEST_CODE);

    }

    public String getImagemExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void salvarFotoNoFirebase() {
        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Salvando Foto de Perfil!");
            dialog.show();

            //Pegar a referência do storage
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImagemExt(imgUri));

            //Adicionar local para referência
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(), "Imagem uploaded", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    final String emailUser = user.getEmail();

                    Usuario u = new Usuario(taskSnapshot.getDownloadUrl().toString(), emailUser);

                    //salvar imagem no firebase database
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(u);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                            dialog.dismiss();

                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, selecione a imagem!", Toast.LENGTH_SHORT).show();
        }
    }



}
