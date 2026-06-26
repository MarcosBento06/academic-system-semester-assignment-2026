package br.com.sistema.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainApp extends Application {

    private static final Logger logger = java.util.logging.Logger.getLogger(MainApp.class.getName());
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Academic System");
        primaryStage.setResizable(true);
        showLogin();
    }

    public static void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/br/com/sistema/ui/login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setWidth(420);
            primaryStage.setHeight(520);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            logger.severe("Erro ao carregar tela de login: " + e.getMessage());
        }
    }

    public static void showMain() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("/br/com/sistema/ui/main.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setWidth(900);
            primaryStage.setHeight(600);
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {
            logger.severe("Erro ao carregar tela principal: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
