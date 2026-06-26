package br.com.sistema.ui.controller;

import br.com.sistema.persistence.PersistenceType;
import br.com.sistema.security.exception.AuthorizationException;
import br.com.sistema.ui.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class PersistenciaController {

    @FXML private ToggleGroup grpTipo;
    @FXML private RadioButton rbTxt;
    @FXML private RadioButton rbXml;
    @FXML private RadioButton rbJson;
    @FXML private Label       lblMensagem;
    @FXML private Label       lblAtual;

    private final AppContext ctx = AppContext.getInstance();

    @FXML
    private void initialize() {
        try {
            PersistenceType atual = ctx.getController().obterPersistenciaAtual();
            atualizarLblAtual(atual);
            switch (atual) {
                case XML  -> grpTipo.selectToggle(rbXml);
                case JSON -> grpTipo.selectToggle(rbJson);
                default   -> grpTipo.selectToggle(rbTxt);
            }
        } catch (Exception e) {
            showError("Erro ao carregar configuração atual: " + e.getMessage());
        }
    }

    @FXML
    private void handleAplicar() {
        Toggle selecionado = grpTipo.getSelectedToggle();
        if (selecionado == null) {
            showError("Selecione um formato de persistência.");
            return;
        }

        String userData = (String) selecionado.getUserData();
        PersistenceType tipo = PersistenceType.valueOf(userData);

        try {
            ctx.getController().configurarPersistencia(ctx.getUsuarioAtual(), tipo);
            atualizarLblAtual(tipo);
            showSuccess("Persistência configurada para " + tipo + ".");
        } catch (AuthorizationException e) {
            showError("Acesso negado: " + e.getMessage());
        } catch (Exception e) {
            showError("Erro: " + e.getMessage());
        }
    }

    @FXML
    private void handleSalvar() {
        try {
            ctx.getController().salvarDados();
            showSuccess("Dados salvos com sucesso em "
                    + ctx.getController().obterPersistenciaAtual() + ".");
        } catch (Exception e) {
            showError("Erro ao salvar: " + e.getMessage());
        }
    }

    private void atualizarLblAtual(PersistenceType tipo) {
        lblAtual.setText("Formato atual: " + tipo);
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
