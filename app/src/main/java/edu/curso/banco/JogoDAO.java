package edu.curso.banco;

import java.util.List;
import edu.curso.model.Jogo;

public interface JogoDAO {
    void cadastrar(Jogo jogo);
    List<Jogo> consultarPorNome(String nome);
    void atualizar(long id, Jogo jogo);
    void apagar(long id);
}
