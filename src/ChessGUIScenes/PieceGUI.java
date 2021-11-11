package ChessGUIScenes;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.Math;


public class PieceGUI extends ImageView{
    private double startX;
    private double startY;

    private BoardGUI boardGUI;
    private Label stateOfDaLabel;

    
    public PieceGUI(Image image, BoardGUI boardGUIinitial, Label stateOfMove){
        this.setImage(image);
        this.setFitHeight(90);
        this.setFitWidth(90);

        
        boardGUI = boardGUIinitial;
        stateOfDaLabel = stateOfMove;

        onClick();
    }   
    
    

    public void onClick(){
        this.setOnMousePressed(e ->{
            startX = e.getSceneX();
            startY = e.getSceneY();
            this.setCursor(Cursor.CLOSED_HAND);
        });

        this.setOnMouseDragged(e ->{
            
            this.setTranslateX((e.getSceneX() - startX));
            this.setTranslateY((e.getSceneY() - startY));
            this.setCursor(Cursor.CLOSED_HAND);
        });

        this.setOnMouseReleased(e ->{
            
            this.setCursor(Cursor.DEFAULT);

            int newX = (int)Math.round(e.getSceneX() / 90.0) - 1;
            int newY = (int)Math.round(e.getSceneY() / 90.0) - 1;
             
            int x = (int)Math.round(startX / 90.0) - 1;
            int y = (int)Math.round(startY / 90.0) - 1;

            if (boardGUI.boardObject.matrix[y][x] == null || !boardGUI.boardObject.turn.equals(boardGUI.boardObject.matrix[y][x].color)) {
                this.setTranslateX(0);
                this.setTranslateY(0);
                stateOfDaLabel.setText("That aint right, choose right piece");
            }

            else if (boardGUI.boardObject.matrix[y][x].validMove(boardGUI.boardObject, newY, newX) && !boardGUI.boardObject.matrix[y][x].isCheckAfterMove(boardGUI.boardObject, newY, newX)){
                boardGUI.boardObject.updateBoardObjectMatrix(y, x, newY, newX);
                boardGUI.updateBoardGUI(y, x, newY, newX);
                boardGUI.boardObject.updateAttributesMoveWork(newY, newX);
                if (boardGUI.boardObject.isGameOver()) stateOfDaLabel.setText("U truly da best. Simply Put");
                else stateOfDaLabel.setText("Gotta give it to you, valid Move");
            }

            else {
                this.setTranslateX(0);
                this.setTranslateY(0);

                boardGUI.boardObject.matrix[y][x].castle = false;
                boardGUI.boardObject.matrix[y][x].enPessant = false;
                boardGUI.boardObject.matrix[y][x].promotion = false;
                stateOfDaLabel.setText("Cmon now, Invalid");
            }

        
        });

    } 

}
