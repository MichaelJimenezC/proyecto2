/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyecto2;

import com.mycompany.proyecto2.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class OpcionesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void nuevoJuego(ActionEvent event) {
        try {
            App.setRoot("menuModos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void cargarJuego(ActionEvent event) {
        try {
            App.setRoot("menuCarga");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void regresar(ActionEvent event) {
        try {
            App.setRoot("menu");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    } 
}
