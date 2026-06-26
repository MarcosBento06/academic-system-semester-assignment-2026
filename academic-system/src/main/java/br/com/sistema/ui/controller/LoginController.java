package br.com.sistema.ui.controller;

import br.com.sistema.model.Usuario;
import br.com.sistema.security.AuthenticationService;
import br.com.sistema.security.exception.AuthenticationException;
import br.com.sistema.ui.AppContext;
import br.com.sistema.ui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Label         lblErro;

    private final AuthenticationService authService =
            AppContext.getInstance().getAuthService();

    @FXML
    private void handleLogin() {
        String usuario = txtUsuario.getText().trim();
        String senha   = txtSenha.getText();

        if (usuario.isEmpty() || senha.isEmpty()) {
            lblErro.setText("Preencha usuário e senha.");
            return;
        }

        try {
            Usuario usuarioAutenticado = authService.autenticar(usuario, senha);

            lblErro.setText("");
            AppContext.getInstance().setUsuarioAtual(usuarioAutenticado);

            MainApp.showMain();

        } catch (AuthenticationException e) {
            lblErro.setText("Usuário ou senha inválidos.");
            txtSenha.clear();
        }
    }
}
