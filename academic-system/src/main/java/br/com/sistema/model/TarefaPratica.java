package br.com.sistema.model;
import br.com.sistema.model.enums.TipoAvaliacao;

public class TarefaPratica extends Avaliacao {
    public TarefaPratica(String titulo, double valor, double peso) {
        super(titulo, valor, peso, TipoAvaliacao.TAREFA_PRATICA);
    }
}