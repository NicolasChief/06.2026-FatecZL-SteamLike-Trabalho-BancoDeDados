package edu.curso.view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PublicarUI {

    public void start(Stage stage){

        BorderPane bp = new BorderPane();

        Scene scn = new Scene(bp, 1520, 780);

        stage.setScene(scn);
        stage.show();

    }

}
