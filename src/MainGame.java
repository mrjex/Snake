import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("resources/GameScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Backend.Snake: The Backend.Game");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        Canvas grid = (Canvas) scene.lookup("#Grid");
        GraphicsContext gc = grid.getGraphicsContext2D();
        for( int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if((i+j) % 2 == 0){
                    gc.setFill(Color.BLACK);
                }else{
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(i * 50, j * 50, 50,50);
            }
        }


    }

    public static void main(String[] args) {
        launch();
    }
}