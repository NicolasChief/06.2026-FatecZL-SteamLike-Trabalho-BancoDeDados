package edu.curso.banco;

import java.util.Date;
import edu.curso.model.Jogo;

public class TesteJogoDB {
    public static void main(String[] args) {
        System.out.println("=== Teste de Cadastro de Jogo ===");
        
        try {
            // Criar um novo jogo de teste
            Jogo jogoTeste = new Jogo(
                "Jogo Teste " + System.currentTimeMillis(),
                new Date(),
                49.99,
                50.0,
                "Um jogo de teste para validar a persistência",
                "Requer Windows 10, 8GB RAM",
                false,
                "",
                ""
            );
            
            System.out.println("\nCriando nova instância de JogoDAOImpl...");
            JogoDAOImpl jogoDAO = new JogoDAOImpl();
            
            System.out.println("\nCadastrando jogo...");
            jogoDAO.cadastrar(jogoTeste);
            
            System.out.println("\nConsultando jogos cadastrados...");
            var jogos = jogoDAO.consultarPorNome("");
            System.out.println("Total de jogos: " + jogos.size());
            for (Jogo j : jogos) {
                System.out.println("- " + j.getNome() + " (Dev: " + j.getDesenvolvedora() + ", Pub: " + j.getPublicadora() + ")");
            }
            
        } catch (Exception e) {
            System.out.println("Erro durante o teste:");
            e.printStackTrace();
        }
    }
}
