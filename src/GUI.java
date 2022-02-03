import ChessGUIObjects.battleChess.BattleGUI;
import ChessGUIObjects.editorChess.ChessBoardEditorGUI;
import ChessGUIObjects.normalChess.ChessBoardGUI;

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
        
        //****** Scene 2: Chess Scene ******

        //State
        Label stateLabel = new Label("State: ");
        Label stateChanging = new Label("");
        
        //Left Hbox
        ChessBoardGUI daGrid = new ChessBoardGUI(stateChanging, false);
        
        //Right Hbox
        Button resetGame = new Button("Reset");
        resetGame.setOnAction(event -> daGrid.resetGame());

        Button pastMoves = new Button("Move back");
        pastMoves.setOnAction(e -> daGrid.moveBackOrForward(1));

        Button moveForward = new Button("Move forward");
        moveForward.setOnAction(e -> daGrid.moveBackOrForward(-1));


        VBox buttonsBox = new VBox(50);
        buttonsBox.getChildren().addAll(stateLabel, stateChanging, resetGame, pastMoves, moveForward);
    

        
        buttonsBox.setAlignment(Pos.CENTER);

        //Big boi Hbox
        HBox rootBox = new HBox(daGrid, buttonsBox);
        HBox.setMargin(daGrid, new Insets(50, 20, 50, 30));

        Scene scene2 = new Scene(rootBox);


        //****** Scene 3: editor ******
        ChessBoardEditorGUI editorBoard = new ChessBoardEditorGUI();
        Scene scene3 = new Scene(editorBoard);

        //****** Scene 4: Da Battle Buhtween Ayoh and TeemTeem ******
        Label stateLabel2 = new Label("State: ");
        Label stateChanging2 = new Label("");

        BattleGUI battleGUI = new BattleGUI(stateChanging2);

        Button resetGame2 = new Button("Reset");
        resetGame2.setOnAction(e -> battleGUI.resetGame());

        Button pastMoves2 = new Button("Move back");
        pastMoves2.setOnAction( e -> battleGUI.moveBackOrForward(1));

        Button moveForward2 = new Button("Move forward");
        moveForward2.setOnAction(e -> battleGUI.moveBackOrForward(-1));

        VBox buttonsBox2 = new VBox(50);

 
        buttonsBox2.getChildren().addAll(stateLabel2, stateChanging2, resetGame2, pastMoves2, moveForward2);
        buttonsBox2.setAlignment(Pos.CENTER);

        HBox rootBox2 = new HBox(battleGUI, buttonsBox2);
        
        Scene scene4 = new Scene(rootBox2);
        
        //*******Scene 5: Only galooeh knows *****
        Label stateLabel3 = new Label("State: ");
        Label stateChanging3 = new Label("");

        ChessBoardGUI galooehGUI = new ChessBoardGUI(stateChanging3, true);
        

        //Right Hbox
        Button galooehReset = new Button("Reset");
        galooehReset.setOnAction(event -> galooehGUI.resetGame());

        Button galooehPastMoves = new Button("Move back");
        galooehPastMoves.setOnAction(e -> galooehGUI.moveBackOrForward(1));

        Button galooehMoveForward = new Button("Move forward");
        galooehMoveForward.setOnAction(e -> galooehGUI.moveBackOrForward(-1));


        VBox buttonsBox3 = new VBox(50);
        buttonsBox3.getChildren().addAll(stateLabel3, stateChanging3, galooehReset);
    
        galooehGUI.buttonContainer = buttonsBox3;
        galooehGUI.moveForward = galooehMoveForward;
        galooehGUI.moveBackward = galooehPastMoves;
        
        buttonsBox3.setAlignment(Pos.CENTER);

        //Big boi Hbox
        HBox rootBox3 = new HBox(galooehGUI, buttonsBox3);
        HBox.setMargin(galooehGUI, new Insets(50, 20, 50, 30));

        Scene scene5 = new Scene(rootBox3);


        //*****Starting scene, Scene 1******
        Label intro = new Label("Wanand Chess.");

        Button buttonWanand = new Button("Play Wanand Chess");
        buttonWanand.setOnAction(event -> stage.setScene(scene2));

        Button buttonEditor = new Button("Wanand Chess Editor u know how it is");
        buttonEditor.setOnAction(event -> stage.setScene(scene3));

        Button buttonBattle = new Button("DA BATTLE BETWEEN AYOH AND TEEMTEEM");
        buttonBattle.setOnAction(e -> stage.setScene(scene4));

        Button onlyGalooehKnows = new Button("Only Galooeh knows");
        onlyGalooehKnows.setOnAction(e -> stage.setScene(scene5));


        VBox introduction = new VBox(100);
        HBox layout1 = new HBox(20);   

        layout1.getChildren().addAll(buttonWanand, buttonEditor, buttonBattle, onlyGalooehKnows);
        introduction.getChildren().addAll(intro, layout1);
        Scene scene = new Scene(introduction);



        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }

}
