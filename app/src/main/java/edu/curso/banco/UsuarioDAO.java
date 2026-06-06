package edu.curso.banco;

import java.util.List;
import edu.curso.model.Usuario;

public interface UsuarioDAO {
    void cadastrar(Usuario usuario);
    List<Usuario> consultarPorNome(String nome);
    void atualizar(long id, Usuario usuario);
    void apagar(long id);
}
