package ChessGUIScenes;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;



public class BoardEditorGUI extends GridPane{
    public BoardEditorGUI(){
        createBoard();
    }

    public void deleteDaPiece(int newY, int newX){
        ObservableList<Node> daNodes = this.getChildren();


        Node pieceToRemove = null;
        for (Node node : daNodes){
            if (node instanceof PieceGUIEditor && GridPane.getColumnIndex(node) == newX && GridPane.getRowIndex(node) == newY) pieceToRemove = node;
        }

        if (pieceToRemove != null) this.getChildren().remove(pieceToRemove);
    }




    private void createBoard(){
        HelperGUI.createBackground(this);

        //Create Piece menu
        String[] pieceIterators = {"king", "queen", "rook", "bishop", "knight", "pawn"};
        for (int i = 0; i < pieceIterators.length; i++){
            Rectangle staticPieceWhite = new Rectangle(90, 90, 90, 90);
            Rectangle staticPieceBlack = new Rectangle(90, 90, 90, 90);
            
            try{
                //White creation
                FileInputStream pathway = new FileInputStream(FilePaths.daPath + "white" + pieceIterators[i] + ".png");                   
                Image image = new Image(pathway);
                staticPieceWhite.setFill(new ImagePattern(image));

                PieceGUIEditor whitePieceNode = new PieceGUIEditor(this, pieceIterators[i], "white");




                //Black creation
                pathway = new FileInputStream(FilePaths.daPath + "black" +  pieceIterators[i] + ".png");                   
                image = new Image(pathway);
                staticPieceBlack.setFill(new ImagePattern(image));

                PieceGUIEditor blackPieceNode = new PieceGUIEditor(this, pieceIterators[i], "black");

                this.add(staticPieceBlack, 9, i);
                this.add(staticPieceWhite, 8, i);

                this.add(blackPieceNode, 9, i);
                this.add(whitePieceNode, 8, i);
            }
            catch (IOException e){
                System.out.println("ayohh");
            }
        }

        Button clearEditorBoard = new Button("Clear");
        Button startPosition = new Button("Start Pos");

        clearEditorBoard.setOnAction(e -> this.clearPieces());
        startPosition.setOnAction(e -> this.startPosition());
        
        GridPane.setHalignment(clearEditorBoard, HPos.CENTER);
        GridPane.setValignment(clearEditorBoard, VPos.CENTER);
        GridPane.setHalignment(startPosition, HPos.CENTER);
        GridPane.setValignment(startPosition, VPos.CENTER);



        this.add(clearEditorBoard, 8, 6);
        this.add(startPosition, 9, 6);

    }

    private void clearPieces(){
        ObservableList<Node> daNodes = this.getChildren();


        ArrayList<Node> nodesToRemove = new ArrayList<Node>();


        //Iterater thru da board nodes
        for (Node node : daNodes){
            if (node instanceof PieceGUIEditor && GridPane.getColumnIndex(node) <= 7) nodesToRemove.add(node);
        }

        //Remove da board nodes
        for (Node node : nodesToRemove){
            this.getChildren().remove(node);
        }
    }

    private void startPosition(){
        this.clearPieces();
        //Black Pieces here
        String[] order = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
        for (int x = 0; x < 8; x++){
            this.add(new PieceGUIEditor(this, order[x], "black"), x, 0);
            this.add(new PieceGUIEditor(this, order[x], "white"), x, 7);
            this.add(new PieceGUIEditor(this, "pawn", "black"), x, 1);
            this.add(new PieceGUIEditor(this, "pawn", "white"), x, 6);
        }
    }
}
