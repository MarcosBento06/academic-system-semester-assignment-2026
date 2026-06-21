package br.com.sistema.validation;

import br.com.sistema.exception.InvalidAssessmentException;
import br.com.sistema.exception.InvalidClassException;
import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class DomainValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validarTurma(Turma turma) {
        Set<ConstraintViolation<Turma>> erros = validator.validate(turma);

        if (!erros.isEmpty()) {
            String mensagem = erros.iterator().next().getMessage();
            throw new InvalidClassException(mensagem);
        }
    }

    public static void validarAvaliacao(Avaliacao avaliacao) {
        Set<ConstraintViolation<Avaliacao>> erros = validator.validate(avaliacao);

        if (!erros.isEmpty()) {
            String mensagem = erros.iterator().next().getMessage();
            throw new InvalidAssessmentException(mensagem);
        }
    }
}