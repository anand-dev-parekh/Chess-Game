import ChessGUIScenes.BoardGUI;
import ChessGUIScenes.BoardEditorGUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        
        //Chess Scene
        Label stateLabel = new Label("State: ");
        Label stateChanging = new Label("");

        //Left Hbox

        /*HBox leftSideContainer = new HBox();
        VBox letterAndGridContainer = new VBox();
        
        
        GridPane letters = new GridPane();
        for (int i = 0; i < 8; i++){
            char c = ((char) (i + 65));
            String ayoh = c + "";

            VBox charContainer = new VBox();
            Text character = new Text(ayoh);
            charContainer.getChildren().add(character);
            letters.add(charContainer, i, 0);
        }
        letters.setHgap(90);
        letters.setAlignment(Pos.CENTER);*/




        BoardGUI daGrid = new BoardGUI(stateChanging);
        

        //Right Hbox
        Button resetGame = new Button("Reset");
        resetGame.setOnAction(event -> daGrid.resetBoard());
        Button pastMoves = new Button("Move back");


        VBox buttonsBox = new VBox(50);
        buttonsBox.getChildren().addAll(stateLabel, stateChanging, resetGame, pastMoves);
    

        
        buttonsBox.setAlignment(Pos.CENTER);

        //Big boi Hbox
        HBox rootBox = new HBox(daGrid, buttonsBox);
        HBox.setMargin(daGrid, new Insets(50, 20, 50, 30));

        Scene scene2 = new Scene(rootBox);


        //Scene 3: editor
        BoardEditorGUI editorBoard = new BoardEditorGUI();

        

        Scene scene3 = new Scene(editorBoard);



        //Starting scene
        Label intro = new Label("Wanand Chess.");
        Button buttonWanand = new Button("Play Wanand Chess");
        buttonWanand.setOnAction(event -> stage.setScene(scene2));
        Button buttonEditor = new Button("Wanand Chess Editor u know how it is");
        buttonEditor.setOnAction(event -> stage.setScene(scene3));


        VBox introduction = new VBox(100);
        HBox layout1 = new HBox(20);   
        layout1.getChildren().addAll(buttonWanand, buttonEditor);
        introduction.getChildren().addAll(intro, layout1);
        Scene scene = new Scene(introduction, 540, 540);



        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}
