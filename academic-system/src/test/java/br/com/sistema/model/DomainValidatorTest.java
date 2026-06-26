package br.com.sistema.model;

import br.com.sistema.exception.InvalidAssessmentException;
import br.com.sistema.exception.InvalidClassException;
import br.com.sistema.validation.DomainValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// TUS-2385 - Test academic domain validation
class DomainValidatorTest {

    @Test
    void turmaValidaNaoLancaExcecao() {
        Turma turma = new Turma("CS101", "Algoritmos");
        assertDoesNotThrow(() -> DomainValidator.validarTurma(turma));
    }

    @Test
    void turmaComCodigoVazioLancaExcecao() {
        Turma turma = new Turma("", "Algoritmos");
        assertThrows(InvalidClassException.class, () -> DomainValidator.validarTurma(turma));
    }

    @Test
    void turmaComDisciplinaVaziaLancaExcecao() {
        Turma turma = new Turma("CS101", "");
        assertThrows(InvalidClassException.class, () -> DomainValidator.validarTurma(turma));
    }

    @Test
    void avaliacaoValidaNaoLancaExcecao() {
        Avaliacao a = new Exame("Prova Final", 10.0, 1.0);
        assertDoesNotThrow(() -> DomainValidator.validarAvaliacao(a));
    }

    @Test
    void avaliacaoComTituloVazioLancaExcecao() {
        Avaliacao a = new Exame("", 10.0, 1.0);
        assertThrows(InvalidAssessmentException.class, () -> DomainValidator.validarAvaliacao(a));
    }

    @Test
    void avaliacaoComPesoZeroLancaExcecao() {
        Avaliacao a = new Exame("Prova", 10.0, 0.0);
        assertThrows(InvalidAssessmentException.class, () -> DomainValidator.validarAvaliacao(a));
    }

    @Test
    void avaliacaoComValorNegativoLancaExcecao() {
        Avaliacao a = new Exame("Prova", -1.0, 1.0);
        assertThrows(InvalidAssessmentException.class, () -> DomainValidator.validarAvaliacao(a));
    }
}
