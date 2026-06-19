package br.com.sistema.factory;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.model.*;

public class AvaliacaoFactory {
    
    public static Avaliacao criarAvaliacao(AvaliacaoDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new AcademicSystemException("O título da avaliação não pode ser vazio.");
        }
        if (dto.getValor() < 0 || dto.getPeso() <= 0) {
            throw new AcademicSystemException("Valor não pode ser negativo e peso deve ser maior que zero.");
        }
        if (dto.getTipo() == null) {
            throw new AcademicSystemException("Tipo de avaliação inválido.");
        }

        switch (dto.getTipo()) {
            case EXAME:
                return new Exame(dto.getTitulo(), dto.getValor(), dto.getPeso());
            case TAREFA_PRATICA:
                return new TarefaPratica(dto.getTitulo(), dto.getValor(), dto.getPeso());
            case SEMINARIO:
                return new Seminario(dto.getTitulo(), dto.getValor(), dto.getPeso());
            case ATRIBUICAO:
                return new Atribuicao(dto.getTitulo(), dto.getValor(), dto.getPeso());
            default:
                throw new AcademicSystemException("Tipo de avaliação não suportado.");
        }
    }
}