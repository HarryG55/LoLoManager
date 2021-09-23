package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateLogController {

    @FXML
    private TextField newLogNameTextField;

    @FXML
    private TextField newLogNameDescriptionTextField;

    @FXML
    private Text createLogActionStateText;

    @FXML
    private Button newLogButton;


    @FXML
    private void createNewLogButtonAction() {

        String path = Paths.get("").toAbsolutePath().toString();

        if(newLogNameTextField.getText().equals("")) {
            createLogActionStateText.setText("日志名为空！无法创建日志！\n");
        }

        if(newLogNameDescriptionTextField.getText().equals("")) {
            createLogActionStateText.setText("日志描述为空！无法创建日志 \n");
        }

        if(newLogNameTextField.getText().equals("") && newLogNameDescriptionTextField.getText().equals("")) {
            createLogActionStateText.setText("日志名与日志描述为空！无法创建日志 \n");
        }

        if(! newLogNameTextField.getText().equals("") && ! newLogNameDescriptionTextField.getText().equals("")) {
            File file = new File(path + "\\resource\\log\\" + newLogNameTextField.getText() + ".sql");

            if (file.exists()) {
                createLogActionStateText.setText("日志文件已存在！无法创建日志 \n");
            }
            else {
                try {
                    file.createNewFile();

                    FileWriter filewriter = new FileWriter(path + "\\resource\\log\\" + newLogNameTextField.getText() + ".sql");
                    BufferedWriter writer = new BufferedWriter(filewriter);

                    StringBuilder builder = new StringBuilder();
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    builder.append("# 日志名称：" + newLogNameTextField.getText() + "\n");
                    builder.append("# 创建时间：" + date.format(new Date()) + "\n");
                    builder.append("# 日志描述：" + newLogNameDescriptionTextField.getText() + "\n");

                    builder.append("\n\n\n");

                    writer.write(builder.toString());

                    writer.close();
                    filewriter.close();


                    createLogActionStateText.setText("日志文件创建成功! \n");


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
    }

}
