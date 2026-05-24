package edu.curso.control;

import edu.curso.model.Jogo;

public class PublicarUC {

    public void publicarJogo() {
        
    }

    public void deslistarJogo() {
        
    }

    public void alterarJogo() {
        
    }

    public Jogo toEntityJogo() {

        Jogo jogo = new Jogo(null, null, 0, 0, null, null, false, null, null);

        jogo.setNome(jogo.getNome());
        jogo.setPreco(jogo.getPreco());
        jogo.setEspacoArmazenamento(jogo.getEspacoArmazenamento());
        jogo.setDescricaoJogo(jogo.getDescricaoJogo());

        return jogo;
    }

}
