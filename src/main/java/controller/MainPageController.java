package controller;

import VO.DatabaseVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPageController {

    /**
     * @description: 0:数据库连接未检查状态 1：语句未检查状态 2:语句可执行状态
     */
    private static int checkFlag = 0;
    private static String checkingSql = null;

    @FXML
    private TextArea SQLInput;
    @FXML
    private TextArea actionLog;
    @FXML
    private TextField programmer;
    @FXML
    private TextField checker;
    @FXML
    public ChoiceBox logChoiceBox;
    @FXML
    private ChoiceBox databaseChoiceBox;
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public void init() {

        //mainBorderPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        //初始化日志显示路径
        ObservableList<String> list = FXCollections.observableArrayList();
        //获取路径
        String path = Paths.get("").toAbsolutePath().toString() + "\\resource";
        File file = new File(path+"\\log");
        File[] filelist = file.listFiles();
        for (File f : filelist) {
            list.add(f.getName());
        }
        logChoiceBox.setItems(list);
        //初始化默认选择
        if(!list.isEmpty()) {
            logChoiceBox.getSelectionModel().select(0);
        }

        ObservableList<String> databaselist = FXCollections.observableArrayList();


        //初始化数据库显示
        try {

            FileReader databaseFile = new FileReader(path + "\\databaseInfomation\\databaseInfomation.dbinfo");
            BufferedReader reader = new BufferedReader(databaseFile);

            //TODO:这里的读取思路超级傻批，但是先暂时这么实现一下。到时候要修正。

            reader.readLine();
            reader.readLine();
            String check = reader.readLine();
            String name = null;
            while(check != null) {
                if(check.contains("Name:")) {
                    name = check.substring(5);
                    databaselist.add(name);
                }

                check = reader.readLine();
            }
            databaseChoiceBox.setItems(databaselist);
            //初始化默认选择
            if(!list.isEmpty()) {
                databaseChoiceBox.getSelectionModel().select(0);
            }

            databaseFile.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void connectTestOnAction() {

        String databaseName = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();

        if(databaseName == null){
            actionLog.appendText("没有选择数据库！\n");
            return;
        }

        actionLog.appendText("连接测试中...\n");

        //获取数据

        DatabaseVO databaseInfo = new DatabaseVO();

        FileReader databaseFile = null;
        try {
            String path = Paths.get("").toAbsolutePath().toString();
            databaseFile = new FileReader(path + "\\resource\\databaseInfomation\\databaseInfomation.dbinfo");

            BufferedReader reader = new BufferedReader(databaseFile);

            //TODO:这里的读取思路超级傻批，但是先暂时这么实现一下。到时候要修正。

            reader.readLine();
            reader.readLine();
            String check = reader.readLine();
            while(check != null) {
                if(check.equals("Name:"+databaseName)) {
                    databaseInfo.setDatabaseName(check.substring(5));
                    databaseInfo.setDatabaseType(reader.readLine().substring(5));
                    databaseInfo.setHost(reader.readLine().substring(8));
                    databaseInfo.setPort(reader.readLine().substring(5));
                    databaseInfo.setSchema(reader.readLine().substring(7));
                    databaseInfo.setUser(reader.readLine().substring(5));
                    databaseInfo.setPassword(reader.readLine().substring(9));
                    databaseInfo.setMemo(reader.readLine().substring(5));

                    break;
                }
                check = reader.readLine();
            }
            databaseFile.close();
            reader.close();

            //数据库连接测试
            //JDBC原生语句

            String DRIVER = null;
            String URL = null;
            String userName = databaseInfo.getUser();
            String password = databaseInfo.getPassword();

            Connection conn = null;
            PreparedStatement preparedStatement = null;

            switch (databaseInfo.getDatabaseType()) {
                case "MySQL":
                    DRIVER = "com.mysql.jdbc.driver";
                    URL = "jdbc:mysql://" + databaseInfo.getHost() + ":" + databaseInfo.getPort() + "/" + databaseInfo.getSchema() + "serverTimezone=Asia/Shanghai";
            }

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,userName,password);
            preparedStatement = conn.prepareStatement("show databases;");
            boolean state = preparedStatement.execute();

            if(state == true){
                checkFlag = 1;
                actionLog.appendText("连接成功！\n");
            }
            else {
                checkFlag = 0;
                actionLog.appendText("连接失败！\n");
            }

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }


    }

    /**
     * @title insertTestOnAction
     * @description 数据库插入测试：尝试在数据库中插入相关SQL语句，但是不正式插入，只判断是否可以插入
     * @author YachenGuo
     */

    @FXML
    private void insertTestOnAction() {

//        //TODO:insert check but not insert really
//        if(checkFlag == 1) {
//            System.out.println("Success!");
//        }


        String databaseName = (String) databaseChoiceBox.getSelectionModel().getSelectedItem();

        if(databaseName == null){
            actionLog.appendText("没有选择数据库！\n");
            return;
        }

        actionLog.appendText("语句插入中...\n");

        //获取数据

        DatabaseVO databaseInfo = new DatabaseVO();

        FileReader databaseFile = null;
        try {
            String path = Paths.get("").toAbsolutePath().toString();
            databaseFile = new FileReader(path + "\\resource\\databaseInfomation\\databaseInfomation.dbinfo");

            BufferedReader reader = new BufferedReader(databaseFile);

            //TODO:这里的读取思路超级傻批，但是先暂时这么实现一下。到时候要修正。

            reader.readLine();
            reader.readLine();
            String check = reader.readLine();
            while(check != null) {
                if(check.equals("Name:"+databaseName)) {
                    databaseInfo.setDatabaseName(check.substring(5));
                    databaseInfo.setDatabaseType(reader.readLine().substring(5));
                    databaseInfo.setHost(reader.readLine().substring(8));
                    databaseInfo.setPort(reader.readLine().substring(5));
                    databaseInfo.setSchema(reader.readLine().substring(7));
                    databaseInfo.setUser(reader.readLine().substring(5));
                    databaseInfo.setPassword(reader.readLine().substring(9));
                    databaseInfo.setMemo(reader.readLine().substring(5));

                    break;
                }
                check = reader.readLine();
            }
            databaseFile.close();
            reader.close();

            //数据库连接测试
            //JDBC原生语句

            String DRIVER = null;
            String URL = null;
            String userName = databaseInfo.getUser();
            String password = databaseInfo.getPassword();

            Connection conn = null;
            PreparedStatement preparedStatement = null;

            switch (databaseInfo.getDatabaseType()) {
                case "MySQL":
                    DRIVER = "com.mysql.jdbc.driver";
                    URL = "jdbc:mysql://" + databaseInfo.getHost() + ":" + databaseInfo.getPort() + "/" + databaseInfo.getSchema() + "serverTimezone=Asia/Shanghai";
            }

            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,userName,password);
            preparedStatement = conn.prepareStatement(SQLInput.getText());
            boolean state = preparedStatement.execute();

            if(state == true){
                checkFlag = 1;
                actionLog.appendText("插入成功！\n");
            }
            else {
                checkFlag = 0;
                actionLog.appendText("插入失败！\n");
            }

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }



    }

    /**
     * @title insertAction
     * @description 数据库插入动作：在数据库中正式插入相关SQL语句，同时在日志中写入相关的内容
     * @author YachenGuo
     * @throws
     */
    @FXML
    private void insertAction() {

        String path = Paths.get("").toAbsolutePath().toString();

        String logPath = path + "\\resource\\log\\" + logChoiceBox.getSelectionModel().getSelectedItem();

        if(SQLInput.getText().equals("")){
            actionLog.appendText("SQL语句为空\n");
        }

        if(SQLInput.getText().equals(checkingSql)){
            actionLog.appendText("SQL已写入日志，无需再次执行\n");
        }

        if(!SQLInput.getText().equals("") && !SQLInput.getText().equals(checkingSql)){
            try {

                checkingSql = SQLInput.getText();

                //true标识以追加形式写入文件
                BufferedWriter writer = new BufferedWriter(new FileWriter(logPath,true));

                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                StringBuilder builder = new StringBuilder();
                builder.append("# 提交人员：" + programmer.getText() +"\n");
                builder.append("# 操作人员：" + checker.getText() + "\n");
                builder.append("# 操作时间：" + date.format(new Date()) + "\n");
                builder.append("\n");

                builder.append(SQLInput.getText());
                builder.append("\n\n\n");

                writer.write(builder.toString());
                writer.close();

                actionLog.appendText("执行成功！\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @title openManageDatabasePageAction
     * @description 打开管理数据库页面
     * @author YachenGuo
     * @throws
     */
    @FXML
    private void openManageDatabasePageAction() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManageDatabase.fxml"));
        try {
            Parent pane = (Parent)loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("数据库连接管理");

            ManageDatabaseController controller = loader.getController();
            controller.init();

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void openModifyDatabasePageAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyDatabase.fxml"));
        try {
            Parent pane = (Parent)loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("连接修改");

            ModifyDatabaseController controller = loader.getController();
            controller.init();

            stage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openManageLogAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManageLog.fxml"));
        try {
            Parent pane = (Parent)loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("日志管理");

            ManageLogController controller = loader.getController();
            controller.init();

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createLogAction() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateLog.fxml"));
        try {
            Parent pane = (Parent)loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("创建日志文件");
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


















}
