package br.com.sistema.security;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.security.exception.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// US-2386 - Test authentication behavior
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthenticationService(userRepository);
    }

    @Test
    void autenticacaoComCredenciaisValidasRetornaUsuario() {
        when(userRepository.buscarUsuario("admin"))
                .thenReturn(new String[]{"admin", "1234", "ADMIN"});

        Usuario usuario = authService.autenticar("admin", "1234");

        assertNotNull(usuario);
        assertEquals("admin", usuario.getNome());
        assertEquals(PapelUsuario.ADMIN, usuario.getPapel());
    }

    @Test
    void autenticacaoComUsuarioInexistenteLancaExcecao() {
        when(userRepository.buscarUsuario("naoexiste")).thenReturn(null);

        assertThrows(AuthenticationException.class,
                () -> authService.autenticar("naoexiste", "1234"));
    }

    @Test
    void autenticacaoComSenhaErradaLancaExcecao() {
        when(userRepository.buscarUsuario("admin"))
                .thenReturn(new String[]{"admin", "1234", "ADMIN"});

        assertThrows(AuthenticationException.class,
                () -> authService.autenticar("admin", "errada"));
    }

    @Test
    void autenticacaoProfessorRetornaPapelCorreto() {
        when(userRepository.buscarUsuario("prof"))
                .thenReturn(new String[]{"prof", "abc", "PROFESSOR"});

        Usuario usuario = authService.autenticar("prof", "abc");

        assertEquals(PapelUsuario.PROFESSOR, usuario.getPapel());
    }
}
