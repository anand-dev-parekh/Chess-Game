package ChessGUIScenes;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

public class BattlePieceGUI extends Rectangle{

    public double startX;
    public double startY;

    public BattleGUI battleGUI;

    private Label stateOfDaLabel;

    public BattlePieceGUI(String piece, Label stateOfDaMove, BattleGUI battleGUI){
        stateOfDaLabel = stateOfDaMove;
    
        this.battleGUI = battleGUI;
        this.setCursor(Cursor.OPEN_HAND);
        this.setHeight(90);
        this.setWidth(90);

        try{ 

            FileInputStream pathway = new FileInputStream("/Users/akhilb/Documents/GitHub/Chess-Game/src/pictures/" + piece + ".png");                   
            Image image = new Image(pathway);
 
            this.setFill(new ImagePattern(image));
         }
         catch (IOException elephant){
            System.out.println("AYOHOHOHOH");
         }

         onClick();
    }

    private void onClick(){
        HelperGUI.mouseMovement(this);


        this.setOnMouseReleased(e ->{
            this.setCursor(Cursor.OPEN_HAND);
            int newX = (int) ((e.getSceneX()) / 90.0);
            int newY = (int) ((e.getSceneY()) / 90.0);
             
            int x = (int) ((startX)/ 90.0);
            int y = (int) ((startY)/ 90.0);


            if (newY > 7 || newY < 0 || newX > 7 || newX < 0 || !battleGUI.boardObject.turn.equals(battleGUI.boardObject.matrix[y][x].color)){
                this.setTranslateX(0);
                this.setTranslateY(0);
                stateOfDaLabel.setText("That aint right, choose right piece");
            }
            else if (battleGUI.boardObject.matrix[y][x].validMove(battleGUI.boardObject, newY, newX)){
                battleGUI.boardObject.updateBoardObjectMatrix(y, x, newY, newX);
                battleGUI.updateBattleGUI(this, newY, newX);

                battleGUI.boardObject.updateAttributesMoveWork(newY, newX);

            }
            else {
                this.setTranslateX(0);
                this.setTranslateY(0);
                stateOfDaLabel.setText("Cmon now, invalid");
            }
            

        });
    }

      
}