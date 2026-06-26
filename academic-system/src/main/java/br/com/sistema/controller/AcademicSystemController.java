package br.com.sistema.controller;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.service.AvaliacaoService;
import br.com.sistema.service.PersistenceService;
import br.com.sistema.service.ReportService;
import br.com.sistema.service.TurmaService;

import java.util.Map;

public class AcademicSystemController {

    private TurmaService turmaService;
    private AvaliacaoService avaliacaoService;
    private PersistenceService persistenceService;
    private ReportService reportService;

    public AcademicSystemController(TurmaService turmaService,
                                    AvaliacaoService avaliacaoService,
                                    PersistenceService persistenceService,
                                    ReportService reportService) {
        this.turmaService = turmaService;
        this.avaliacaoService = avaliacaoService;
        this.persistenceService = persistenceService;
        this.reportService = reportService;
    }

    public void registrarTurma(Usuario usuario, TurmaDTO dto) {
        turmaService.registrarTurma(usuario, dto);
    }

    public void registrarAvaliacao(Usuario usuario, String codigoTurma, AvaliacaoDTO dto) {
        avaliacaoService.registrarAvaliacao(usuario, codigoTurma, dto);
    }

    public void carregarDados() throws Exception {
        persistenceService.carregarDados();
    }

    public void salvarDados() throws Exception {
        persistenceService.salvarDados();
    }

    public void exportarTxt(String nomeArquivo) throws Exception {
        persistenceService.exportarTxt(nomeArquivo);
    }

    public Turma buscarTurma(String codigo) {
        return turmaService.buscarTurma(codigo);
    }

    public Map<String, Turma> listarTurmas() {
        return turmaService.listarTurmas();
    }

    public void configurarPersistencia(Usuario usuario, PersistenceType tipo) {
        persistenceService.configurarPersistencia(usuario, tipo);
    }

    public PersistenceType obterPersistenciaAtual() {
        return persistenceService.getTipoAtual();
    }

    public String gerarRelatorioResumoAvaliacoes(Usuario usuario) {
        return reportService.gerarRelatorioResumoAvaliacoes(usuario);
    }

    public String gerarRelatoriopesoAvaliacoes(Usuario usuario) {
        return reportService.gerarRelatoriopesoAvaliacoes(usuario);
    }

    public String gerarRelatorioConfiguracaoPersistencia(Usuario usuario) {
        return reportService.gerarRelatorioConfiguracaoPersistencia(usuario);
    }
}
