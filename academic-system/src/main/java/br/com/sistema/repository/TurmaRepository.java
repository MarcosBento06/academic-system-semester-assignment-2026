package br.com.sistema.repository;

import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TurmaRepository {

    private Map<String, Turma> bancoDeDados = new HashMap<>();
    
    public Map<String, Turma> listarTurmas() {
        return bancoDeDados;
    }

    public void salvar(Turma turma) {
        bancoDeDados.put(turma.getCodigo(), turma);
    }

    public Turma buscarPorCodigo(String codigo) {
        return bancoDeDados.get(codigo);
    }

    public void exportarParaTxt(String nomeArquivo) throws IOException {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            for (Turma turma : bancoDeDados.values()) {
                writer.write("Class Code: " + turma.getCodigo() + "\n");
                writer.write("Class Title: " + turma.getDisciplina() + "\n");

                for (Avaliacao avaliacao : turma.getAvaliacoes()) {
                    writer.write("Assessment Type: " + avaliacao.getTipo() + "\n");
                    writer.write("Assessment Value: " + avaliacao.getValor() + "\n");
                    writer.write("Assessment Weight: " + avaliacao.getPeso() + "\n");
                    writer.write("\n");
                }

                writer.write("----------------------------------\n");
            }
        }
    }
}