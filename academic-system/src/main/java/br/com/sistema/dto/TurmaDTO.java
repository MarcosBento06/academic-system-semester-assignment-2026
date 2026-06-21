package br.com.sistema.dto;

public class TurmaDTO {

    private String codigo;
    private String disciplina;

    public TurmaDTO(String codigo, String disciplina) {
        this.codigo = codigo;
        this.disciplina = disciplina;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDisciplina() {
        return disciplina;
    }
}