package ChessGUIObjects;

import ChessGUIObjects.battleChess.BattlePieceGUI;
import ChessGUIObjects.editorChess.ChessPieceEditorGUI;
import ChessGUIObjects.normalChess.ChessBoardGUI;
import ChessGUIObjects.normalChess.ChessPieceGUI;

import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HelperGUI {

    public static void createBackground(ChessBoardGUI chessBoard){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){

                Rectangle square = new Rectangle(90, 90, 90, 90);
                if ((y + x) % 2 == 0) square.setFill(Color.AZURE);
                else square.setFill(Color.POWDERBLUE);
                chessBoard.add(square, y, x);
                chessBoard.backgrounds[y][x] = square;
            }
        }
    }


    public static void createBackground(GridPane chessBoard){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){

                Rectangle square = new Rectangle(90, 90, 90, 90);
                if ((y + x) % 2 == 0) square.setFill(Color.AZURE);
                else square.setFill(Color.POWDERBLUE);
                chessBoard.add(square, y, x);
            }
        }
    }


    public static void mouseMovement(ChessPieceGUI object){
        object.setOnMousePressed(e ->{
            if (object.boardGUI.boardView == 1){
                object.startX = e.getSceneX();
                object.startY = e.getSceneY();


                int curX = (int)(e.getSceneX() - 30) / 90;
                int curY = (int)(e.getSceneY() - 50) / 90;

                object.boardGUI.backgrounds[curX][curY].setFill(Color.LIGHTYELLOW);

                object.setCursor(Cursor.CLOSED_HAND);
                object.toFront();
            }
        });

        object.setOnMouseDragged(e ->{
            if (object.boardGUI.boardView == 1){
                object.setTranslateX((e.getSceneX() - object.startX));
                object.setTranslateY((e.getSceneY() - object.startY));
                object.setCursor(Cursor.CLOSED_HAND);
            }
        });

    }

    public static void mouseMovement(ChessPieceEditorGUI object){
        object.setOnMousePressed(e ->{
            object.startX = e.getSceneX();
            object.startY = e.getSceneY();

            object.setCursor(Cursor.CLOSED_HAND);
            object.toFront();
        });

        object.setOnMouseDragged(e ->{
            
            object.setTranslateX((e.getSceneX() - object.startX));
            object.setTranslateY((e.getSceneY() - object.startY));
            object.setCursor(Cursor.CLOSED_HAND);
        });
    }


    public static void mouseMovement(BattlePieceGUI object){
        object.setOnMousePressed(e ->{
            object.startX = e.getSceneX();
            object.startY = e.getSceneY();

            object.setCursor(Cursor.CLOSED_HAND);
            object.toFront();
        });

        object.setOnMouseDragged(e ->{
            
            object.setTranslateX((e.getSceneX() - object.startX));
            object.setTranslateY((e.getSceneY() - object.startY));
            object.setCursor(Cursor.CLOSED_HAND);
        });
    }

}
