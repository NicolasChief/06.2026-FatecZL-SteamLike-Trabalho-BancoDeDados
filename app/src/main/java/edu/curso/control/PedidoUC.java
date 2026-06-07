package edu.curso.control;

import edu.curso.model.Jogo;
import edu.curso.model.Pedido;
import edu.curso.model.Usuario;

public class PedidoUC {

    public Pedido realizarPedido(
            Usuario usuario,
            Jogo jogo
    ) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                "Usuário inválido."
            );
        }

        if (jogo == null) {
            throw new IllegalArgumentException(
                "Jogo inválido."
            );
        }

        if (
            usuario.getSaldoConta()
            <
            jogo.getPreco()
        ) {

            throw new RuntimeException(
                "Saldo insuficiente."
            );
        }

        Pedido pedido =
            new Pedido("REALIZADO");

        pedido.setValorTotal(
            jogo.getPreco()
        );

        double novoSaldo =
            usuario.getSaldoConta()
            -
            jogo.getPreco();

        usuario.setSaldoConta(
            novoSaldo
        );

        return pedido;
    }

    public void cancelarPedido(
            Pedido pedido
    ) {

        if (pedido != null) {

            pedido.setStatusPedido(
                "CANCELADO"
            );
        }
    }
}
