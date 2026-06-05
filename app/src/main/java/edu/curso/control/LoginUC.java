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
}