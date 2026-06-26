package br.com.sistema.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// TUS-2384 - Test identifiable domain object equality
class TurmaEqualityTest {

    @Test
    void turmasComMesmoCodigoSaoIguais() {
        Turma t1 = new Turma("CS101", "Algoritmos");
        Turma t2 = new Turma("CS101", "Outra Disciplina");
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void turmasComCodigosDiferentesSaoDiferentes() {
        Turma t1 = new Turma("CS101", "Algoritmos");
        Turma t2 = new Turma("CS102", "Algoritmos");
        assertNotEquals(t1, t2);
    }

    @Test
    void turmaIgualASiMesma() {
        Turma t = new Turma("CS101", "Algoritmos");
        assertEquals(t, t);
    }

    @Test
    void avaliacoesComMesmoTituloETipoSaoIguais() {
        Avaliacao a1 = new Exame("Prova Final", 10.0, 0.5);
        Avaliacao a2 = new Exame("Prova Final", 8.0, 0.3);
        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void avaliacoesComTiposDiferentesSaoDiferentes() {
        Avaliacao a1 = new Exame("Prova", 10.0, 0.5);
        Avaliacao a2 = new Seminario("Prova", 10.0, 0.5);
        assertNotEquals(a1, a2);
    }
}
