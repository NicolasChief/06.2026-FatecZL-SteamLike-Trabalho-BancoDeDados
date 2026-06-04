package edu.curso.model;

import java.util.Date;

public class Jogo {

    private String nome;
    private Date dataLancamento;
    private double preco;
    private double espacoArmazenamento;
    private String descricaoJogo;
    private String descricaoSpecs;
    private boolean statusAquicicao;
    private String publicadora;
    private String desenvolvedora;

    public Jogo(String nome, Date dataLancamento, double preco, double espacoArmazenamento,
            String descricaoJogo, String descricaoSpecs, boolean statusAquicicao, String publicadora,
            String desenvolvedora) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.preco = preco;
        this.espacoArmazenamento = espacoArmazenamento;
        this.descricaoJogo = descricaoJogo;
        this.descricaoSpecs = descricaoSpecs;
        this.statusAquicicao = statusAquicicao;
        this.publicadora = publicadora;
        this.desenvolvedora = desenvolvedora;
    }

    public String getNome() {
        return nome;
    }
    public String setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }
    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getEspacoArmazenamento() {
        return espacoArmazenamento;
    }
    public void setEspacoArmazenamento(double espacoArmazenamento) {
        this.espacoArmazenamento = espacoArmazenamento;
    }

    public String getDescricaoJogo() {
        return descricaoJogo;
    }
    public void setDescricaoJogo(String descricaoJogo) {
        this.descricaoJogo = descricaoJogo;
    }

    public String getDescricaoSpecs() {
        return descricaoSpecs;
    }
    public void setDescricaoSpecs(String descricaoSpecs) {
        this.descricaoSpecs = descricaoSpecs;
    }

    public boolean getStatusAquicicao() {
        return statusAquicicao;
    }
    public void setStatusAquicicao(boolean statusAquicicao) {
        this.statusAquicicao = statusAquicicao;
    }

    public String getPublicadora() {
        return publicadora;
    }
    public void setPublicadora(String publicadora) {
        this.publicadora = publicadora;
    }

    public String getDesenvolvedora() {
        return desenvolvedora;
    }
    public void setDesenvolvedora(String desenvolvedora) {
        this.desenvolvedora = desenvolvedora;
    }

    public void exibirDetalhes() {
        System.out.println("Nome: " + nome);
        System.out.println("Data de lançamento: " + dataLancamento);
        System.out.println("Preço: " + preco);
        System.out.println("Espaço de armazenamento: " + espacoArmazenamento);
        System.out.println("Descrição: " + descricaoJogo);
        System.out.println("Requisitos: " + descricaoSpecs);
        System.out.println("Status de aquisição: " + statusAquicicao);
        System.out.println("Publicadora: " + publicadora);
        System.out.println("Desenvolvedora: " + desenvolvedora);
    }

}
