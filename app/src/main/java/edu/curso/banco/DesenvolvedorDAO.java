package edu.curso.banco;

import java.util.List;
import edu.curso.model.Desenvolvedora;

public interface DesenvolvedorDAO {

    void cadastrar(Desenvolvedora dev);
    List<Desenvolvedora> consultarPorNome(String nome);
    void atualizar(long id, Desenvolvedora dev);
    void apagar(long id);
}