package br.com.sistema.model;

import br.com.sistema.model.enums.TipoAvaliacao;

public abstract class Avaliacao {
    private String titulo;
    private double valor;
    private double peso;
    private TipoAvaliacao tipo;

    public Avaliacao(String titulo, double valor, double peso, TipoAvaliacao tipo) {
        this.titulo = titulo;
        this.valor = valor;
        this.peso = peso;
        this.tipo = tipo;
    }

    public String getTitulo() { return titulo; }
    public double getValor() { return valor; }
    public double getPeso() { return peso; }
    public TipoAvaliacao getTipo() { return tipo; }

    @Override
    public String toString() {
        return tipo + ": " + titulo + " (Valor: " + valor + ", Peso: " + peso + ")";
    }
}