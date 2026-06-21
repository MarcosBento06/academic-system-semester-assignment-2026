package br.com.sistema.factory;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.exception.InvalidAssessmentException;
import br.com.sistema.model.*;
import br.com.sistema.validation.DomainValidator;

public class AvaliacaoFactory {
    
    public static Avaliacao criarAvaliacao(AvaliacaoDTO dto) {
        if (dto.getTipo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new InvalidAssessmentException("Tipo de avaliação inválido.");
        }
        
        Avaliacao avaliacao;

        switch (dto.getTipo()) {
        case EXAME:
            avaliacao = new Exame(dto.getTitulo(), dto.getValor(), dto.getPeso());
            break;
        case TAREFA_PRATICA:
            avaliacao = new TarefaPratica(dto.getTitulo(), dto.getValor(), dto.getPeso());
            break;
        case SEMINARIO:
            avaliacao = new Seminario(dto.getTitulo(), dto.getValor(), dto.getPeso());
            break;
        case ATRIBUICAO:
            avaliacao = new Atribuicao(dto.getTitulo(), dto.getValor(), dto.getPeso());
            break;
        default:
            throw new AcademicSystemException("Tipo de avaliação não suportado.");
        }
        DomainValidator.validarAvaliacao(avaliacao);
        return avaliacao;
    }
}