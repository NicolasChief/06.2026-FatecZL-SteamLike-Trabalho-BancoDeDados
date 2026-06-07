package edu.curso.control;

import java.util.ArrayList;
import java.util.List;

import edu.curso.model.Desenvolvedora;
import edu.curso.model.Jogo;
import edu.curso.model.Usuario;
import edu.curso.view.BuscaUI;
import edu.curso.view.PedidoUI;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class BuscaUC {

    private List<Jogo> catalogo = new ArrayList<>();
    private Object usuarioOuDesenvolvedor;

    public void adicionarJogo(Jogo jogo) {
        catalogo.add(jogo);
    }

    public List<Jogo> listarTodos() {
        return catalogo;
    }

    public List<Jogo> pesquisarJogo(String nome) {
        List<Jogo> resultado = new ArrayList<>();
        for (Jogo j : catalogo) {
            if (j.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(j);
            }
        }
        return resultado;
    }

    public void setUsuarioOuDesenvolvedor(Object usuario) {
        this.usuarioOuDesenvolvedor = usuario;
    }

    public boolean isDesenvolvedor() {
        return usuarioOuDesenvolvedor instanceof Desenvolvedora;
    }

    public void atualizarTabela(String filtro, ObservableList<Jogo> jogosObservaveis) {
        List<Jogo> resultados = pesquisarJogo(filtro);
        jogosObservaveis.setAll(resultados);
    }

    public void abrirPedido(Stage stage, Jogo jogo) {
        if (usuarioOuDesenvolvedor instanceof Usuario) {
            PedidoUI.mostrarPedido(stage, jogo, (Usuario) usuarioOuDesenvolvedor);
        }
    }

    public static void mostrarBusca(Stage stage, Object usuarioOuDesenvolvedor) {
        BuscaUC buscaUC = new BuscaUC();
        buscaUC.setUsuarioOuDesenvolvedor(usuarioOuDesenvolvedor);
        BuscaUI buscaUI = new BuscaUI();
        buscaUI.setBuscaUC(buscaUC);
        buscaUI.start(stage);
    }

    public void adicionarDadosDemo() {
        adicionarJogo(new Jogo("Cyberpunk 2077", new java.util.Date(), 149.90, 70, "Ação RPG futurista", "PC/PS/Xbox", false, "CD Projekt", "CD Projekt"));
        adicionarJogo(new Jogo("The Witcher 3", new java.util.Date(), 129.90, 50, "RPG medieval", "PC/PS/Xbox", true, "CD Projekt", "CD Projekt"));
        adicionarJogo(new Jogo("Grand Theft Auto V", new java.util.Date(), 99.90, 80, "Ação e aventura", "PC/PS/Xbox", true, "Rockstar", "Rockstar"));
        adicionarJogo(new Jogo("Horizon Zero Dawn", new java.util.Date(), 119.90, 60, "Ação e aventura", "PC/PS", false, "Sony", "Guerrilla Games"));
        adicionarJogo(new Jogo("God of War", new java.util.Date(), 139.90, 55, "Ação e aventura mitológica", "PC/PS", false, "Sony", "Santa Monica Studio"));
    }
}
