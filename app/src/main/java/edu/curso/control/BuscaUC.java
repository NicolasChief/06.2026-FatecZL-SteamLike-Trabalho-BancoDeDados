package edu.curso.control;

import java.util.ArrayList;
import java.util.List;

import edu.curso.model.Jogo;

public class BuscaUC {

    private List<Jogo> catalogo = new ArrayList<>();

    public void adicionarJogo(Jogo jogo) {
        catalogo.add(jogo);
    }

    public List<Jogo> listarTodos() {
        return catalogo;
    }

    public List<Jogo> pesquisarJogo(String nome) {
        List<Jogo> resultado = new ArrayList<>();
        for (Jogo j : catalogo) {
            if (j.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(j);
            }
        }
        return resultado;
    }
}