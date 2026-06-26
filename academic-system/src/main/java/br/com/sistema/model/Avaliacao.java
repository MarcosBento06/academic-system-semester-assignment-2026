package br.com.sistema.model;

import br.com.sistema.model.enums.TipoAvaliacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avaliacao)) return false;
        Avaliacao other = (Avaliacao) o;
        return Objects.equals(titulo, other.titulo) && tipo == other.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, tipo);
    }

    @Override
    public String toString() {
        return tipo + ": " + titulo + " (Valor: " + valor + ", Peso: " + peso + ")";
    }
}
