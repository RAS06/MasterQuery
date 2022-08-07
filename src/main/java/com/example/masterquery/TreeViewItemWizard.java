package com.example.masterquery;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TreeViewItemWizard extends Dialog<TreeItem<String>> {

    private TreeItem<String> treeItem;

    private TextField nodeName;
    private TextField parentName;
    private Label errorMessage = new Label(null);

    public TreeViewItemWizard(TreeItem<String> initItem){
        super();
        this.setTitle("Add Node");
        buildUI();
    }

    private void buildUI() {
        Pane pane = createGridPane();
        getDialogPane().setContent(pane);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!validateDialog()){
                    actionEvent.consume();
                }
            }

            private boolean validateDialog(){
                if(nodeName.getText().isEmpty() || parentName.getText().isEmpty() || Controller.getTreeItemByName(parentName.getText()) == null){
                    errorMessage.setText("Either a field is blank or no such parent category exists.");
                    return false;
                }
                String s = nodeName.getText();
                String str = parentName.getText();
                treeItem = new TreeItem<String>(s);
                Controller.getTreeItemByName(str).getChildren().add(treeItem);
                Controller.treeItems.add(treeItem);
                String pathway = "src/main/resources/com/example/masterquery/" + s + ".txt";
                Path p = Paths.get(pathway);
                try {
                    Path f = Files.createFile(p);
                    Controller.textAreaFileNames.add(pathway);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    FileWriter fw = new FileWriter("src/main/resources/com/example/masterquery/treeData.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    if(countLines("src/main/resources/com/example/masterquery/treeData.txt") == 0) {
                        bw.write("\"" + s + "\" " + str);
                    }
                    else {
                        bw.write("\n\"" + s + "\" " + str);
                    }
                    bw.close();
                } catch(IOException ioe){
                    ioe.printStackTrace();
                }

                return true;
            }
        });
    }


    public Pane createGridPane(){
        VBox content = new VBox(18);
        content.setPrefWidth(600);

        Label nodeNameLabel = new Label("Category Name");
        Label parentNodeLabel = new Label("Subcategory of");
        errorMessage.setStyle("-fx-text-fill: #8d0000");
        this.nodeName = new TextField();
        this.parentName = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(nodeNameLabel, 0, 0);
        grid.add(parentNodeLabel, 0, 1);
        grid.add(errorMessage, 0, 4);
        grid.add(nodeName, 1, 0);
        GridPane.setHgrow(this.nodeName, Priority.ALWAYS);
        grid.add(parentName, 1, 1);
        GridPane.setHgrow(this.parentName, Priority.ALWAYS);

        content.getChildren().add(grid);

        return content;
    }

    public static long countLines(String fileName) {

        Path path = Paths.get(fileName);

        long lines = 0;
        try {

            lines = Files.lines(path).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;

    }

}
