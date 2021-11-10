package ChessGUIScenes;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.Math;


public class PieceGUI extends ImageView{
    private double startX;
    private double startY;

    private double stackX = 0;
    private double stackY = 0;

    private BoardGUI boardGUI;
    private Label stateOfDaLabel;

    
    public PieceGUI(Image image, BoardGUI boardGUIinitial, Label stateOfMove){
        this.setImage(image);
        this.setFitHeight(80);
        this.setFitWidth(80);

        
        boardGUI = boardGUIinitial;
        stateOfDaLabel = stateOfMove;

        onClick();
    }   
    
    

    public void onClick(){
        this.setOnMousePressed(e ->{
            startX = e.getSceneX();
            startY = e.getSceneY();
        });

        this.setOnMouseDragged(e ->{
            this.setTranslateX((e.getSceneX() - startX) + stackX);
            this.setTranslateY((e.getSceneY() - startY) + stackY);
        });

        this.setOnMouseReleased(e ->{
            stackX += e.getSceneX() - startX;
            stackY += e.getSceneY() - startY;
        
            int newX = (int)Math.round(e.getSceneX() / 90.0) - 1;
            int newY = (int)Math.round(e.getSceneY() / 90.0) - 1;
             
            int x = (int)Math.round(startX / 90.0) - 1;
            int y = (int)Math.round(startY / 90.0) - 1;

            System.out.println(y + " " + x);
            System.out.println(newY + " " + newX);

            if (boardGUI.boardObject.matrix[y][x] == null || !boardGUI.boardObject.turn.equals(boardGUI.boardObject.matrix[y][x].color)) {
                this.setTranslateX(0);
                this.setTranslateY(0);
                stackX -= (e.getSceneX() - startX);
                stackY -= (e.getSceneY() - startY);
                stateOfDaLabel.setText("That aint right, choose right piece");
            }

            else if (boardGUI.boardObject.matrix[y][x].validMove(boardGUI.boardObject, newY, newX) && !boardGUI.boardObject.matrix[y][x].isCheckAfterMove(boardGUI.boardObject, newY, newX)){
                boardGUI.boardObject.updateBoardObjectMatrix(y, x, newY, newX);
                boardGUI.updateBoard();
                if (boardGUI.boardObject.isGameOver()) stateOfDaLabel.setText("U truly da best. Simply Put");
                else stateOfDaLabel.setText("Gotta give it to you, valid Move");
            }

            else {
                this.setTranslateX(0);
                this.setTranslateY(0);
                stackX -= (e.getSceneX() - startX);
                stackY -= (e.getSceneY() - startY);

                boardGUI.boardObject.matrix[y][x].castle = false;
                boardGUI.boardObject.matrix[y][x].enPessant = false;
                boardGUI.boardObject.matrix[y][x].promotion = false;
                stateOfDaLabel.setText("Cmon now, Invalid");
            }

        
        });

    } 

}
