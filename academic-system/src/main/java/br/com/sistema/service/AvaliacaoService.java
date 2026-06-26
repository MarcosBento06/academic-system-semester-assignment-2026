package br.com.sistema.service;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.factory.AvaliacaoFactory;
import br.com.sistema.logging.AcademicLogger;
import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;

import java.util.logging.Logger;


public class AvaliacaoService {

    private static final Logger logger = AcademicLogger.getLogger();
    private TurmaRepository turmaRepository;

    public AvaliacaoService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public void registrarAvaliacao(Usuario usuario, String codigoTurma, AvaliacaoDTO dto) {
        if (usuario == null || usuario.getPapel() != PapelUsuario.PROFESSOR) {
            logger.warning("AUTHZ_FAILURE - registro de avaliação negado para: "
                    + (usuario != null ? usuario.getNome() : "null"));
            throw new AuthorizationException("Acesso negado: Apenas professores podem registrar avaliações.");
        }

        Turma turma = turmaRepository.buscarPorCodigo(codigoTurma);
        if (turma == null) {
            throw new AcademicSystemException("Operação falhou: Código de turma inexistente (" + codigoTurma + ").");
        }

        Avaliacao novaAvaliacao = AvaliacaoFactory.criarAvaliacao(dto);
        turma.adicionarAvaliacao(novaAvaliacao);
        turmaRepository.salvar(turma);

        logger.info("ASSESSMENT_REGISTERED - turma: " + codigoTurma
                + ", avaliação: " + dto.getTitulo() + " por: " + usuario.getNome());
    }
}
