package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class ManageLogController {

    @FXML
    private ListView logListView;
    @FXML
    private Label manageLogStateLabel;

    public void init() {
        ObservableList<String> list = FXCollections.observableArrayList();

        //获取路径
        String path = Paths.get("").toAbsolutePath().toString() + "\\resource\\log";

        File file = new File(path);

        File[] filelist = file.listFiles();

        for (File f : filelist) {
            list.add(f.getName());
        }

        logListView.setPlaceholder(new Label("Log列表"));

        logListView.setItems(list);
    }



    @FXML
    private void editLogButtonAction() {

        String fileName = String.valueOf(logListView.getSelectionModel().getSelectedItems().get(0));
        String path = Paths.get("").toAbsolutePath().toString() + "\\resource\\log\\" + fileName;

        Desktop desk = Desktop.getDesktop();

        File file = new File(path);
        try {
            desk.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void deleteLogButtonAction() {

        String alertMessage = "确认删除" + String.valueOf(logListView.getSelectionModel().getSelectedItems().get(0)) + "?";
        Alert alertWindow = new Alert(Alert.AlertType.NONE,alertMessage
                ,new ButtonType("确认",ButtonBar.ButtonData.YES)
                ,new ButtonType("取消",ButtonBar.ButtonData.NO));

        alertWindow.setTitle("删除确认");

        //Java8引入。主要解决空指针异常。本质上是个包含有可选值的包装类，其既可以含有对象也可以为空。
        Optional confirm = alertWindow.showAndWait();

        if(confirm.get().toString().equals("ButtonType [text=确认, buttonData=YES]")) {
            String fileName = String.valueOf(logListView.getSelectionModel().getSelectedItems().get(0));
            String path = Paths.get("").toAbsolutePath().toString() + "\\resource\\log\\" + fileName;

            File file = new File(path);
            file.delete();

            //更新列表
            ObservableList<String> list = FXCollections.observableArrayList();

            //获取路径
            path = Paths.get("").toAbsolutePath().toString() + "\\resource\\log";
            file = new File(path);
            File[] filelist = file.listFiles();
            for (File f : filelist) {
                list.add(f.getName());
            }
            logListView.setPlaceholder(new Label("Log列表"));
            logListView.setItems(list);




        }







    }



}
