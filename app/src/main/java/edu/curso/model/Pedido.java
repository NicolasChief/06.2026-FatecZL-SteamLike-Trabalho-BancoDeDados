package edu.curso.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String statusPedido;
    private double valorTotal;
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }
    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemPedido itemPedido) {
        this.itens.add(itemPedido);
    }

    public void calcularPreco() {
        this.valorTotal = itens.stream().mapToDouble(ItemPedido::calcularTotal).sum();
    }

}
