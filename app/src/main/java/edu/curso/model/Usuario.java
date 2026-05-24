package edu.curso.model;

import java.util.Date;

public class Usuario {

    private String nome;
    private Date dataNasc;
    private String email;
    private String senha;
    private String telefone;
    private double saldoConta;

    public Usuario(String nome, Date dataNasc, String email, String senha, String telefone, double saldoConta) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.saldoConta = saldoConta;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
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

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getSaldoConta() {
        return saldoConta;
    }
    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    public void logar() {
        System.out.println("Usuário " + nome + " conectado.");
    }

}
