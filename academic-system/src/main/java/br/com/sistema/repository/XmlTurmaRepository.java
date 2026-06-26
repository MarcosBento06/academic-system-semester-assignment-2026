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

public class XmlTurmaRepository {

    public void exportarParaXml(String nomeArquivo, Collection<Turma> turmas) throws IOException {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<academicSystem>\n");
            writer.write("  <classes>\n");

            for (Turma turma : turmas) {
                writer.write("    <class>\n");
                writer.write("      <code>" + escape(turma.getCodigo()) + "</code>\n");
                writer.write("      <title>" + escape(turma.getDisciplina()) + "</title>\n");
                writer.write("      <assessments>\n");

                for (Avaliacao a : turma.getAvaliacoes()) {
                    writer.write("        <assessment>\n");
                    writer.write("          <assessmentTitle>" + escape(a.getTitulo()) + "</assessmentTitle>\n");
                    writer.write("          <type>" + a.getTipo() + "</type>\n");
                    writer.write("          <value>" + a.getValor() + "</value>\n");
                    writer.write("          <weight>" + a.getPeso() + "</weight>\n");
                    writer.write("        </assessment>\n");
                }

                writer.write("      </assessments>\n");
                writer.write("    </class>\n");
            }

            writer.write("  </classes>\n");
            writer.write("</academicSystem>\n");
        }
    }

    public List<Turma> carregarDeXml(String nomeArquivo) throws IOException {
        List<Turma> turmas = new ArrayList<>();
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) return turmas;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            Turma turmaAtual = null;
            String codigo = null, tituloTurma = null;
            String tituloAv = null, tipo = null;
            double valor = 0;

            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("<code>")) {
                    codigo = extrairConteudo(linha, "code");
                } else if (linha.startsWith("<title>")) {
                    tituloTurma = extrairConteudo(linha, "title");
                    turmaAtual = new Turma(codigo, tituloTurma);
                    turmas.add(turmaAtual);
                } else if (linha.startsWith("<assessmentTitle>")) {
                    tituloAv = extrairConteudo(linha, "assessmentTitle");
                } else if (linha.startsWith("<type>")) {
                    tipo = extrairConteudo(linha, "type");
                } else if (linha.startsWith("<value>")) {
                    valor = Double.parseDouble(extrairConteudo(linha, "value"));
                } else if (linha.startsWith("<weight>") && turmaAtual != null && tipo != null) {
                    double peso = Double.parseDouble(extrairConteudo(linha, "weight"));
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

    private String extrairConteudo(String linha, String tag) {
        return linha.replace("<" + tag + ">", "").replace("</" + tag + ">", "").trim();
    }

    private String escape(String v) {
        if (v == null) return "";
        return v.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}

