package br.com.sistema.service;

import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.exception.InvalidClassException;
import br.com.sistema.model.Turma;
import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TUS-2401 - Test ClassService behavior
@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @Mock
    private TurmaRepository repository;

    private TurmaService service;
    private Usuario admin;
    private Usuario professor;

    @BeforeEach
    void setUp() {
        service = new TurmaService(repository);
        admin = new Usuario("admin", PapelUsuario.ADMIN);
        professor = new Usuario("prof", PapelUsuario.PROFESSOR);
    }

    @Test
    void adminPodeRegistrarTurmaValida() {
        service.registrarTurma(admin, new TurmaDTO("CS101", "Algoritmos"));
        verify(repository).salvar(any(Turma.class));
    }

    @Test
    void professorNaoPodeRegistrarTurma() {
        assertThrows(AuthorizationException.class,
                () -> service.registrarTurma(professor, new TurmaDTO("CS101", "Algoritmos")));
        verify(repository, never()).salvar(any());
    }

    @Test
    void usuarioNuloNaoPodeRegistrarTurma() {
        assertThrows(AuthorizationException.class,
                () -> service.registrarTurma(null, new TurmaDTO("CS101", "Algoritmos")));
    }

    @Test
    void turmaComCodigoVazioLancaExcecao() {
        assertThrows(InvalidClassException.class,
                () -> service.registrarTurma(admin, new TurmaDTO("", "Algoritmos")));
        verify(repository, never()).salvar(any());
    }
}
