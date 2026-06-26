package br.com.sistema.repository;

import br.com.sistema.model.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// US-2389 - Test persistence repositories
class TurmaRepositoryTest {

    private TurmaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TurmaRepository();
    }

    @Test
    void salvarESobrescreverTurmaComMesmoCodigo() {
        repository.salvar(new Turma("CS101", "Algoritmos"));
        repository.salvar(new Turma("CS101", "Algoritmos Avançados"));
        assertEquals(1, repository.listarTurmas().size());
        assertEquals("Algoritmos Avançados", repository.buscarPorCodigo("CS101").getDisciplina());
    }

    @Test
    void buscarTurmaInexistenteRetornaNull() {
        assertNull(repository.buscarPorCodigo("INEXISTENTE"));
    }

    @Test
    void listarTurmasRetornaTodasSalvas() {
        repository.salvar(new Turma("CS101", "Algoritmos"));
        repository.salvar(new Turma("CS102", "POO"));
        assertEquals(2, repository.listarTurmas().size());
    }

    @Test
    void repositorioVazioRetornaListaVazia() {
        assertTrue(repository.listarTurmas().isEmpty());
    }
}
