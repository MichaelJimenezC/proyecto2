/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Pauleth
 */
public class MenuCargaController implements Initializable {

    @FXML
    VBox partidas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSavedGames();
    }

    @FXML
    public void regresar(ActionEvent event) {
        try {
            App.setRoot("opciones");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void loadSavedGames() {
        // Suponiendo que los juegos se guardan en el directorio de trabajo del proyecto
        File dir = new File(System.getProperty("user.dir"));
        File[] files = dir.listFiles((d, name) -> name.startsWith("game_") && name.endsWith(".dat"));
        Game game = new Game();

        if (files != null) {
            for (File file : files) {
                String[] datos = file.getName().split("_");
                System.out.println(datos[2]);
                String modo = datos[2].split("\\.")[0];
                String mode = "";
                if (modo.equals("AI")) {
                    mode = "Player vs IA";
                } else if (modo.equals("Player")) {
                    mode = "Player vs Player";
                } else if (modo.equals("AIvsAI")) {
                    mode = "IA vs IA";
                }
                Button button = new Button(mode + "\n" + formatDateString(datos[1]));
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: center-left; -fx-font-size: 18;");

                button.setPrefSize(531, 63);
                button.setOnAction(event -> {
                    game.start(new Stage());
                    game.loadGame(file.getName());
                });
                partidas.getChildren().add(button);
                System.out.println("Añadiendo" + file.getName());
            }
        }
    }

    public static String formatDateString(String dateTimeStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(outputFormatter);
    }

}

