package br.com.sistema.model;

import br.com.sistema.model.enums.TipoAvaliacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public abstract class Avaliacao {
	@NotBlank(message = "O título da avaliação não pode ser vazio.")
    private String titulo;
	
	@PositiveOrZero(message = "Valor não pode ser negativo.")
    private double valor;
	
	@Positive(message = "Peso deve ser maior que zero.")
    private double peso;
	
	@NotNull(message = "Tipo de avaliação inválido.")
    private TipoAvaliacao tipo;

    public Avaliacao(String titulo, double valor, double peso, TipoAvaliacao tipo) {
        this.titulo = titulo;
        this.valor = valor;
        this.peso = peso;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo + ": " + titulo + " (Valor: " + valor + ", Peso: " + peso + ")";
    }
}