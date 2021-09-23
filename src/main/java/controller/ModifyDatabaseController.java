package controller;

import VO.DatabaseVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class ModifyDatabaseController {


    @FXML
    private TextField dbname;

    @FXML
    private ChoiceBox dbType;

    @FXML
    private TextField dbaddress;

    @FXML
    private TextField dbport;

    @FXML
    private TextField dbuser;

    @FXML
    private TextField dbpassword;

    @FXML
    private TextField dbmemo;

    @FXML
    private Label state;

    @FXML
    private TextField dbschema;

    @FXML
    protected void init() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("MySQL","Oracle","MongoDB","Redis");
        dbType.setItems(list);
        dbType.getSelectionModel().select(0);
    }

    @FXML
    private void createNewDatabaseAction () {

        DatabaseVO databaseInfo = new DatabaseVO();

        //TODO:这里最好加一个文本验证的功能
        if(dbname.getText() == "" || dbaddress.getText() == "" || dbport.getText() == "" || dbuser.getText() == "" || dbpassword.getText() == "" || dbmemo.getText() == "") {
            state.setText("所有文本框信息均不能为空！");
        }
        else{
            databaseInfo.setDatabaseName(dbname.getText());
            System.out.println(dbType.getItems());
            databaseInfo.setDatabaseType(dbType.getItems().get(0).toString());
            databaseInfo.setHost(dbaddress.getText());
            databaseInfo.setPort(dbport.getText());
            databaseInfo.setSchema(dbschema.getText());
            databaseInfo.setUser(dbuser.getText());
            databaseInfo.setPassword(dbpassword.getText());
            databaseInfo.setMemo(dbmemo.getText());
            databaseInfo.setState("未验证");
        }

        String path = Paths.get("").toAbsolutePath().toString();

        try {
            FileWriter fileWriter = new FileWriter(path + "\\resource\\databaseInfomation\\" + "databaseInfomation.dbinfo",true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            StringBuilder builder = new StringBuilder();

            builder.append("Name:" + databaseInfo.getDatabaseName() + "\n");
            builder.append("Type:" + databaseInfo.getDatabaseType() + "\n");
            builder.append("Address:"+databaseInfo.getHost() + "\n");
            builder.append("Port:"+databaseInfo.getPort() + "\n");
            builder.append("Schema:"+databaseInfo.getSchema() + "\n");
            builder.append("User:"+databaseInfo.getUser()+"\n");
            builder.append("Password:"+ databaseInfo.getPassword() + "\n");
            builder.append("Memo:" + databaseInfo.getMemo() + "\n");

            writer.write("\n\n");
            writer.write(builder.toString());

            writer.close();
            fileWriter.close();

            state.setText("数据库创建成功");



        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
