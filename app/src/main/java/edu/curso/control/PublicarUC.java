package edu.curso.control;

import edu.curso.banco.JogoDAOImpl;
import edu.curso.model.Jogo;

public class PublicarUC {

    public void publicarJogo(Jogo jogo) {
        new JogoDAOImpl().cadastrar(jogo);
    }

    public void deslistarJogo(String nome) {

        Jogo remover = null;

        for (Jogo j : JogoDAOImpl.jogos) //substituir
            {

            if (j.getNome().equals(nome))  {

                remover = j;
                break;
            }
        }

        if (remover != null) {

            JogoDAOImpl.jogos.remove(remover);
        }
    }

    public void alterarJogo(
            String nome,
            Jogo novoJogo
    ) {

        for (Jogo j : JogoDAOImpl.jogos) //substituir
            {

            if (j.getNome().equals(nome)) {

                j.setNome(
                        novoJogo.getNome());

                j.setPreco(
                        novoJogo.getPreco());

                j.setDescricaoJogo(
                        novoJogo.getDescricaoJogo());

                j.setEspacoArmazenamento(
                        novoJogo.getEspacoArmazenamento());

                break;
            }
        }
    }
}