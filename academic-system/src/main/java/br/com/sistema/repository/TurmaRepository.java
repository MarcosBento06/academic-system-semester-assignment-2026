package br.com.sistema.repository;

import br.com.sistema.model.Turma;
import java.util.HashMap;
import java.util.Map;

public class TurmaRepository {
    private Map<String, Turma> bancoDeDados = new HashMap<>();

    public void salvar(Turma turma) {
        bancoDeDados.put(turma.getCodigo(), turma);
    }

    public Turma buscarPorCodigo(String codigo) {
        return bancoDeDados.get(codigo);
    }
}