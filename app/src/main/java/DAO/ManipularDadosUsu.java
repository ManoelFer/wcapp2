package DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entidades.Usuario;

/**
 * Created by Manoel on 01/11/2017.
 */

public class ManipularDadosUsu {

    public String dataNascimento ;
    public String emailDoUsuario ;
    public String idDoUsuario ;
    public String nomeDoUsuario ;
    public String senhaDoUsuario ;
    public String sexoDoUsuario ;
    public String sobrenomeDoUsuario ;

    public void retornaDadosDoUsu() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String emailUser = user.getEmail();// Função para recuperar o e-mail do usuário logado.

        final FirebaseDatabase database = FirebaseDatabase.getInstance();//Função do Firebase para acesssar o banco de dados
        DatabaseReference ref = database.getReferenceFromUrl("https://wolfchatapp.firebaseio.com/").child("usuario");//Referência
        //pela Url, para declarar, por qual caminho o sistema deve seguir para encontrar os dados desejados

        ref.orderByChild("email").equalTo(emailUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Usuario u = d.getValue(Usuario.class);//Seta todos os valores na minha entidade Usuário, onde se localiza
                    //Meus geters e seters;

                    //Popula as variáveis criadas, para receber os valores, setados em minha classe.
                    dataNascimento = u.getDataNascimento();
                    emailDoUsuario = u.getEmail();
                    idDoUsuario = u.getId();
                    nomeDoUsuario = u.getNome();
                    senhaDoUsuario = u.getSenha();
                    sexoDoUsuario = u.getSexo();
                    sobrenomeDoUsuario = u.getSobrenome();
                    //final do penúltimo comentário

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
