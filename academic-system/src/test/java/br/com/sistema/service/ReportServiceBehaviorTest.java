package br.com.sistema.service;

import br.com.sistema.model.Exame;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TUS-2404 - Test ReportService behavior
class ReportServiceBehaviorTest {

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
    void relatorioPesoStatusValidoQuandoSomaMil() {
        Turma turma = new Turma("CS101", "POO");
        turma.adicionarAvaliacao(new Exame("Prova", 10.0, 0.5));
        turma.adicionarAvaliacao(new Exame("Trabalho", 10.0, 0.5));
        repository.salvar(turma);

        String resultado = reportService.gerarRelatoriopesoAvaliacoes(admin);
        assertTrue(resultado.contains("VÁLIDO"));
    }

    @Test
    void relatorioPesoStatusInvalidoQuandoSomaDiferenteDeUm() {
        Turma turma = new Turma("CS101", "POO");
        turma.adicionarAvaliacao(new Exame("Prova", 10.0, 0.3));
        repository.salvar(turma);

        String resultado = reportService.gerarRelatoriopesoAvaliacoes(admin);
        assertTrue(resultado.contains("INVÁLIDO"));
    }

    @Test
    void relatorioConfiguracaoRefleteTipoConfigurado() {
        persistenceService.configurarPersistencia(admin, PersistenceType.JSON);
        String resultado = reportService.gerarRelatorioConfiguracaoPersistencia(admin);
        assertTrue(resultado.contains("JSON"));
    }

    @Test
    void professorPodeGerarRelatorioResumo() {
        assertDoesNotThrow(() -> reportService.gerarRelatorioResumoAvaliacoes(professor));
    }

    @Test
    void professorPodeGerarRelatorioPesos() {
        assertDoesNotThrow(() -> reportService.gerarRelatoriopesoAvaliacoes(professor));
    }

    @Test
    void professorNaoPodeGerarRelatorioConfiguracaoPersistencia() {
        assertThrows(AuthorizationException.class,
                () -> reportService.gerarRelatorioConfiguracaoPersistencia(professor));
    }
}
