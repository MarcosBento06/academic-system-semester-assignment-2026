package br.com.sistema.controller;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.model.enums.TipoAvaliacao;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.service.AvaliacaoService;
import br.com.sistema.service.PersistenceService;
import br.com.sistema.service.ReportService;
import br.com.sistema.service.TurmaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademicSystemControllerTest {

    @Mock private TurmaService turmaService;
    @Mock private AvaliacaoService avaliacaoService;
    @Mock private PersistenceService persistenceService;
    @Mock private ReportService reportService;

    private AcademicSystemController controller;
    private Usuario admin;
    private Usuario professor;

    @BeforeEach
    void setUp() {
        controller = new AcademicSystemController(
                turmaService, avaliacaoService, persistenceService, reportService);
        admin     = new Usuario("admin", PapelUsuario.ADMIN);
        professor = new Usuario("prof", PapelUsuario.PROFESSOR);
    }

    @Test
    void registrarTurmaDelegaParaTurmaService() {
        TurmaDTO dto = new TurmaDTO("CS101", "Algoritmos");
        controller.registrarTurma(admin, dto);
        verify(turmaService).registrarTurma(admin, dto);
    }

    @Test
    void registrarAvaliacaoDelegaParaAvaliacaoService() {
        AvaliacaoDTO dto = new AvaliacaoDTO("Prova", 10.0, 1.0, TipoAvaliacao.EXAME);
        controller.registrarAvaliacao(professor, "CS101", dto);
        verify(avaliacaoService).registrarAvaliacao(professor, "CS101", dto);
    }

    @Test
    void configurarPersistenciaDelegaParaPersistenceService() {
        controller.configurarPersistencia(admin, PersistenceType.XML);
        verify(persistenceService).configurarPersistencia(admin, PersistenceType.XML);
    }

    @Test
    void gerarRelatorioResumoDelegaParaReportService() {
        controller.gerarRelatorioResumoAvaliacoes(admin);
        verify(reportService).gerarRelatorioResumoAvaliacoes(admin);
    }

    @Test
    void gerarRelatorioPesosDelegaParaReportService() {
        controller.gerarRelatoriopesoAvaliacoes(admin);
        verify(reportService).gerarRelatoriopesoAvaliacoes(admin);
    }

    @Test
    void gerarRelatorioConfiguracaoDelegaParaReportService() {
        controller.gerarRelatorioConfiguracaoPersistencia(admin);
        verify(reportService).gerarRelatorioConfiguracaoPersistencia(admin);
    }
}