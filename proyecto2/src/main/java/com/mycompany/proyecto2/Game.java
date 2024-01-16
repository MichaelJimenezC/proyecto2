/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2;

import com.mycompany.proyecto2.MenuModosController;
import com.mycompany.proyecto2.App;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import logica.Tree;

/**
 *
 * @author Michael
 */
public class Game extends Application implements Serializable {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private Board board;
    private transient Canvas canvas;
    private transient Image imageBackground, imageX, imageO;

    private transient javafx.geometry.Point2D[] cells;
    private static final int DISTANCE = 100;
    public static List<Board> intermediateBoards = new ArrayList<>();

    private enum Mode {
        Player, AI, AIvsAI
    }
    private Mode mode;
    @FXML
    private transient BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btnSave = new Button("Guardar Partida");
        btnSave.setOnAction(event -> saveGame());
        Button btnBack = new Button("Regresar");
        btnBack.setOnAction(event -> regresar());
        HBox botones = new HBox();
        botones.getChildren().addAll(btnBack, btnSave);
        botones.setAlignment(Pos.CENTER);
        botones.setSpacing(20);
        board = new Board();
        loadCells();
        loadImages();

        canvas = new Canvas(WIDTH, HEIGHT);
        canvas.setOnMouseClicked(this::handleMouseClick);
        VBox layout = new VBox(10, botones, canvas); // Añadir el botón debajo del canvas
        layout.setAlignment(Pos.CENTER);
        root = new BorderPane(layout);
        root.setAlignment(canvas, Pos.CENTER);

        primaryStage.setTitle("Lazo's Tic Tac Toe");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();

        draw();
        determineMode();
    }

    private void regresar() {

        App.cerrar(root);
        App app = new App();
        try {
            app.start(new Stage());
            app.setRoot("menuModos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void determineMode() {
        if (MenuModosController.modo.equals("AI")) {
            mode = Mode.AI;
        } else if (MenuModosController.modo.equals("Player")) {
            mode = Mode.Player;
        } else if (MenuModosController.modo.equals("AIvsAI")) {
            mode = Mode.AIvsAI;
        }
        canvas.setOnMouseClicked(this::handleMouseClick);
    }

   

    private void playMoveAI() {
        Algorithms.miniMax(board);
        draw();
        System.out.println(intermediateBoards.size());
    }

    private void loadCells() {
        cells = new javafx.geometry.Point2D[9];

        cells[0] = new javafx.geometry.Point2D(109, 109);
        cells[1] = new javafx.geometry.Point2D(299, 109);
        cells[2] = new javafx.geometry.Point2D(489, 109);
        cells[3] = new javafx.geometry.Point2D(109, 299);
        cells[4] = new javafx.geometry.Point2D(299, 299);
        cells[5] = new javafx.geometry.Point2D(489, 299);
        cells[6] = new javafx.geometry.Point2D(109, 489);
        cells[7] = new javafx.geometry.Point2D(299, 489);
        cells[8] = new javafx.geometry.Point2D(489, 489);
    }

    private void loadImages() {
        imageBackground = getImage("background");
        imageX = getImage("x");
        imageO = getImage("o");
    }

    private Image getImage(String path) {
        try {
            path = "/img/" + path + ".png";
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            throw new RuntimeException("Image could not be loaded.");
        }
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        setProperties(gc);
        paintBoard(gc);
        paintWinner(gc);
    }

    private void setProperties(GraphicsContext gc) {
        gc.setLineWidth(5);
        gc.drawImage(imageBackground, 0, 0, WIDTH, HEIGHT);
    }

    private void paintBoard(GraphicsContext gc) {
        Board.State[][] boardArray = board.toArray();

        int offset = 20;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (boardArray[y][x] == Board.State.X) {
                    gc.drawImage(imageX, offset + 190 * x, offset + 190 * y, 150, 150);
                } else if (boardArray[y][x] == Board.State.O) {
                    gc.drawImage(imageO, offset + 190 * x, offset + 190 * y, 150, 150);
                }
            }
        }
    }

    private void paintWinner(GraphicsContext gc) {
        if (board.isGameOver()) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("TimesRoman", 50));

            String s;

            if (board.getWinner() == Board.State.Blank) {
                s = "Draw";
            } else {
                s = board.getWinner() + " Wins!";
            }

            gc.fillText(s, WIDTH / 2 - gc.getFont().getSize() / 2, 315);
        }
    }

    private void handleMouseClick(MouseEvent event) {
        if (board.isGameOver()) {
            board.reset();
            draw();
        } else {
            if (mode == Mode.Player || mode == Mode.AI) {
                playMove(event);
            }
        }
    }

    private void playMove(MouseEvent event) {
        int move = getMove(new javafx.geometry.Point2D(event.getX(), event.getY()));
        if (move != -1 && board.move(move)) {
            draw();
            if (mode == Mode.AI) {
                playMoveAI();
            }
        }
    }

    private int getMove(javafx.geometry.Point2D point) {
        for (int i = 0; i < cells.length; i++) {
            if (distance(cells[i], point) <= DISTANCE) {
                return i;
            }
        }
        return -1;
    }

    private double distance(javafx.geometry.Point2D p1, javafx.geometry.Point2D p2) {
        double xDiff = p1.getX() - p2.getX();
        double yDiff = p1.getY() - p2.getY();

        double xDiffSquared = xDiff * xDiff;
        double yDiffSquared = yDiff * yDiff;

        return Math.sqrt(xDiffSquared + yDiffSquared);
    }

    @FXML
    public void regresar(ActionEvent event) {
        try {
            App.setRoot("opciones");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void mostrar(Board board) {
        this.board = board;
    }

}
