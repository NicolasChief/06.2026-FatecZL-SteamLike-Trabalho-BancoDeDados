package edu.curso.control;

import java.util.ArrayList;
import java.util.List;

import edu.curso.model.Pedido;

public class PedidoUC {

    private List<Pedido> pedidos =
            new ArrayList<>();

    public void realizarPedido(
            Pedido pedido
    ) {

        pedido.calcularPreco();

        pedido.setStatusPedido(
                "CONCLUIDO"
        );

        pedidos.add(pedido);
    }

    public void deletarPedido(
            Pedido pedido
    ) {

        pedidos.remove(pedido);
    }

    public List<Pedido> listarPedidos() {

        return pedidos;
    }
    
}
