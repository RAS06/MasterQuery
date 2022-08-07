package com.example.masterquery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Dialog;

public class Controller implements Initializable {
    @FXML
    public static AnchorPane anchorPaneOne = new AnchorPane();
    @FXML
    public static VBox box = new VBox();
    @FXML
    public static ScrollPane sp = new ScrollPane();
    @FXML
    public Button button = new Button();
    @FXML
    public Button printButton = new Button();
    @FXML
    public TreeView<String> treeView = new TreeView<String>();

    public static ArrayList<TreeItem<String>> treeItems = new ArrayList<TreeItem<String>>();
    public static ArrayList<String> textAreaFileNames = new ArrayList<String>();

    @FXML
    public void resize(){
        sp.prefHeightProperty().bind(sp.heightProperty());
        System.out.println("reached");
    }

    @FXML
    public void addNode(){
        Dialog<TreeItem<String>> treeItemWizard = new TreeViewItemWizard(new TreeItem<String>(null));
        Optional<TreeItem<String>> result = treeItemWizard.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> root = new TreeItem<>("Root Node");
        treeItems.add(root);
        constructTreeView("src/main/resources/com/example/masterquery/treeData.txt");
        treeView.setRoot(root);
    }

    public void constructTreeView(String file){
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            String nodeName = "";
            String parentNode;
            int curr = 0;
            while(((line = br.readLine()) != null)){
                //Isolate name of node that is in quotation marks
                while(!line.substring(curr, curr + 1).equals("\"")){
                    curr++;
                }
                curr++;
                while(!line.substring(curr, curr + 1).equals("\"")){
                    nodeName += line.substring(curr, curr+1);
                    curr++;
                }
                curr += 2;
                //Get the Name of the parent node which is sitting after the second quote
                parentNode = line.substring(curr);
                //Add the new TreeItem to the treeItems ArrayList using the nodeName retrieved from the .txt file.
                treeItems.add(new TreeItem<>(nodeName));
                textAreaFileNames.add("src/main/resources/com/example/masterquery/" + nodeName + ".txt");
                //And add it to the children of its parent node, also retrieved from the .txt file
                getTreeItemByName(parentNode).getChildren().add(treeItems.get(treeItems.size() - 1));
                nodeName = "";
                parentNode = "";
                curr = 0;
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    public void selectNode(){

    }
    public static TreeItem<String> getTreeItemByName(String name){
        for(TreeItem<String> t: treeItems){
            if(t.getValue().equals(name))
                return t;
        }
        return null;
    }

    public void printData() {
        System.out.println(treeItems);
        System.out.println(textAreaFileNames);
    }
}