package edu.curso.model;

public class Estudio {
 
    private String nome;
    private String cnpjcpf;

    public Estudio() {
    }

    public Estudio(String nome, String cnpjcpf) {
        this.nome = nome;
        this.cnpjcpf = cnpjcpf;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpjcpf() {
        return cnpjcpf;
    }
    public void setCnpjcpf(String cnpjcpf) {
        this.cnpjcpf = cnpjcpf;
    }

}
