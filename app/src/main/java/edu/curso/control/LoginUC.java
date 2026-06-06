package edu.curso.control;


import edu.curso.banco.bancofaketeste;  //substituir
import edu.curso.model.Usuario;


public class LoginUC {

    public String logar(String nome, String senha) {

        for (Usuario u : bancofaketeste.lista) // trocar para sql 
        {

            if (u.getNome().equals(nome)) {

                if (
                    u.getSenha().equals(senha)
                ) {

                    return "LOGIN_OK";

                } else {

                    return "SENHA_INCORRETA";
                }
            }
        }

        return "USUARIO_NAO_EXISTE";
    }

    public Usuario autenticar(String nome, String senha) {
        for (Usuario u : bancofaketeste.lista) {
            if (//u.getNome().equals(nome) && u.getSenha().equals(senha)) {
                u.getNome().equals("admin") && u.getSenha().equals("admin")) {
                return u;
            }
        }
        return null;
    }
}