package br.com.sistema.ui.controller;

import br.com.sistema.dto.AvaliacaoDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.model.Turma;
import br.com.sistema.model.enums.TipoAvaliacao;
import br.com.sistema.ui.AppContext;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class AvaliacaoFormController {

    @FXML private ComboBox<Turma>         cbTurma;
    @FXML private ComboBox<TipoAvaliacao> cbTipo;
    @FXML private TextField               txtTitulo;
    @FXML private TextField               txtValor;
    @FXML private TextField               txtPeso;
    @FXML private Label                   lblMensagem;

    private final AppContext ctx = AppContext.getInstance();

    @FXML
    private void initialize() {
        refreshTurmas();
        cbTipo.setItems(FXCollections.observableArrayList(TipoAvaliacao.values()));
    }

    @FXML
    private void handleRegistrar() {
        Turma turma        = cbTurma.getValue();
        TipoAvaliacao tipo = cbTipo.getValue();
        String titulo      = txtTitulo.getText().trim();
        String valorStr    = txtValor.getText().trim();
        String pesoStr     = txtPeso.getText().trim();

        if (turma == null || tipo == null || titulo.isEmpty()
                || valorStr.isEmpty() || pesoStr.isEmpty()) {
            showError("Preencha todos os campos obrigatórios.");
            return;
        }

        double valor, peso;
        try {
            valor = Double.parseDouble(valorStr);
            peso  = Double.parseDouble(pesoStr);
        } catch (NumberFormatException e) {
            showError("Valor e Peso devem ser números decimais (ex.: 10.0, 0.4).");
            return;
        }

        try {
            ctx.getController().registrarAvaliacao(
                    ctx.getUsuarioAtual(),
                    turma.getCodigo(),
                    new AvaliacaoDTO(titulo, valor, peso, tipo));

            showSuccess("Avaliação registrada em \"" + turma.getCodigo() + "\" com sucesso!");
            handleLimpar();

        } catch (AcademicSystemException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void handleLimpar() {
        refreshTurmas();
        cbTipo.getSelectionModel().clearSelection();
        txtTitulo.clear();
        txtValor.clear();
        txtPeso.clear();
        lblMensagem.setText("");
        lblMensagem.getStyleClass().removeAll("msg-error", "msg-success");
    }

    private void refreshTurmas() {
        List<Turma> turmas = new ArrayList<>(ctx.getController().listarTurmas().values());
        cbTurma.setItems(FXCollections.observableArrayList(turmas));
    }

    private void showSuccess(String msg) {
        lblMensagem.getStyleClass().removeAll("msg-error");
        lblMensagem.getStyleClass().add("msg-success");
        lblMensagem.setText("OK  " + msg);
    }

    private void showError(String msg) {
        lblMensagem.getStyleClass().removeAll("msg-success");
        lblMensagem.getStyleClass().add("msg-error");
        lblMensagem.setText("X  " + msg);
    }
}
