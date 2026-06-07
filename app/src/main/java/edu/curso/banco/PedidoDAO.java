package edu.curso.banco;

import java.util.List;
import edu.curso.model.Pedido;

public interface PedidoDAO {

    void cadastrar(Pedido pedido);
    List<Pedido> consultar();
    void atualizar(long id, Pedido pedido);
    void apagar(long id);
}
