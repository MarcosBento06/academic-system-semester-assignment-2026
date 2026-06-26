package br.com.sistema.security;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.security.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

// US-2387 - Test authorization behavior
@ExtendWith(MockitoExtension.class)
class AuthorizationTest {

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthenticationService(userRepository);
    }

    @Test
    void adminPassaVerificacaoDeAdmin() {
        Usuario admin = new Usuario("admin", PapelUsuario.ADMIN);
        assertDoesNotThrow(() -> authService.verificarAdmin(admin));
    }

    @Test
    void professorFalhaVerificacaoDeAdmin() {
        Usuario prof = new Usuario("prof", PapelUsuario.PROFESSOR);
        assertThrows(AuthorizationException.class, () -> authService.verificarAdmin(prof));
    }
}
