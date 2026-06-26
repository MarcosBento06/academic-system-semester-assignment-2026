package br.com.sistema.service;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.exception.InvalidAssessmentException;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.model.enums.TipoAvaliacao;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TUS-2402 - Test AssessmentService behavior
@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @Mock
    private TurmaRepository repository;

    private AvaliacaoService service;
    private Usuario professor;
    private Usuario admin;
    private Turma turma;

    @BeforeEach
    void setUp() {
        service = new AvaliacaoService(repository);
        professor = new Usuario("prof", PapelUsuario.PROFESSOR);
        admin = new Usuario("admin", PapelUsuario.ADMIN);
        turma = new Turma("CS101", "Algoritmos");
    }

    @Test
    void professorPodeRegistrarAvaliacaoValida() {
        when(repository.buscarPorCodigo("CS101")).thenReturn(turma);
        AvaliacaoDTO dto = new AvaliacaoDTO("Prova Final", 10.0, 1.0, TipoAvaliacao.EXAME);

        assertDoesNotThrow(() -> service.registrarAvaliacao(professor, "CS101", dto));
        assertEquals(1, turma.getAvaliacoes().size());
    }

    @Test
    void adminNaoPodeRegistrarAvaliacao() {
        AvaliacaoDTO dto = new AvaliacaoDTO("Prova", 10.0, 1.0, TipoAvaliacao.EXAME);
        assertThrows(AuthorizationException.class,
                () -> service.registrarAvaliacao(admin, "CS101", dto));
    }

    @Test
    void turmaInexistenteLancaExcecao() {
        when(repository.buscarPorCodigo("XX")).thenReturn(null);
        AvaliacaoDTO dto = new AvaliacaoDTO("Prova", 10.0, 1.0, TipoAvaliacao.EXAME);
        assertThrows(AcademicSystemException.class,
                () -> service.registrarAvaliacao(professor, "XX", dto));
    }

    @Test
    void avaliacaoComPesoInvalidoLancaExcecao() {
        when(repository.buscarPorCodigo("CS101")).thenReturn(turma);
        AvaliacaoDTO dto = new AvaliacaoDTO("Prova", 10.0, -1.0, TipoAvaliacao.EXAME);
        assertThrows(InvalidAssessmentException.class,
                () -> service.registrarAvaliacao(professor, "CS101", dto));
    }
}
