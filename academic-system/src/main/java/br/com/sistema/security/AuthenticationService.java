package br.com.sistema.security;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.security.exception.AuthenticationException;
import br.com.sistema.security.exception.AuthorizationException;

public class AuthenticationService {

    private UserRepository repository = new UserRepository();

    public Usuario autenticar(String login, String senha) {

        String[] dados = repository.buscarUsuario(login);

        if (dados == null) {
            throw new AuthenticationException("Usuário inexistente.");
        }

        if (!dados[1].equals(senha)) {
            throw new AuthenticationException("Senha inválida.");
        }

        return new Usuario(login, PapelUsuario.valueOf(dados[2]));
    }

    public void verificarAdmin(Usuario usuario) {
        if (usuario.getPapel() != PapelUsuario.ADMIN) {
            throw new AuthorizationException("Acesso negado.");
        }
    }
}