package br.com.sistema.repository;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.factory.AvaliacaoFactory;
import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;
import br.com.sistema.model.enums.TipoAvaliacao;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TurmaRepository {

    private Map<String, Turma> bancoDeDados = new HashMap<>();

    public Map<String, Turma> listarTurmas() {
        return Collections.unmodifiableMap(bancoDeDados);
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
                    writer.write("Assessment Title: " + avaliacao.getTitulo() + "\n");
                    writer.write("Assessment Type: " + avaliacao.getTipo() + "\n");
                    writer.write("Assessment Value: " + avaliacao.getValor() + "\n");
                    writer.write("Assessment Weight: " + avaliacao.getPeso() + "\n");
                    writer.write("\n");
                }

                writer.write("----------------------------------\n");
            }
        }
    }

    public void carregarDeTxt(String nomeArquivo) throws IOException {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) return;

        bancoDeDados.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            Turma turmaAtual = null;
            String titulo = null;
            String tipo = null;
            double valor = 0;

            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("Class Code: ")) {
                    String codigo = linha.substring("Class Code: ".length());
                    turmaAtual = new Turma(codigo, "");
                } else if (linha.startsWith("Class Title: ") && turmaAtual != null) {
                    turmaAtual.setDisciplina(linha.substring("Class Title: ".length()));
                    bancoDeDados.put(turmaAtual.getCodigo(), turmaAtual);
                } else if (linha.startsWith("Assessment Title: ")) {
                    titulo = linha.substring("Assessment Title: ".length());
                } else if (linha.startsWith("Assessment Type: ")) {
                    tipo = linha.substring("Assessment Type: ".length());
                } else if (linha.startsWith("Assessment Value: ")) {
                    valor = Double.parseDouble(linha.substring("Assessment Value: ".length()));
                } else if (linha.startsWith("Assessment Weight: ") && turmaAtual != null && tipo != null) {
                    double peso = Double.parseDouble(linha.substring("Assessment Weight: ".length()));
                    String tituloFinal = (titulo != null) ? titulo : "Sem título";
                    Avaliacao av = AvaliacaoFactory.criarAvaliacao(
                            new AvaliacaoDTO(tituloFinal, valor, peso, TipoAvaliacao.valueOf(tipo)));
                    turmaAtual.adicionarAvaliacao(av);
                    titulo = null;
                    tipo = null;
                    valor = 0;
                }
            }
        }
    }
}
