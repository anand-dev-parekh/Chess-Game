package ChessGUIObjects.battleChess;

import java.io.FileInputStream;
import java.io.IOException;

import ChessGUIObjects.BasePieceGUI;
import ChessGUIObjects.FilePaths;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


public class BattlePieceGUI extends BasePieceGUI{

    int paddingX = 0;
    int paddingY = 0;

    public BattlePieceGUI(String piece, BattleGUI battleGUI){
    
        this.boardGUI = battleGUI;
        this.setCursor(Cursor.OPEN_HAND);
        this.setHeight(90);
        this.setWidth(90);

        try{ 

            FileInputStream pathway = new FileInputStream(FilePaths.daPath + piece + ".png");                   
            Image image = new Image(pathway);
 
            this.setFill(new ImagePattern(image));
         }
         catch (IOException elephant){
            System.out.println("AYOHOHOHOH");
         }

         onClick();
    }

    private void onClick(){
        mouseMovementOn();


        this.setOnMouseReleased(e ->{
            this.setCursor(Cursor.OPEN_HAND);
            int newX = (int) ((e.getSceneX() - this.paddingX) / 90.0);
            int newY = (int) ((e.getSceneY() - this.paddingY) / 90.0);
             
            int x = (int) ((startX - this.paddingX)/ 90.0);
            int y = (int) ((startY - this.paddingY)/ 90.0);


            if (newY > 7 || newY < 0 || newX > 7 || newX < 0 || !boardGUI.boardObject.turn.equals(boardGUI.boardObject.matrix[y][x].color)){
                this.setTranslateX(0);
                this.setTranslateY(0);
                this.changeBackgroundBack(y, x);
                boardGUI.stateOfDaMove.setText("That aint right, choose right piece");
            }
            else if (boardGUI.boardObject.matrix[y][x].validMove(boardGUI.boardObject, newY, newX)){

                updateBackgrounds(y, x, newY, newX);

                boardGUI.boardObject.updateBoardObjectMatrix(y, x, newY, newX);
                boardGUI.updateBoardGUI(y, x, newY, newX, this);

                boardGUI.boardObject.updateAttributesMoveWork(newY, newX);

                boardGUI.stateOfDaMove.setText("Gotta Give it to you, Valid Move");

                if (boardGUI.boardObject.isBattleOver() == 1){
                    boardGUI.boardObject.gameOver = true;
                    boardGUI.stateOfDaMove.setText("LEZ GOO teemteem ez win");
                }
                else if (boardGUI.boardObject.isBattleOver() == 2){
                    boardGUI.boardObject.gameOver = false;
                    boardGUI.stateOfDaMove.setText("AYOH FO DA WIN ");
                }
            }
            else {
                this.setTranslateX(0);
                this.setTranslateY(0);

                this.changeBackgroundBack(y, x);
                boardGUI.stateOfDaMove.setText("Cmon now, invalid");
            }
            

        });
    }

      
}