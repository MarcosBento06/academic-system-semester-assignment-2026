package br.com.sistema.model;
import br.com.sistema.model.enums.TipoAvaliacao;

public class Atribuicao extends Avaliacao {
    public Atribuicao(String titulo, double valor, double peso) {
        super(titulo, valor, peso, TipoAvaliacao.ATRIBUICAO);
    }
}