package br.com.sistema.ui.controller;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.ui.AppContext;
import br.com.sistema.ui.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML private Label lblUserInfo;

    @FXML private Button btnTurmas;
    @FXML private Button btnAvaliacoes;
    @FXML private Button btnVisualizar;
    @FXML private Button btnRelatorios;
    @FXML private Button btnPersistencia;
    @FXML private Button btnSalvar;

    @FXML private StackPane contentArea;

    private final AppContext ctx = AppContext.getInstance();

    @FXML
    private void initialize() {
        Usuario usuario = ctx.getUsuarioAtual();
        if (usuario != null) {
            lblUserInfo.setText(usuario.getNome() + "  [" + usuario.getPapel() + "]");
        }

        boolean isAdmin = usuario != null && usuario.getPapel() == PapelUsuario.ADMIN;

        btnTurmas.setVisible(isAdmin);
        btnTurmas.setManaged(isAdmin);
        btnPersistencia.setVisible(isAdmin);
        btnPersistencia.setManaged(isAdmin);

        if (isAdmin) {
            showTurmas();
        } else {
            showAvaliacoes();
        }
    }

    @FXML
    private void showTurmas() {
        loadContent("/br/com/sistema/ui/turma_form.fxml");
        highlight(btnTurmas);
    }

    @FXML
    private void showAvaliacoes() {
        loadContent("/br/com/sistema/ui/avaliacao_form.fxml");
        highlight(btnAvaliacoes);
    }

    @FXML
    private void showVisualizacao() {
        loadContent("/br/com/sistema/ui/visualizacao.fxml");
        highlight(btnVisualizar);
    }

    @FXML
    private void showRelatorios() {
        loadContent("/br/com/sistema/ui/relatorios.fxml");
        highlight(btnRelatorios);
    }

    @FXML
    private void showPersistencia() {
        loadContent("/br/com/sistema/ui/persistencia.fxml");
        highlight(btnPersistencia);
    }

    @FXML
    private void handleSalvar() {
        try {
            ctx.getController().salvarDados();
            highlight(btnSalvar);
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Salvar Dados");
            alert.setHeaderText(null);
            alert.setContentText("Dados salvos com sucesso!");
            alert.setGraphic(null);
            alert.showAndWait();
        } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao salvar: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLogout() {
        ctx.getAuthService().logout(
                ctx.getUsuarioAtual() != null ? ctx.getUsuarioAtual().getNome() : "?");
        ctx.setUsuarioAtual(null);
        MainApp.showLogin();
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            Label err = new Label("Erro ao carregar tela: " + e.getMessage());
            contentArea.getChildren().setAll(err);
        }
    }

    private void highlight(Button active) {
        for (Button b : new Button[]{btnTurmas, btnAvaliacoes, btnVisualizar,
                                      btnRelatorios, btnPersistencia, btnSalvar}) {
            b.getStyleClass().remove("sidebar-btn-active");
        }
        active.getStyleClass().add("sidebar-btn-active");
    }
}
