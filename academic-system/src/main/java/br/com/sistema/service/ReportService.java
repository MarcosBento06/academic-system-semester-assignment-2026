package br.com.sistema.service;

import br.com.sistema.logging.AcademicLogger;
import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;

import java.util.Map;
import java.util.logging.Logger;


public class ReportService {

    private static final Logger logger = AcademicLogger.getLogger();

    private TurmaRepository turmaRepository;
    private PersistenceService persistenceService;

    public ReportService(TurmaRepository turmaRepository, PersistenceService persistenceService) {
        this.turmaRepository = turmaRepository;
        this.persistenceService = persistenceService;
    }

    public String gerarRelatorioResumoAvaliacoes(Usuario usuario) {
        logger.info("REPORT_GEN - resumo de avaliações solicitado por: " + usuario.getNome()
                + " [" + usuario.getPapel() + "]");
        Map<String, Turma> turmas = turmaRepository.listarTurmas();
        StringBuilder sb = new StringBuilder();
        sb.append("===== RELATÓRIO: RESUMO DE AVALIAÇÕES POR TURMA =====\n");
        if (turmas.isEmpty()) {
            sb.append("Nenhuma turma cadastrada.\n");
        } else {
            for (Turma turma : turmas.values()) {
                sb.append("Turma: ").append(turma.getCodigo())
                  .append(" - ").append(turma.getDisciplina()).append("\n");
                if (turma.getAvaliacoes().isEmpty()) {
                    sb.append("  Nenhuma avaliação cadastrada.\n");
                } else {
                    for (Avaliacao a : turma.getAvaliacoes()) {
                        sb.append("  Tipo: ").append(a.getTipo()).append("\n");
                        sb.append("  Valor: ").append(a.getValor()).append("\n");
                        sb.append("  Peso: ").append(a.getPeso()).append("\n");
                        sb.append("  ---\n");
                    }
                }
                sb.append("\n");
            }
        }
        sb.append("=====================================================\n");
        return sb.toString();
    }

    public String gerarRelatoriopesoAvaliacoes(Usuario usuario) {
        logger.info("REPORT_GEN - pesos de avaliações solicitado por: " + usuario.getNome()
                + " [" + usuario.getPapel() + "]");
        Map<String, Turma> turmas = turmaRepository.listarTurmas();
        StringBuilder sb = new StringBuilder();
        sb.append("===== RELATÓRIO: PESO DAS AVALIAÇÕES POR TURMA =====\n");
        if (turmas.isEmpty()) {
            sb.append("Nenhuma turma cadastrada.\n");
        } else {
            for (Turma turma : turmas.values()) {
                double total = turma.getAvaliacoes().stream()
                        .mapToDouble(Avaliacao::getPeso).sum();
                sb.append("Turma: ").append(turma.getCodigo())
                  .append(" - ").append(turma.getDisciplina()).append("\n");
                sb.append("  Peso Total: ").append(total).append("\n");
                sb.append("  Status: ").append(Math.abs(total - 1.0) < 1e-9 ? "VÁLIDO" : "INVÁLIDO").append("\n\n");
            }
        }
        sb.append("====================================================\n");
        return sb.toString();
    }

    public String gerarRelatorioConfiguracaoPersistencia(Usuario usuario) {
        if (usuario == null || usuario.getPapel() != PapelUsuario.ADMIN) {
            logger.warning("AUTHZ_FAILURE - relatório de persistência negado para: "
                    + (usuario != null ? usuario.getNome() : "null"));
            throw new AuthorizationException("Apenas administradores podem gerar este relatório.");
        }
        logger.info("REPORT_GEN - configuração de persistência solicitada por: " + usuario.getNome());
        PersistenceType tipo = persistenceService.getTipoAtual();
        return "===== RELATÓRIO: CONFIGURAÇÃO DE PERSISTÊNCIA =====\n"
             + "Tipo atual: " + tipo + "\n"
             + "===================================================\n";
    }
}
