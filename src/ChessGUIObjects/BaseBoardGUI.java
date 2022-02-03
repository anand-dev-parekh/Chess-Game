package ChessGUIObjects;

import game.*;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import ChessGUIObjects.battleChess.BattlePieceGUI;
import ChessGUIObjects.normalChess.ChessPieceGUI;

import java.io.FileInputStream;
import java.io.IOException;


public abstract class BaseBoardGUI extends GridPane{

    public Rectangle[][] backgrounds = new Rectangle[8][8];

    public Board boardObject;


    public int[] prevMoveNewBackground = new int[2];
    public int[] prevMoveOldBackground = new int[2];

    public Label stateOfDaMove;
    public Button moveBackward;
    public Button moveForward;
    public VBox buttonContainer;

    public int boardView = 1;

    public boolean isGalooeh;

    //Creates chess square background
    public void createBackground(){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){

                Rectangle square = new Rectangle(90, 90, 90, 90);
                if ((y + x) % 2 == 0) square.setFill(Color.AZURE);
                else square.setFill(Color.POWDERBLUE);
                this.add(square, y, x);
                this.backgrounds[y][x] = square;
            }
        }
    }

    
    //For now we will just create a new instance. DOwn the road make pieces attributes, and just reinput pieces to save memory.
    public void resetGame(){
        if (isGalooeh){
            this.buttonContainer.getChildren().remove(this.moveBackward);
            this.buttonContainer.getChildren().remove(this.moveForward);
        }
        this.boardView = 1;

        this.boardObject.resetBoardObject();
        resetBoardGUI();
    }

    //Resets boardGUI
    private void resetBoardGUI(){
        this.destroyAllPieceNodes();
         
        //Deletes old highlights
        if ((prevMoveNewBackground[0] + prevMoveNewBackground[1]) % 2 == 0){
            backgrounds[prevMoveNewBackground[0]][prevMoveNewBackground[1]].setFill(Color.AZURE);
        }
        else {
            backgrounds[prevMoveNewBackground[0]][prevMoveNewBackground[1]].setFill(Color.POWDERBLUE);
        }

        if ((prevMoveOldBackground[0] + prevMoveOldBackground[1]) % 2 == 0){
            backgrounds[prevMoveOldBackground[0]][prevMoveOldBackground[1]].setFill(Color.AZURE);
        }
        else {
            backgrounds[prevMoveOldBackground[0]][prevMoveOldBackground[1]].setFill(Color.POWDERBLUE);
        }

        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (boardObject.matrix[y][x] != null){
                this.add(boardObject.matrix[y][x].basePieceGUI, x, y);
                }
            }
        }
    }


    //Updates boardGUI
    public abstract void updateBoardGUI(int y, int x, int newY, int newX, BasePieceGUI pieceGUI);


    //Moves pieceGUI object
    protected void movePiece(BasePieceGUI basePieceGUI, int newY, int newX){
        GridPane.setColumnIndex(basePieceGUI, newX);
        GridPane.setRowIndex(basePieceGUI, newY);
        basePieceGUI.setTranslateX(0);
        basePieceGUI.setTranslateY(0);
        GridPane.setHalignment(basePieceGUI, HPos.CENTER);
        GridPane.setValignment(basePieceGUI, VPos.CENTER);
    }

    //Destroys pieceGUI
    public void destroyPiece(BasePieceGUI pieceGUI){
        this.getChildren().remove(pieceGUI);
    }
    
    //Move back and forth chess.
    public void moveBackOrForward(int decrement){
        if (this.boardView + decrement > 0 && this.boardView + decrement <= this.boardObject.prevBoards.size()){
            this.boardView += decrement;
            this.updateBackwardsMovement(this.boardObject.prevBoards.get(this.boardObject.prevBoards.size() - this.boardView));
        } 
    }

    //Helper function to moving back and forth
    private void updateBackwardsMovement(Base[][] displayBoard){
        this.destroyAllPieceNodes();

        if (boardView == 1){
            backgrounds[prevMoveNewBackground[0]][prevMoveNewBackground[1]].setFill(Color.LIGHTYELLOW);
            backgrounds[prevMoveOldBackground[0]][prevMoveOldBackground[1]].setFill(Color.LIGHTYELLOW);

        }
        else {
            if ((prevMoveNewBackground[0] + prevMoveNewBackground[1]) % 2 == 0){
                backgrounds[prevMoveNewBackground[0]][prevMoveNewBackground[1]].setFill(Color.AZURE);
            }
            else {
                backgrounds[prevMoveNewBackground[0]][prevMoveNewBackground[1]].setFill(Color.POWDERBLUE);
            }
            if ((prevMoveOldBackground[0] + prevMoveOldBackground[1]) % 2 == 0){
                backgrounds[prevMoveOldBackground[0]][prevMoveOldBackground[1]].setFill(Color.AZURE);
            }
            else {
                backgrounds[prevMoveOldBackground[0]][prevMoveOldBackground[1]].setFill(Color.POWDERBLUE);
            }
        }


        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                
                if (displayBoard[y][x] == null) continue;

                //if galooeh game, change images back to regular images
                if (isGalooeh){
                    String color = displayBoard[y][x].color;
                    String piece = displayBoard[y][x].piece;
                    
                    try{
                        FileInputStream pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
                        
                        Image image = new Image(pathway);
                        displayBoard[y][x].basePieceGUI.setFill(new ImagePattern(image));    
                    }
                    catch (IOException e) {
                        System.out.println("FILE ERROR PICTURES");
                    } 
                }

                this.add(displayBoard[y][x].basePieceGUI, x, y);
            }
        }
    }

    //Destroys all piece GUIS
    protected void destroyAllPieceNodes(){
        ObservableList<Node> nodes = this.getChildren();
        ArrayList<Node> nodesToRemove = new ArrayList<Node>();

        for (Node node : nodes){
            if (node instanceof ChessPieceGUI || node instanceof BattlePieceGUI){
                nodesToRemove.add(node);
            }
        }

        for (Node node : nodesToRemove){
            this.getChildren().remove(node);
        }
    }

    //Galooeh game stuff - show pieces and back and forth buttons after game over
    public void showRegPieces(){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (boardObject.matrix[y][x] == null) continue;
                String color = this.boardObject.matrix[y][x].color;
                String piece = this.boardObject.matrix[y][x].piece;
                
                try{
                    FileInputStream pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
                    
                    Image image = new Image(pathway);
                    this.boardObject.matrix[y][x].basePieceGUI.setFill(new ImagePattern(image));    
                }
                catch (IOException e) {
                    System.out.println("FILE ERROR PICTURES");
                } 
            }    
        }
    }

    //Shows move forward and backwards buttons
    public void showButtons(){
        buttonContainer.getChildren().addAll(moveBackward, moveForward);
    }

    //Creates board Object
    protected Board intializeBoardObject(String[] rowZero, String[] rowOne, String[] rowSix, String[] rowSeven){
        Base[][] matrix = createMatrix(rowZero, rowOne, rowSix, rowSeven);

        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();

        Board boardObject = new Board(matrix, prevBoards);

        //Adds first position
        Base[][] tempBoardMatrix = new Base[8][8];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                tempBoardMatrix[i][j] = matrix[i][j]; 
            }
        }
        boardObject.prevBoards.add(tempBoardMatrix);
        boardObject.pieceInstances = addPieceInstances(matrix);

        return boardObject;
    } 

    //Helper function to intialize boardObject
    private Base[][] createMatrix(String[] rowZero, String[] rowOne, String[] rowSix, String[] rowSeven){
        Base[][] matrix = new Base[8][8];

        for (int i = 0; i < 8; i++){

            Base row0 = createBasePiece(rowZero[i], "black", 0, i);
            matrix[0][i] = row0;

            Base row1 = createBasePiece(rowOne[i], "black", 1, i);
            matrix[1][i] = row1;

            Base row6 = createBasePiece(rowSix[i], "white", 6, i);
            matrix[6][i] = row6;

            Base row7 = createBasePiece(rowSeven[i], "white", 7, i);
            matrix[7][i] = row7;
        }
        return matrix;
    }

    //Helper function to intialize boardObject
    private Base[] addPieceInstances(Base[][] boardMatrix){
        Base[] pieceInstances = new Base[32];
        for (int x = 0; x < 8; x++){
            pieceInstances[x] = boardMatrix[0][x];

            pieceInstances[x + 8] = boardMatrix[1][x];

            pieceInstances[x + 16] = boardMatrix[6][x];

            pieceInstances[x + 24] = boardMatrix[7][x];
        }
        return pieceInstances;
    }

     //Helper function to intialize boardObject
    private Base createBasePiece(String piece, String color, int y, int x){
        Base basePiece;

        if (piece.equals("rook")){
            basePiece = new Rook(color, y, x, piece);
        }
        else if (piece.equals("bishop")){
            basePiece = new Bishop(color, y, x, piece);
        }
        else if (piece.equals("knight")){
            basePiece = new Knight(color, y, x, piece);
        }
        else if (piece.equals("queen")){
            basePiece = new Queen(color, y, x, piece);
        }
        else if (piece.equals("king")){
            basePiece = new King(color, y, x, piece);
        }
        else {
            basePiece = new Pawn(color, y, x, piece);
        }
        return basePiece;
    }
}
