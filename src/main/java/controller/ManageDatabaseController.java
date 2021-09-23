package controller;


import VO.DatabaseVO;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.file.Paths;


public class ManageDatabaseController {


    @FXML
    private TableView databaseManageTable;

    public void init() {

        //TODO:为快速实现相关功能，暂先不实现表格上的编辑处理，主要的工作还是集中在按钮功能上。
        //databaseManageTable.setEditable(true);

        TableColumn nameColumn = new TableColumn("名称");
        TableColumn typeColumn = new TableColumn("类型");
        TableColumn addressColumn = new TableColumn("地址");
        TableColumn portColumn = new TableColumn("端口");
        TableColumn schemaColumn = new TableColumn("数据库");
        TableColumn userColumn = new TableColumn("用户名");
        TableColumn passwordColumn = new TableColumn("密码");
        TableColumn memoColumn = new TableColumn("备注");
        TableColumn stateColumn = new TableColumn("状态");

        databaseManageTable.getColumns().addAll(nameColumn,typeColumn,addressColumn,portColumn
                ,schemaColumn,userColumn,passwordColumn,memoColumn,stateColumn);

        String path = Paths.get("").toAbsolutePath().toString();
        ObservableList list = FXCollections.observableArrayList();

        FileReader databaseFile = null;
        try {
            databaseFile = new FileReader(path + "\\resource\\databaseInfomation\\databaseInfomation.dbinfo");

            BufferedReader reader = new BufferedReader(databaseFile);

            //TODO:这里的读取思路超级傻批，但是先暂时这么实现一下。到时候要修正。

            reader.readLine();
            reader.readLine();
            String check = reader.readLine();
            while(check != null) {
                if(check.contains("Name:")) {


                    DatabaseVO databaseInfo = new DatabaseVO();
                    databaseInfo.setDatabaseName(check.substring(5));
                    databaseInfo.setDatabaseType(reader.readLine().substring(5));
                    databaseInfo.setHost(reader.readLine().substring(8));
                    databaseInfo.setPort(reader.readLine().substring(5));
                    databaseInfo.setSchema(reader.readLine().substring(7));
                    databaseInfo.setUser(reader.readLine().substring(5));
                    databaseInfo.setPassword(reader.readLine().substring(9));
                    databaseInfo.setMemo(reader.readLine().substring(5));

                    list.add(databaseInfo);

                }

                check = reader.readLine();
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("databaseName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("databaseType"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("host"));
            portColumn.setCellValueFactory(new PropertyValueFactory<>("port"));
            schemaColumn.setCellValueFactory(new PropertyValueFactory<>("schema"));
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            memoColumn.setCellValueFactory(new PropertyValueFactory<>("memo"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

            databaseManageTable.setItems(list);

            databaseFile.close();
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    @FXML
    private void deleteDatabaseAction() {

        DatabaseVO data = (DatabaseVO)databaseManageTable.getSelectionModel().getSelectedItem();

        //删除文件中记录
        String path = Paths.get("").toAbsolutePath().toString();
        File oldFile = new File(path + "\\resource\\databaseInfomation\\databaseInfomation.dbinfo");
        FileReader databaseFile = null;
        String line = null;

        try {
            File newFile = new File(path + "\\resource\\databaseInfomation\\databaseInfomation.dbinfo.tmp");
            databaseFile = new FileReader(path + "\\resource\\databaseInfomation\\databaseInfomation.dbinfo");
            BufferedReader reader = new BufferedReader(databaseFile);
            PrintWriter writer = new PrintWriter(newFile);
            while((line = reader.readLine()) != null) {

                if(line.equals("Name:"+data.getDatabaseName())){
                    //跳过7行，与文件记录格式约定有关
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                }
                else {
                    writer.println(line);
                    writer.flush();
                }
            }

            databaseFile.close();
            reader.close();
            writer.close();

            oldFile.delete();
            newFile.renameTo(oldFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        //刷新页面
        init();

    }


}
