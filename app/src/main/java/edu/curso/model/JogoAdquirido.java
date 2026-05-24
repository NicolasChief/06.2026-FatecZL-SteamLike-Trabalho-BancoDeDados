package edu.curso.model;

import java.util.Date;

public class JogoAdquirido {

    private Date dataAquisicao;
    private Jogo jogo; 

    public JogoAdquirido(Date dataAquisicao, Jogo jogo) {
        this.dataAquisicao = dataAquisicao;
        this.jogo = jogo;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }
    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Jogo getJogo() {
        return jogo;
    }
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public void rodar() {
        if (jogo != null) {
            System.out.println("Rodando: " + jogo.getNome());
        } else {
            System.out.println("Nenhum jogo disponível para rodar.");
        }
    }

    public void desinstalarJogo() {
        if (jogo != null) {
            System.out.println("Desinstalando: " + jogo.getNome());
            jogo = null;
        } else {
            System.out.println("Nenhum jogo instalado para desinstalar.");
        }
    }

}
