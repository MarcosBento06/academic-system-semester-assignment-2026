package br.com.sistema.service;

import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// US-2388 - Test report generation
class ReportServiceTest {

    private TurmaRepository repository;
    private PersistenceService persistenceService;
    private ReportService reportService;
    private Usuario admin;
    private Usuario professor;

    @BeforeEach
    void setUp() {
        repository = new TurmaRepository();
        persistenceService = new PersistenceService(repository);
        reportService = new ReportService(repository, persistenceService);
        admin = new Usuario("admin", PapelUsuario.ADMIN);
        professor = new Usuario("prof", PapelUsuario.PROFESSOR);
    }

    @Test
    void relatorioResumoSemTurmasRetornaMensagemVazia() {
        String resultado = reportService.gerarRelatorioResumoAvaliacoes(admin);
        assertTrue(resultado.contains("Nenhuma turma cadastrada."));
    }

    @Test
    void relatorioResumoComTurmaListaTurma() {
        repository.salvar(new Turma("CS101", "Algoritmos"));
        String resultado = reportService.gerarRelatorioResumoAvaliacoes(admin);
        assertTrue(resultado.contains("CS101"));
        assertTrue(resultado.contains("Algoritmos"));
    }

    @Test
    void relatorioConfiguracaoPersistenciaExibetipoAtual() {
        String resultado = reportService.gerarRelatorioConfiguracaoPersistencia(admin);
        assertTrue(resultado.contains("TXT"));
    }

    @Test
    void relatorioConfiguracaoPersistenciaNegadoParaProfessor() {
        assertThrows(AuthorizationException.class,
                () -> reportService.gerarRelatorioConfiguracaoPersistencia(professor));
    }

    @Test
    void relatorioPesosComTurmaExibePesoTotal() {
        repository.salvar(new Turma("CS101", "Algoritmos"));
        String resultado = reportService.gerarRelatoriopesoAvaliacoes(admin);
        assertTrue(resultado.contains("CS101"));
        assertTrue(resultado.contains("Peso Total"));
    }
}
