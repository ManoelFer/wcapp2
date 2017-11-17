package Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import DAO.ConfiguracaoFirebase;


/**
 * Created by Manoel on 19/10/2017.
 */

public class Usuario
{

    private String id;
    private String url;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String sexo;
    private String email;
    private String senha;

    public Usuario(){

    }
    public void salvar(){

        //Além de Salvar na autenticação do fire base, também cria uma tabela no banco, chamada de filhos ou seja Child
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuario").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("fotoPerfil",getUrl());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("sobrenome",getSobrenome());
        hashMapUsuario.put("dataNascimento",getDataNascimento());
        hashMapUsuario.put("sexo",getSexo());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("senha",getSenha());

        return hashMapUsuario;
    }

    public Usuario(String url, String email) {
        this.url = url;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String setUrl(String url) {
        this.url = url;
        return url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
