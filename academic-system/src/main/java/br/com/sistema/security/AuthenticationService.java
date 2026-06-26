package br.com.sistema.security;

import br.com.sistema.logging.AcademicLogger;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.security.exception.AuthenticationException;
import br.com.sistema.security.exception.AuthorizationException;

import java.util.logging.Logger;

public class AuthenticationService {

    private static final Logger logger = AcademicLogger.getLogger();
    private UserRepository repository;

    public AuthenticationService() {
        this.repository = new UserRepository();
    }

    // construtor para injeção em testes
    public AuthenticationService(UserRepository repository) {
        this.repository = repository;
    }


    public Usuario autenticar(String login, String senha) {
        String[] dados = repository.buscarUsuario(login);

        if (dados == null) {
            logger.warning("AUTH_FAILURE - usuário não encontrado: " + login);
            throw new AuthenticationException("Usuário inexistente.");
        }

        if (!dados[1].equals(senha)) {
            logger.warning("AUTH_FAILURE - senha inválida para: " + login);
            throw new AuthenticationException("Senha inválida.");
        }

        Usuario usuario = new Usuario(login, PapelUsuario.valueOf(dados[2]));
        logger.info("AUTH_SUCCESS - login: " + login + ", papel: " + usuario.getPapel());
        return usuario;
    }


    public void logout(String login) {
        logger.info("LOGOUT - usuário: " + login);
    }


    public void verificarAdmin(Usuario usuario) {
        if (usuario.getPapel() != PapelUsuario.ADMIN) {
            logger.warning("AUTHZ_FAILURE - usuário " + usuario.getNome() + " tentou ação de ADMIN");
            throw new AuthorizationException("Acesso negado.");
        }
    }
}
