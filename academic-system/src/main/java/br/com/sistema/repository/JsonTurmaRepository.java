package br.com.sistema.repository;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.factory.AvaliacaoFactory;
import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;
import br.com.sistema.model.enums.TipoAvaliacao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonTurmaRepository {

    public void exportarParaJson(String nomeArquivo, Collection<Turma> turmas) throws IOException {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            Turma[] arr = turmas.toArray(new Turma[0]);

            writer.write("{\n");
            writer.write("  \"classes\": [\n");

            for (int i = 0; i < arr.length; i++) {
                Turma turma = arr[i];
                List<Avaliacao> avaliacoes = turma.getAvaliacoes();

                writer.write("    {\n");
                writer.write("      \"code\": \"" + escape(turma.getCodigo()) + "\",\n");
                writer.write("      \"title\": \"" + escape(turma.getDisciplina()) + "\",\n");
                writer.write("      \"assessments\": [\n");

                for (int j = 0; j < avaliacoes.size(); j++) {
                    Avaliacao a = avaliacoes.get(j);
                    writer.write("        {\n");
                    writer.write("          \"assessmentTitle\": \"" + escape(a.getTitulo()) + "\",\n");
                    writer.write("          \"type\": \"" + a.getTipo() + "\",\n");
                    writer.write("          \"value\": " + a.getValor() + ",\n");
                    writer.write("          \"weight\": " + a.getPeso() + "\n");
                    writer.write("        }" + (j < avaliacoes.size() - 1 ? "," : "") + "\n");
                }

                writer.write("      ]\n");
                writer.write("    }" + (i < arr.length - 1 ? "," : "") + "\n");
            }

            writer.write("  ]\n");
            writer.write("}\n");
        }
    }

    public List<Turma> carregarDeJson(String nomeArquivo) throws IOException {
        List<Turma> turmas = new ArrayList<>();
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) return turmas;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            Turma turmaAtual = null;
            String codigo = null;
            String tituloAv = null, tipo = null;
            double valor = 0;

            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("\"code\":")) {
                    codigo = extrairValorString(linha);
                } else if (linha.startsWith("\"title\":")) {
                    String tituloTurma = extrairValorString(linha);
                    turmaAtual = new Turma(codigo, tituloTurma);
                    turmas.add(turmaAtual);
                } else if (linha.startsWith("\"assessmentTitle\":")) {
                    tituloAv = extrairValorString(linha);
                } else if (linha.startsWith("\"type\":")) {
                    tipo = extrairValorString(linha);
                } else if (linha.startsWith("\"value\":")) {
                    valor = Double.parseDouble(extrairValorNumerico(linha));
                } else if (linha.startsWith("\"weight\":") && turmaAtual != null && tipo != null) {
                    double peso = Double.parseDouble(extrairValorNumerico(linha));
                    String tituloFinal = (tituloAv != null) ? tituloAv : "Sem título";
                    Avaliacao av = AvaliacaoFactory.criarAvaliacao(
                            new AvaliacaoDTO(tituloFinal, valor, peso, TipoAvaliacao.valueOf(tipo)));
                    turmaAtual.adicionarAvaliacao(av);
                    tituloAv = null;
                    tipo = null;
                    valor = 0;
                }
            }
        }
        return turmas;
    }

    private String extrairValorString(String linha) {
        int inicio = linha.indexOf('"', linha.indexOf(':') + 1) + 1;
        int fim = linha.lastIndexOf('"');
        return linha.substring(inicio, fim);
    }

    private String extrairValorNumerico(String linha) {
        return linha.substring(linha.indexOf(':') + 1).trim().replace(",", "");
    }

    private String escape(String v) {
        if (v == null) return "";
        return v.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

