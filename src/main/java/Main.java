import controller.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

public class Main extends Application {

    @FXML
    private ChoiceBox logChoiceBox;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 900, 600);

        JMetro jmetro = new JMetro();
        jmetro.setStyle(Style.LIGHT);
        jmetro.setScene(scene);

        primaryStage.setTitle("LoLoManager");

        primaryStage.setScene(scene);

//        MainPageController controller = loader.getController();
//        controller.init();


        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
