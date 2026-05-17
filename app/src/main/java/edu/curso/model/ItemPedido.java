package edu.curso.model;

public class ItemPedido {
    
    private int quantidade;
    private double precoUni;

    public ItemPedido() {
    }

    public ItemPedido(int quantidade, double precoUni) {
        this.quantidade = quantidade;
        this.precoUni = precoUni;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUni() {
        return precoUni;
    }
    public void setPrecoUni(double precoUni) {
        this.precoUni = precoUni;
    }

    public double calcularTotal() {
        return quantidade * precoUni;
    }

}
