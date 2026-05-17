package edu.curso.model;

import java.util.ArrayList;
import java.util.List;

public class Desenvolvedora extends Estudio {

    private List<Jogo> portifolio = new ArrayList<>();

    public Desenvolvedora() {
        super();
    }

    public Desenvolvedora(String nome, String cnpjcpf) {
        super(nome, cnpjcpf);
    }

    public List<Jogo> getPortifolio() {
        return portifolio;
    }
    public void setPortifolio(List<Jogo> portifolio) {
        this.portifolio = portifolio;
    }

    public void adicionarJogo(Jogo jogo) {
        this.portifolio.add(jogo);
    }

}
