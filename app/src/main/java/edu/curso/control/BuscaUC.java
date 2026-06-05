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
        if (nome == null || nome.trim().isEmpty()) {
            return listarTodos();
        }
        String termo = nome.toLowerCase();
        List<Jogo> resultado = new ArrayList<>();
        for (Jogo j : catalogo) {
            if (j.getNome().toLowerCase().contains(termo)) {
                resultado.add(j);
            }
        }
        resultado.sort((j1, j2) -> {
            int i1 = j1.getNome().toLowerCase().indexOf(termo);
            int i2 = j2.getNome().toLowerCase().indexOf(termo);
            if (i1 != i2) {
                return Integer.compare(i1, i2);
            }
            return j1.getNome().compareToIgnoreCase(j2.getNome());
        });
        return resultado;
    }
}