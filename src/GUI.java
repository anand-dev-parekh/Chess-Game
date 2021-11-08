/*import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;


public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        GridPane daGrid = new GridPane();
        
        
        

        //Chess Scene
        for (int i = 0; i < 9; i++){
            Label columnIndex = new Label("" + i);
            daGrid.add(columnIndex, 0, i);
            GridPane.setValignment(columnIndex, VPos.CENTER);
        }
        for (int i = 1; i < 9;i++){
            Label rowIndex = new Label("" + i);
            daGrid.add(rowIndex, i, 0);
            GridPane.setHalignment(rowIndex, HPos.CENTER);
        }

        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++){
                Rectangle square = new Rectangle(90, 90, 90, 90);
                if ((i + j) % 2 == 0)square.setFill(Color.GREEN);
                else square.setFill(Color.WHITE);
                daGrid.add(square, j, i);
            }
        }

        Scene scene2 = new Scene(daGrid, 740, 740);
        //Starting scene
        Label intro = new Label("Wanand Chess.");
        Button buttonWanand = new Button("Play");
        buttonWanand.setOnAction(event -> stage.setScene(scene2));

        VBox layout1 = new VBox(20);   
        layout1.getChildren().addAll(intro, buttonWanand);
        Scene scene = new Scene(layout1, 540, 540);



        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}*/
