package br.com.sistema.ui.controller;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.enums.PapelUsuario;
import br.com.sistema.ui.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RelatoriosController {

    @FXML private Button   btnPersistencia;
    @FXML private TextArea txtRelatorio;

    private final AppContext ctx = AppContext.getInstance();

    @FXML
    private void initialize() {
        Usuario usuario = ctx.getUsuarioAtual();
        boolean isAdmin = usuario != null && usuario.getPapel() == PapelUsuario.ADMIN;

        btnPersistencia.setVisible(isAdmin);
        btnPersistencia.setManaged(isAdmin);
    }

    @FXML
    private void handleResumo() {
        try {
            String relatorio = ctx.getController()
                    .gerarRelatorioResumoAvaliacoes(ctx.getUsuarioAtual());
            txtRelatorio.setText(relatorio);
        } catch (Exception e) {
            txtRelatorio.setText("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @FXML
    private void handlePeso() {
        try {
            String relatorio = ctx.getController()
                    .gerarRelatoriopesoAvaliacoes(ctx.getUsuarioAtual());
            txtRelatorio.setText(relatorio);
        } catch (Exception e) {
            txtRelatorio.setText("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @FXML
    private void handlePersistencia() {
        try {
            String relatorio = ctx.getController()
                    .gerarRelatorioConfiguracaoPersistencia(ctx.getUsuarioAtual());
            txtRelatorio.setText(relatorio);
        } catch (Exception e) {
            txtRelatorio.setText("Acesso negado: " + e.getMessage());
        }
    }
}
