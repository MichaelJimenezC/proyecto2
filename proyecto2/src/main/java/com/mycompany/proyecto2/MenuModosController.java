/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyecto2;

import com.mycompany.proyecto2.App;
import com.mycompany.proyecto2.Game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class MenuModosController implements Initializable {

    @FXML
    Pane Pane1;
    public static String modo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void iniciarJuego() {
        Game gameUI = new Game();
        gameUI.start(new Stage()); // Esto lanzará la nueva ventana del juego
    }

    @FXML
    public void cambiarPI(ActionEvent event) {
        modo = "AI";
        App.cerrar(Pane1);
        iniciarJuego();
    }

    @FXML
    public void cambiarII(ActionEvent event) {
        modo = "AIvsAI";
        App.cerrar(Pane1);
        System.out.println("ia vs ia");
        iniciarJuego();

    }

    @FXML
    public void cambiarPP(ActionEvent event) {
        modo = "Player";
        App.cerrar(Pane1);
        System.out.println("player vs player");
        iniciarJuego();

    }

    @FXML
    public void regresar(ActionEvent event) {
        try {
            System.out.println("Cambiando a pagina que dice nueva partida o cargar");
            App.setRoot("opciones");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
