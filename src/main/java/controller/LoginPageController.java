package controller;

import dao.LoginDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

/**
 * @author YachenGuo
 * @ClassName LoginPageController
 * @Description
 * @createTime 2021 09 06 19:44
 */
public class LoginPageController {


    @FXML
    private TextField LoginUser;
    @FXML
    private TextField LoginPassword;
    @FXML
    private Button LoginButton;
    @FXML
    private Button ForgetPasswordButton;

    @FXML
    public void Login(){

        String user = LoginUser.getText();
        String password = LoginPassword.getText();

        if(user.isEmpty() || password.isEmpty()){

            Alert alertWindow = new Alert(Alert.AlertType.ERROR,"用户名或密码不可为空！");
            alertWindow.setTitle("登录错误");
            Optional confirm = alertWindow.showAndWait();
        }

        //TODO:登录验证路径










    }


}
