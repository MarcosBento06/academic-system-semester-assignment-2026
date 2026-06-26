package br.com.sistema.service;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.repository.TurmaRepository;
import br.com.sistema.security.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

// TUS-2403 - Test PersistenceService behavior
@ExtendWith(MockitoExtension.class)
class PersistenceServiceTest {

    @Mock
    private TurmaRepository repository;

    private PersistenceService service;
    private Usuario admin;
    private Usuario professor;

    @BeforeEach
    void setUp() {
        service = new PersistenceService(repository);
        admin = new Usuario("admin", PapelUsuario.ADMIN);
        professor = new Usuario("prof", PapelUsuario.PROFESSOR);
    }

    @Test
    void tipoPadraoEhTxt() {
        assertEquals(PersistenceType.TXT, service.getTipoAtual());
    }

    @Test
    void adminPodeAlterarTipoParaXml() {
        service.configurarPersistencia(admin, PersistenceType.XML);
        assertEquals(PersistenceType.XML, service.getTipoAtual());
    }

    @Test
    void adminPodeAlterarTipoParaJson() {
        service.configurarPersistencia(admin, PersistenceType.JSON);
        assertEquals(PersistenceType.JSON, service.getTipoAtual());
    }

    @Test
    void professorNaoPodeAlterarTipoDePersistencia() {
        assertThrows(AuthorizationException.class,
                () -> service.configurarPersistencia(professor, PersistenceType.XML));
    }

    @Test
    void usuarioNuloNaoPodeAlterarPersistencia() {
        assertThrows(AuthorizationException.class,
                () -> service.configurarPersistencia(null, PersistenceType.XML));
    }
}
