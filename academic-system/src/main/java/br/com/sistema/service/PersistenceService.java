package br.com.sistema.service;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.security.exception.AuthorizationException;

public class PersistenceService {

    private PersistenceType tipoAtual = PersistenceType.TXT;

    public void configurarPersistencia(Usuario usuario, PersistenceType tipo) {

        if (usuario == null ||
            usuario.getPapel() != PapelUsuario.ADMIN) {
            throw new AuthorizationException("Apenas administradores podem configurar a persistência.");
        }

        tipoAtual = tipo;
        System.out.println("[AUDIT] Persistência alterada para " + tipo);
    }

    public PersistenceType getTipoAtual() {
        return tipoAtual;
    }
}