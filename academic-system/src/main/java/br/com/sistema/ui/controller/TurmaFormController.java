package br.com.sistema.ui.controller;

import br.com.sistema.dto.TurmaDTO;
import br.com.sistema.exception.AcademicSystemException;
import br.com.sistema.security.exception.AuthorizationException;
import br.com.sistema.ui.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TurmaFormController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtDisciplina;
    @FXML private Label     lblMensagem;

    private final AppContext ctx = AppContext.getInstance();

    @FXML
    private void handleCadastrar() {
        String codigo     = txtCodigo.getText().trim();
        String disciplina = txtDisciplina.getText().trim();

        if (codigo.isEmpty() || disciplina.isEmpty()) {
            showError("Preencha todos os campos obrigatórios.");
            return;
        }

        try {
            ctx.getController().registrarTurma(
                    ctx.getUsuarioAtual(),
                    new TurmaDTO(codigo, disciplina));
            showSuccess("Turma \"" + codigo + "\" cadastrada com sucesso!");
            handleLimpar();
        } catch (AuthorizationException e) {
            showError("Acesso negado: " + e.getMessage());
        } catch (AcademicSystemException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError("Erro inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void handleLimpar() {
        txtCodigo.clear();
        txtDisciplina.clear();
        lblMensagem.setText("");
        lblMensagem.getStyleClass().removeAll("msg-error", "msg-success");
        txtCodigo.requestFocus();
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
