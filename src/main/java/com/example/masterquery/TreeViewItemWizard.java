package com.example.masterquery;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class TreeViewItemWizard extends Dialog<TreeItem<String>> {

    private TreeItem<String> treeItem;

    private TextField nodeName;
    private TextField parentName;

    public TreeViewItemWizard(){
        super();
        this.setTitle("Add Node");
        buildUI();
        setPropertyBindings();
        setResultConverter();
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
                if(nodeName.getText().isEmpty() || parentName.getText().isEmpty()){
                    return false;
                }
                return true;
            }
        });
    }

    public void setPropertyBindings(){
        //treeItem = new TreeItem<String>(nodeName.textProperty().toString());
        System.out.println(nodeName.textProperty().getValue());
    }

    public void setResultConverter() {
        Callback<ButtonType, TreeItem<String>> treeItemResultConverter =  new Callback<ButtonType, TreeItem<String>>() {
            @Override
            public TreeItem<String> call(ButtonType param) {
                if(param == ButtonType.OK){
                    return treeItem;
                } else
                return null;
            }
        };
    }

    public Pane createGridPane(){
        VBox content = new VBox(18);

        Label nodeNameLabel = new Label("Category Name");
        Label parentNodeLabel = new Label("Subcategory of");
        this.nodeName = new TextField();
        this.parentName = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.add(nodeNameLabel, 0, 0);
        grid.add(parentNodeLabel, 0, 1);
        grid.add(nodeName, 1, 0);
        GridPane.setHgrow(this.nodeName, Priority.ALWAYS);
        grid.add(parentName, 1, 1);
        GridPane.setHgrow(this.parentName, Priority.ALWAYS);

        content.getChildren().add(grid);

        return content;
    }


}
