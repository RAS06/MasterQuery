package com.example.masterquery;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("MasterQuery");
        scene.getStylesheets().add(getClass().getResource("Query.css").toExternalForm());


        //Controller.sp.prefHeightProperty().bind(Controller.sp.heightProperty());

//        AnchorPane.setBottomAnchor(Controller.sp, 0.0);
//        AnchorPane.setTopAnchor(Controller.sp, 0.0);

        

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}