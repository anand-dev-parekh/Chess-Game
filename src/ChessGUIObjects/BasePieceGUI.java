package ChessGUIObjects;

import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasePieceGUI extends Rectangle{
    protected double startX;
    protected double startY;

    protected int paddingX;
    protected int paddingY;

    public BaseBoardGUI boardGUI;

    protected void updateBackgrounds(int y, int x, int newY, int newX){
        //resets
        if (boardGUI.prevMoveNewBackground != null){

            if ((boardGUI.prevMoveNewBackground[0] + boardGUI.prevMoveNewBackground[1]) % 2 == 0){
                boardGUI.backgrounds[boardGUI.prevMoveNewBackground[0]][boardGUI.prevMoveNewBackground[1]].setFill(Color.AZURE);
            }
            else {
                boardGUI.backgrounds[boardGUI.prevMoveNewBackground[0]][boardGUI.prevMoveNewBackground[1]].setFill(Color.POWDERBLUE);
            }

            if ((boardGUI.prevMoveOldBackground[0] + boardGUI.prevMoveOldBackground[1]) % 2 == 0){
                boardGUI.backgrounds[boardGUI.prevMoveOldBackground[0]][boardGUI.prevMoveOldBackground[1]].setFill(Color.AZURE);
            }
            else {
                boardGUI.backgrounds[boardGUI.prevMoveOldBackground[0]][boardGUI.prevMoveOldBackground[1]].setFill(Color.POWDERBLUE);
            }
        }

        //Changes if valid move
        boardGUI.prevMoveOldBackground[0] = x;
        boardGUI.prevMoveOldBackground[1] = y;

        boardGUI.prevMoveNewBackground[0] = newX;
        boardGUI.prevMoveNewBackground[1] = newY;

        boardGUI.backgrounds[x][y].setFill(Color.LIGHTYELLOW);
        boardGUI.backgrounds[newX][newY].setFill(Color.LIGHTYELLOW);

    }


    public void mouseMovementOn(){
        this.setOnMousePressed(e ->{
            if (boardGUI.boardView == 1){
                this.startX = e.getSceneX();
                this.startY = e.getSceneY();

                int curX = (int)(e.getSceneX() - paddingX) / 90;
                int curY = (int)(e.getSceneY() - paddingY) / 90;

                this.boardGUI.backgrounds[curX][curY].setFill(Color.LIGHTYELLOW);

                this.setCursor(Cursor.CLOSED_HAND);
                this.toFront();
            }
        });

        this.setOnMouseDragged(e ->{
            if (boardGUI.boardView == 1){
                this.setTranslateX((e.getSceneX() - this.startX));
                this.setTranslateY((e.getSceneY() - this.startY));
                this.setCursor(Cursor.CLOSED_HAND);
            }
        });

    }

    protected void changeBackgroundBack(int y, int x){
        if ((x + y) % 2 == 0){
            boardGUI.backgrounds[x][y].setFill(Color.AZURE);
        }
        else{
            boardGUI.backgrounds[x][y].setFill(Color.POWDERBLUE);
        }
    }


    
}
