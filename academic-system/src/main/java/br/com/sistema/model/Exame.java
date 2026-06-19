package br.com.sistema.model;
import br.com.sistema.model.enums.TipoAvaliacao;

public class Exame extends Avaliacao {
    public Exame(String titulo, double valor, double peso) {
        super(titulo, valor, peso, TipoAvaliacao.EXAME);
    }
}