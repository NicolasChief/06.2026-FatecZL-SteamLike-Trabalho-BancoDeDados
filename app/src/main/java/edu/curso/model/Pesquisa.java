package edu.curso.model;

import java.util.ArrayList;
import java.util.List;

public class Pesquisa {

    private List<Jogo> campoBusca = new ArrayList<>();

    public Pesquisa(List<Jogo> campoBusca) {
        this.campoBusca = campoBusca;
    }

    public List<Jogo> getCampoBusca() {
        return campoBusca;
    }
    public void setCampoBusca(List<Jogo> campoBusca) {
        this.campoBusca = campoBusca;
    }

    public void adicionarJogo(Jogo jogo) {
        this.campoBusca.add(jogo);
    }

    public void exibirPesquisa() {
        if (campoBusca.isEmpty()) {
            System.out.println("Nenhum resultado encontrado.");
            return;
        }

        for (Jogo jogo : campoBusca) {
            jogo.exibirDetalhes();
            System.out.println("---");
        }
    }

}
