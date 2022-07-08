package com.example.crud;

public class Cliente {
    int idcliente;
    String nome;
    String email;
    String telefone;

    public int getIdcliente() {
        return idcliente;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente() {

    }
    public Cliente(int _idcliente,String _nome,String _email, String _telefone) {
        this.idcliente = _idcliente;
        this.nome = _nome;
        this.email = _email;
        this.telefone = _telefone;
    }
    public Cliente(String _nome,String _email, String _telefone) {
        this.nome = _nome;
        this.email = _email;
        this.telefone = _telefone;
    }
}
