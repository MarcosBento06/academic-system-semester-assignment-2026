package br.com.sistema.model;

import br.com.sistema.model.enums.TipoAvaliacao;

public class Seminario extends Avaliacao {
    
    public Seminario(String titulo, double valor, double peso) {
        super(titulo, valor, peso, TipoAvaliacao.SEMINARIO);
    }
}