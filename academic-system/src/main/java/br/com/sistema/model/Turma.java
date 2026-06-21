package br.com.sistema.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class Turma {
	@NotBlank(message = "Código da turma não pode ser vazio.")
    private String codigo;
	
	@NotBlank(message = "Disciplina não pode ser vazia.")
    private String disciplina;
	
    private List<Avaliacao> avaliacoes;

    public Turma(String codigo, String disciplina) {
        this.codigo = codigo;
        this.disciplina = disciplina;
        this.avaliacoes = new ArrayList<>();
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        this.avaliacoes.add(avaliacao);
    }

    public void exibirInformacoes() {
        System.out.println("Turma: " + codigo + " - " + disciplina);
        System.out.println("Avaliações Cadastradas:");
        if (avaliacoes.isEmpty()) {
            System.out.println("  Nenhuma avaliação cadastrada.");
        } else {
            for (Avaliacao a : avaliacoes) {
                System.out.println("  - " + a.toString());
            }
        }
    }
}