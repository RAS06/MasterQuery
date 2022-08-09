package com.example.masterquery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Button saveButton = new Button();
    @FXML
    public Button saveAllButton = new Button();
    @FXML
    public Button deleteButton = new Button();
    @FXML
    public TreeView<String> treeView = new TreeView<String>();
    @FXML
    public TextArea textArea = new TextArea();

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
        textArea.setText("");
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        String s;
        if(selectedItem != null){
            s = selectedItem.getValue();
            String ss = "";
            for(int i = 0; i < s.length(); i++){
                if(!s.substring(i, i + 1).equals(" ")){
                  ss += s.substring(i, i+ 1);
                }
            }
            try {
                FileInputStream fis = new FileInputStream("src/main/resources/com/example/masterquery/" + ss + ".txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                boolean started = false;
                while(((line = br.readLine()) != null)) {
                    if(!started) {
                        textArea.appendText(line);
                        started = true;
                    } else{
                        textArea.appendText("\n" + line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static TreeItem<String> getTreeItemByName(String name){
        for(TreeItem<String> t: treeItems){
            if(t.getValue().equals(name))
                return t;
        }
        return null;
    }

    public void save() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        overwrite(selectedItem);
    }

    public void saveAll(){
        //for(TreeItem<String> item: treeItems){
            //overwrite(item);
        //}
    }
    public void overwrite(TreeItem<String> selectedItem){
        if(selectedItem != null) {
            try {
                String s = selectedItem.getValue();
                String ss = "";
                for(int i = 0; i < s.length(); i++){
                    if(!s.substring(i, i + 1).equals(" ")){
                        ss += s.substring(i, i + 1);
                    }
                }
                if(ss != null) {
                    FileWriter fw = new FileWriter("src/main/resources/com/example/masterquery/" + ss + ".txt", false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(textArea.getText());
                    bw.close();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void deleteNode(){
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem != null && !selectedItem.getValue().equals("Root Node")) {
            String s = selectedItem.getValue();
            String ss = "";
            for(int i = 0; i < s.length(); i++){
                if(!s.substring(i, i + 1).equals(" ")){
                    ss += s.substring(i, i+ 1);
                }
            }
            if(ss != null) {
                selectedItem.getParent().getChildren().remove(selectedItem);
                Path p = Paths.get("src/main/resources/com/example/masterquery/" + ss + ".txt");
                try {
                    Files.deleteIfExists(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}