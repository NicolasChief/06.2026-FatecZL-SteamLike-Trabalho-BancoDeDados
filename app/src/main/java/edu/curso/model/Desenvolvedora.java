package edu.curso.model;

import java.util.ArrayList;
import java.util.List;

public class Desenvolvedora extends Estudio {

    private String email;
    private String senha;
    private String telefone;
    private List<Jogo> portifolio = new ArrayList<>();

    public Desenvolvedora() {
        super();
    }

    public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getSenha() {
    return senha;
}

public void setSenha(String senha) {
    this.senha = senha;
}

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}

    public Desenvolvedora(String nome, String cnpjcpf) {
        super(nome, cnpjcpf);
    }

    public List<Jogo> getPortifolio() {
        return portifolio;
    }
    public void setPortifolio(List<Jogo> portifolio) {
        this.portifolio = portifolio;
    }

    public void adicionarJogo(Jogo jogo) {
        this.portifolio.add(jogo);
    }

}
