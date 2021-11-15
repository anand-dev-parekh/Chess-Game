package ChessGUIScenes;

import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HelperGUI {


    public static void createBackground(GridPane chessBoard){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){

                Rectangle square = new Rectangle(90, 90, 90, 90);
                if ((y + x) % 2 == 0) square.setFill(Color.BEIGE);
                else square.setFill(Color.TAN);
                chessBoard.add(square, y, x);
            }
        }
    }


    public static void mouseMovement(PieceGUI object){
        object.setOnMousePressed(e ->{
            if (object.boardGUI.boardView == 1){
                object.startX = e.getSceneX();
                object.startY = e.getSceneY();

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

    public static void mouseMovement(PieceGUIEditor object){
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
