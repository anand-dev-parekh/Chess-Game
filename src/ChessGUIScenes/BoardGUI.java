package ChessGUIScenes;

import game.Board;
import game.Base;
import game.King;
import game.Queen;
import game.Rook;
import game.Bishop;
import game.Knight;
import game.Pawn;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.IOException;


import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BoardGUI extends GridPane{
    private Board boardObject; 


    public BoardGUI(){
        this.boardObject =  createGame();
        updateBoard();
    }


    public void updateBoard(){
        this.getChildren().clear();

        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                
                Rectangle square = new Rectangle(90, 90, 90, 90);
                if ((y + x) % 2 == 0) square.setFill(Color.TAN);
                else square.setFill(Color.BEIGE);
                this.add(square, x, y);

                if (boardObject.matrix[y][x] != null){
                    try{
                        FileInputStream pathway = new FileInputStream("/Users/anandparekh/Documents/GitHub/Chess-Game-Clone/src/pictures/" + this.boardObject.matrix[y][x].color + this.boardObject.matrix[y][x].piece + ".png");
                                    
                        Image image = new Image(pathway);
                        ImageView imageNode = new ImageView(image);

                        this.add(imageNode, x, y);
                        GridPane.setHalignment(imageNode, HPos.CENTER);
                    }
                    catch (IOException e) {
                        System.out.println("FILE ERROR PICTURES");
                    }
                }
            }
        }
        
        
        return;
    }

    //For now we will just create a new instance. DOwn the road make pieces attributes, and just reinput pieces to save memory.
    public void resetBoard(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8;j++){
                this.boardObject.matrix[i][j] = null;
            }
        }
        
        updateBoard();
    }


    public Board createGame(){
        Base blackKing = new King("black", 0, 4, "king");
        Base blackQueen = new Queen("black", 0, 3, "queen");
        Base blackBishop1 = new Bishop("black", 0, 2, "bishop");
        Base blackBishop2 = new Bishop("black", 0, 5, "bishop");
        Base blackKnight2 = new Knight("black", 0, 6, "knight");
        Base blackKnight1 = new Knight("black", 0, 1, "knight");
        Base blackRook1 = new Rook("black", 0 , 0, "rook");
        Base blackRook2 = new Rook("black", 0, 7, "rook");
        Base blackPawn0 = new Pawn("black", 1, 0, "pawn");
        Base blackPawn1 = new Pawn("black", 1, 1, "pawn");
        Base blackPawn2 = new Pawn("black", 1, 2, "pawn");
        Base blackPawn3 = new Pawn("black", 1, 3, "pawn");
        Base blackPawn4 = new Pawn("black", 1, 4, "pawn");
        Base blackPawn5 = new Pawn("black", 1, 5, "pawn");
        Base blackPawn6 = new Pawn("black", 1, 6, "pawn");
        Base blackPawn7 = new Pawn("black", 1, 7, "pawn");
        
        Base whiteKing = new King("white", 7, 4, "king");
        Base whiteQueen = new Queen("white", 7, 3, "queen");
        Base whiteBishop1 = new Bishop("white", 7, 2, "bishop");
        Base whiteBishop2 = new Bishop("white", 7, 5, "bishop");
        Base whiteKnight2 = new Knight("white", 7, 6, "knight");
        Base whiteKnight1 = new Knight("white", 7, 1, "knight");
        Base whiteRook1 = new Rook("white", 7 , 0, "rook");
        Base whiteRook2 = new Rook("white", 7, 7, "rook");
        Base whitePawn0 = new Pawn("white", 6, 0, "pawn");
        Base whitePawn1 = new Pawn("white", 6, 1, "pawn");
        Base whitePawn2 = new Pawn("white", 6, 2, "pawn");
        Base whitePawn3 = new Pawn("white", 6, 3, "pawn");
        Base whitePawn4 = new Pawn("white", 6, 4, "pawn");
        Base whitePawn5 = new Pawn("white", 6, 5, "pawn");
        Base whitePawn6 = new Pawn("white", 6, 6, "pawn");
        Base whitePawn7 = new Pawn("white", 6, 7, "pawn");

        Base[][] matrix = {
                          {blackRook1, blackKnight1, blackBishop1, blackQueen, blackKing, blackBishop2, blackKnight2, blackRook2}, 
                          {blackPawn0, blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {whitePawn0, whitePawn1, whitePawn2, whitePawn3, whitePawn4, whitePawn5, whitePawn6, whitePawn7},
                          {whiteRook1, whiteKnight1, whiteBishop1, whiteQueen, whiteKing, whiteBishop2, whiteKnight2, whiteRook2},
                          }; 

        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();

        //create board object
        Board boardObject = new Board(matrix, prevBoards);

        //add starting matrix to prevBoards
        Base[][] tempBoardMatrix = new Base[8][8];
        for (int i = 0; i < boardObject.matrix.length; i++){
            for (int j = 0; j < boardObject.matrix[i].length; j++){
                tempBoardMatrix[i][j] = boardObject.matrix[i][j]; 
            }
        }
        boardObject.prevBoards.add(tempBoardMatrix); 
        return boardObject;
    }

}
