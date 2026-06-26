package br.com.sistema.service;

import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.logging.AcademicLogger;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import br.com.sistema.validation.DomainValidator;

import java.util.logging.Logger;

public class TurmaService {

    private static final Logger logger = AcademicLogger.getLogger();
    private TurmaRepository repository;

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public void registrarTurma(Usuario usuario, TurmaDTO dto) {
        if (usuario == null || usuario.getPapel() != PapelUsuario.ADMIN) {
            logger.warning("AUTHZ_FAILURE - cadastro de turma negado para: "
                    + (usuario != null ? usuario.getNome() : "null"));
            throw new AuthorizationException("Apenas administradores podem cadastrar turmas.");
        }

        Turma turma = new Turma(dto.getCodigo(), dto.getDisciplina());
        DomainValidator.validarTurma(turma);
        repository.salvar(turma);

        logger.info("CLASS_REGISTERED - código: " + dto.getCodigo() + " por: " + usuario.getNome());
    }

    public Turma buscarTurma(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    public java.util.Map<String, Turma> listarTurmas() {
        return repository.listarTurmas();
    }
}
