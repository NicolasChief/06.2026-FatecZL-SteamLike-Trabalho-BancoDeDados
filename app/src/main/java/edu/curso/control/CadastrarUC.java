package edu.curso.control;

import java.time.ZoneId;
import java.util.Date;

import edu.curso.banco.DesenvolvedorDAOImpl;
import edu.curso.banco.UsuarioDAOImpl;
import edu.curso.model.Desenvolvedora;
import edu.curso.model.Usuario;

public class CadastrarUC {

	public void cadastrarUsuario(String nome, java.time.LocalDate dataSelecionada, String email, String senha, String telefone) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome é obrigatório");
		}
		if (dataSelecionada == null) {
			throw new IllegalArgumentException("Data de nascimento é obrigatória");
		}
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email é obrigatório");
		}
		if (senha == null || senha.isEmpty()) {
			throw new IllegalArgumentException("Senha é obrigatória");
		}

		Date dataNasc = Date.from(dataSelecionada.atStartOfDay(ZoneId.systemDefault()).toInstant());

		if (telefone != null && !telefone.isEmpty() && !telefone.matches("\\d{10}|\\d{11}")) {
			throw new IllegalArgumentException("Telefone inválido. Use apenas dígitos (10 ou 11 caracteres).");
		}

		Usuario usuario = new Usuario(nome.trim(), dataNasc, email.trim(), senha, (telefone == null ? null : telefone.trim()), 0.0);
		new UsuarioDAOImpl().cadastrar(usuario);
	}

	public void cadastrarDesenvolvedor(String nome, String cnpj, String email, String senha, String telefone) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome da desenvolvedora é obrigatório");
		}
		if (cnpj == null || cnpj.trim().isEmpty()) {
			throw new IllegalArgumentException("CNPJ é obrigatório");
		}
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email é obrigatório");
		}
		if (senha == null || senha.isEmpty()) {
			throw new IllegalArgumentException("Senha é obrigatória");
		}

		if (telefone != null && !telefone.isEmpty() && !telefone.matches("\\d{10}|\\d{11}")) {
			throw new IllegalArgumentException("Telefone inválido. Use apenas dígitos (10 ou 11 caracteres).");
		}

		Desenvolvedora desenvolvedor = new Desenvolvedora(nome.trim(), cnpj.trim());
		desenvolvedor.setEmail(email.trim());
		desenvolvedor.setSenha(senha);
		desenvolvedor.setTelefone((telefone == null ? null : telefone.trim()));
		new DesenvolvedorDAOImpl().cadastrar(desenvolvedor);
	}

}