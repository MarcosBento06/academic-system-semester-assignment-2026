package br.com.sistema.model;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String codigo;
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

    public String getCodigo() { return codigo; }
    public String getDisciplina() { return disciplina; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }

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