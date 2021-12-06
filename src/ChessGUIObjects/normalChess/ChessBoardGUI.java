package ChessGUIObjects.normalChess;

import game.Queen;
import game.Rook;
import game.Bishop;
import game.Knight;

import ChessGUIObjects.BaseBoardGUI;
import ChessGUIObjects.BasePieceGUI;
import ChessGUIObjects.FilePaths;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class ChessBoardGUI extends BaseBoardGUI{
    
    private Stage promotionAlertWhite;
    private Stage promotionAlertBlack;
    
    private int newXPromotion;
    private int newYPromotion;


    public ChessBoardGUI(Label stateOfMove, boolean isGalooehKnows){
        //Input state label
        this.stateOfDaMove = stateOfMove;

        //Should pics represent actual piece or galooeh
        this.isGalooeh = isGalooehKnows;


        //Inits the boardObject
        String[] topPieces  = {"rook", "knight", "bishop", "king", "queen", "bishop", "knight", "rook"};
        String[] botPieces  = {"pawn", "pawn", "pawn", "pawn", "pawn", "pawn", "pawn", "pawn"};

        this.boardObject =  intializeBoardObject(topPieces, botPieces, botPieces, topPieces);
        //creates pieceGUIs and background
        createBoardGUI();
        
        
        //Inits Promotion stages
        this.promotionAlertWhite = new Stage();
        createPromotionStage("white", this.promotionAlertWhite);

        this.promotionAlertBlack = new Stage();
        createPromotionStage("black", this.promotionAlertBlack);
    }


    private void createPromotionStage(String color, Stage daStage){
        HBox holder = new HBox();


        String[] pieces  = {"queen", "rook", "bishop", "knight"};
        for (String piece : pieces){
            try {
                FileInputStream pathway;
                if (isGalooeh) { //Sets img to galooeh
                    pathway = new FileInputStream(FilePaths.daPath + "galooeh.png");
                }
                else { //sets img to actual piece
                    pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
                }
                
                Image image = new Image(pathway);
                            
                ImageView imageNode = new ImageView(image);  
            
            
                Button buttonPiece = new Button();
                buttonPiece.setGraphic(imageNode);

                buttonPiece.setOnMouseClicked(e ->{
                    this.updateGUIForPromotion(piece, color);
                    daStage.close();
                });

                holder.getChildren().add(buttonPiece);
            
            }
            catch(IOException ye ) {System.out.println("Ayoh");}                       

        }  
        Scene promotionScene = new Scene(holder);
        daStage.setScene(promotionScene);
    }


    private void updateGUIForPromotion(String piece, String color){


        destroyPiece(this.boardObject.matrix[this.newYPromotion][this.newXPromotion].basePieceGUI);



        if (piece.equals("rook")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Rook(color, this.newYPromotion, this.newXPromotion, piece);
        }
        else if (piece.equals("bishop")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Bishop(color, this.newYPromotion, this.newXPromotion, piece);
        }
        else if (piece.equals("knight")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Knight(color, this.newYPromotion, this.newXPromotion, piece);
        }
        else if (piece.equals("queen")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Queen(color, this.newYPromotion, this.newXPromotion, piece);
        }



        try{
            FileInputStream pathway;
            if (isGalooeh) { //Sets img to galooeh
                pathway = new FileInputStream(FilePaths.daPath + "galooeh.png");
            }
            else { //sets img to actual piece
                pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
            }

            ChessPieceGUI promotionPiece = new ChessPieceGUI(pathway, this);
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion].basePieceGUI = promotionPiece;

            GridPane.setRowIndex(promotionPiece, this.newYPromotion);
            GridPane.setColumnIndex(promotionPiece, this.newXPromotion);


            this.getChildren().addAll(promotionPiece);
        }
        catch (IOException e){
            System.out.println("Promotion image aint workin");
        }
    }

    //Moves the acutal piece
    public void updateBoardGUI(int y, int x, int newY, int newX, BasePieceGUI pieceGUI){
        this.movePiece(pieceGUI, newY, newX);

        if (this.boardObject.matrix[newY][newX].castle){

            if (newX > x){
                //Updates node
                GridPane.setColumnIndex(this.boardObject.matrix[newY][newX - 1].basePieceGUI, newX - 1);
                GridPane.setRowIndex(this.boardObject.matrix[newY][newX - 1].basePieceGUI, newY);

                //sets node to translate of 0
                this.boardObject.matrix[newY][newX - 1].basePieceGUI.setTranslateX(0);
                this.boardObject.matrix[newY][newX - 1].basePieceGUI.setTranslateY(0);
                GridPane.setHalignment(this.boardObject.matrix[newY][newX - 1].basePieceGUI, HPos.CENTER);
                GridPane.setValignment(this.boardObject.matrix[newY][newX - 1].basePieceGUI, VPos.CENTER);
            }
            else{
                GridPane.setColumnIndex(this.boardObject.matrix[newY][newX + 1].basePieceGUI, newX + 1);
                GridPane.setRowIndex(this.boardObject.matrix[newY][newX + 1].basePieceGUI, newY);
                this.boardObject.matrix[newY][newX + 1].basePieceGUI.setTranslateX(0);
                this.boardObject.matrix[newY][newX + 1].basePieceGUI.setTranslateY(0);
                GridPane.setHalignment(this.boardObject.matrix[newY][newX + 1].basePieceGUI, HPos.CENTER);
                GridPane.setValignment(this.boardObject.matrix[newY][newX + 1].basePieceGUI, VPos.CENTER);   
            }
        }

        if (this.boardObject.matrix[newY][newX].promotion){
            this.newXPromotion = newX;
            this.newYPromotion = newY;

            if (this.boardObject.matrix[newY][newX].color.equals("white")) this.promotionAlertWhite.show();

            else this.promotionAlertBlack.show();
        }
    }


    //For now we will just create a new instance. DOwn the road make pieces attributes, and just reinput pieces to save memory.
    public void resetBoard(){
        if (isGalooeh){
            this.buttonContainer.getChildren().remove(this.moveBackward);
            this.buttonContainer.getChildren().remove(this.moveForward);
        }
        this.boardView = 1;

        this.boardObject.resetBoardObject();
        createBoardGUI();

    }


    private void createBoardGUI(){
        this.getChildren().clear();

        this.createBackground();

        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                

                if (boardObject.matrix[y][x] != null){
                    try{
                        FileInputStream pathway;
                        if (isGalooeh) { //Sets img to galooeh
                            pathway = new FileInputStream(FilePaths.daPath + "galooeh.png");
                        }
                        else { //sets img to actual piece
                            pathway = new FileInputStream(FilePaths.daPath + this.boardObject.matrix[y][x].color + this.boardObject.matrix[y][x].piece + ".png");
                        }                                    
                        
                        ChessPieceGUI imageNode = new ChessPieceGUI(pathway, this);                        
                        this.boardObject.matrix[y][x].basePieceGUI = imageNode;
                        GridPane.setRowIndex(imageNode, y);
                        GridPane.setColumnIndex(imageNode, x);

                        this.getChildren().addAll(imageNode);
                        
                        GridPane.setHalignment(imageNode, HPos.CENTER);
                        GridPane.setValignment(imageNode, VPos.CENTER);

                    }
                    catch (IOException e) {
                        System.out.println("FILE ERROR PICTURES");
                    }
                }
            }
        }
        
        return;
    }


}
