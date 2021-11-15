package ChessGUIScenes;

import java.io.FileInputStream;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;


public class PieceGUI extends Rectangle {
    public double startX;
    public double startY;

    //private double translationX;
    //private double translationY;

    public BoardGUI boardGUI;

    
    public PieceGUI(FileInputStream pathway, BoardGUI boardGUIinitial){
        this.setCursor(Cursor.OPEN_HAND);
        this.setHeight(90);
        this.setWidth(90);

        Image image = new Image(pathway);
        this.setFill(new ImagePattern(image));


        
        this.boardGUI = boardGUIinitial;

        onClick();
    }   
    
    

    public void onClick(){
        HelperGUI.mouseMovement(this);   
             
        this.setOnMouseReleased(e ->{
            if (this.boardGUI.boardView == 1){

                this.setCursor(Cursor.OPEN_HAND);

                int newX = (int) ((e.getSceneX() - 30) / 90.0);
                int newY = (int) ((e.getSceneY() - 50) / 90.0);
                
                int x = (int) ((startX - 30)/ 90.0);
                int y = (int) ((startY - 50)/ 90.0);

                if (newX == x && newY == y) {
                    this.setTranslateX(0);
                    this.setTranslateY(0);
                    boardGUI.stateOfDaMove.setText("AYO hurry up. this is OUTRAGEOUS");
                }
                else if (!boardGUI.boardObject.turn.equals(boardGUI.boardObject.matrix[y][x].color) || newY > 7 || newY < 0 || newX > 7 || newX < 0) {
                    this.setTranslateX(0);
                    this.setTranslateY(0);
                    boardGUI.stateOfDaMove.setText("That aint right, choose right piece.");
                }
                else if (boardGUI.boardObject.matrix[y][x].validMove(boardGUI.boardObject, newY, newX) && !boardGUI.boardObject.matrix[y][x].isCheckAfterMove(boardGUI.boardObject, newY, newX)){
                    
                    boardGUI.boardObject.updateBoardObjectMatrix(y, x, newY, newX);
                    boardGUI.updateBoardGUI(y, x, newY, newX, this);

                    boardGUI.boardObject.updateAttributesMoveWork(newY, newX);

                    if (boardGUI.boardObject.isGameOver()) {
                        boardGUI.stateOfDaMove.setText("U truly da best. Simply Put");
                        if (boardGUI.isGalooeh){
                            boardGUI.showRegPieces();
                            boardGUI.showButtons();
                        }
                    }
                    else boardGUI.stateOfDaMove.setText("Gotta give it to you, valid Move");
                }

                else {
                    this.setTranslateX(0);
                    this.setTranslateY(0);

                    boardGUI.boardObject.matrix[y][x].castle = false;
                    boardGUI.boardObject.matrix[y][x].enPessant = false;
                    boardGUI.boardObject.matrix[y][x].promotion = false;
                    boardGUI.stateOfDaMove.setText("Cmon now, Invalid");
                }
            }
        
        });

    } 

}
