package com.example.masterquery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    public static AnchorPane anchorPaneOne = new AnchorPane();
    @FXML
    public static VBox box = new VBox();
    @FXML
    public static ScrollPane sp = new ScrollPane();
    @FXML
    public Button button = new Button("Resize");
    @FXML
    public TreeView<String> treeView = new TreeView<String>();

    public ArrayList<TreeItem<String>> treeItems = new ArrayList<TreeItem<String>>();


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void resize(){
        sp.prefHeightProperty().bind(sp.heightProperty());
        System.out.println("reached");
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
    public TreeItem<String> getTreeItemByName(String name){
        for(TreeItem<String> t: treeItems){
            if(t.getValue().equals(name))
                return t;
        }
        return new TreeItem<>();
    }
}