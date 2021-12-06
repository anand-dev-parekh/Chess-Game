package ChessGUIObjects.editorChess;

import ChessGUIObjects.FilePaths;
import ChessGUIObjects.HelperGUI;

import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;


public class ChessPieceEditorGUI extends Rectangle{
    public double startX;
    public double startY;


    private ChessBoardEditorGUI daBoard;
    private String color;
    private String piece;


    public ChessPieceEditorGUI(ChessBoardEditorGUI daBoard, String piece, String color){
        this.setCursor(Cursor.OPEN_HAND);
        this.setHeight(90);
        this.setWidth(90);


       try{ 
           FileInputStream pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");                   
            Image image = new Image(pathway);

            this.setFill(new ImagePattern(image));
        }
        catch (IOException elephant){
            System.out.println("AYOHOHOHOH");
        }


        this.color = color;
        this.piece = piece;



        this.daBoard = daBoard;


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


            if (newX >=0 && newX <=7 && newY >= 0 && newY <= 7){

                //Delete da node on da new spot of da board in case of da capture in da BoardEditor
                if (newX != x || newY != y) this.daBoard.deleteDaPiece(newY, newX);


                //sets the new node
                GridPane.setColumnIndex(this, newX);
                GridPane.setRowIndex(this, newY);
                this.setTranslateX(0);
                this.setTranslateY(0);
                GridPane.setHalignment(this, HPos.CENTER);
                GridPane.setValignment(this, VPos.CENTER);


                //replace node to old place
                if (x > 7){
                    ChessPieceEditorGUI replacement = new ChessPieceEditorGUI(this.daBoard, this.piece, this.color);


                    this.daBoard.add(replacement, x, y);
                    GridPane.setHalignment(replacement, HPos.CENTER);
                    GridPane.setValignment(replacement, VPos.CENTER);
                }
            }
            else{
                if (x > 7){
                    this.setTranslateX(0);
                    this.setTranslateY(0);
                }
                else {
                    this.daBoard.getChildren().remove(this);
                }
            }
        });



    }
}
