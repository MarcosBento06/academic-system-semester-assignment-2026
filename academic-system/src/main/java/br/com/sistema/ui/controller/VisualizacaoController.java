package br.com.sistema.ui.controller;

import br.com.sistema.model.Avaliacao;
import br.com.sistema.model.Turma;
import br.com.sistema.ui.AppContext;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class VisualizacaoController {

    @FXML private ListView<Turma> listTurmas;
    @FXML private Label           lblTurmaSelecionada;
    @FXML private TextArea        txtAvaliacoes;

    private final AppContext ctx = AppContext.getInstance();

    @FXML
    private void initialize() {
        refreshTurmas();
    }

    @FXML
    private void handleTurmaSelecionada() {
        Turma turma = listTurmas.getSelectionModel().getSelectedItem();
        if (turma == null) return;

        lblTurmaSelecionada.setText(turma.getCodigo() + " - " + turma.getDisciplina());

        List<Avaliacao> avaliacoes = turma.getAvaliacoes();
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            txtAvaliacoes.setText("Nenhuma avaliação cadastrada para esta turma.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avaliacoes.size(); i++) {
            Avaliacao a = avaliacoes.get(i);
            sb.append(i + 1).append(". ");
            sb.append(a.getTitulo());
            sb.append("\n   Tipo : ").append(a.getTipo());
            sb.append("\n   Valor: ").append(a.getValor());
            sb.append("\n   Peso : ").append(a.getPeso());
            if (i < avaliacoes.size() - 1) sb.append("\n\n");
        }
        txtAvaliacoes.setText(sb.toString());
    }

    @FXML
    private void handleAtualizar() {
        refreshTurmas();
        txtAvaliacoes.clear();
        lblTurmaSelecionada.setText("Selecione uma turma para ver as avaliações");
    }

    private void refreshTurmas() {
        List<Turma> turmas = new ArrayList<>(ctx.getController().listarTurmas().values());
        listTurmas.setItems(FXCollections.observableArrayList(turmas));
    }
}
