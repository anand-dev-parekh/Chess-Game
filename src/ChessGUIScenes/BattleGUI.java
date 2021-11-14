package ChessGUIScenes;

import game.Bishop;
import game.Board;
import game.Knight;
import game.Base;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class BattleGUI extends GridPane{
    public Board boardObject;
    public int whitePieceCount = 16;
    public int blackPieceCount = 16;
    
    private Label stateOfDaMove;
    
    public BattleGUI(Label stateOfDaMove){

        this.stateOfDaMove = stateOfDaMove;

        this.boardObject = createGame();
        createBattleGUI(stateOfDaMove);
    }


    public void destroyPiece(BattlePieceGUI battlePieceGUI, String color){
        this.getChildren().remove(battlePieceGUI);
        if (color.equals("white")) whitePieceCount--;
        else blackPieceCount--;
        
        if (blackPieceCount == 0 || whitePieceCount == 0) endGame(color);
    }   

    private void endGame(String color){
        if (color.equals("black")) stateOfDaMove.setText("LEZ GOO AYOOH. AYOH VICTORIOUS");
        else stateOfDaMove.setText("TEEMTEEM for da win ONW");
    }



    public void updateBattleGUI(BattlePieceGUI battleGUI, int newY, int newX){
        GridPane.setColumnIndex(battleGUI, newX);
        GridPane.setRowIndex(battleGUI, newY);
        battleGUI.setTranslateX(0);
        battleGUI.setTranslateY(0);
        GridPane.setHalignment(battleGUI, HPos.CENTER);
        GridPane.setValignment(battleGUI, VPos.CENTER);

    }

    private void createBattleGUI(Label stateOfDaMove){
        for (int i = 0; i < 8; i++){
            BattlePieceGUI topBishopNode = new BattlePieceGUI("teemteem", stateOfDaMove, this);
            this.boardObject.matrix[0][i].battlePieceGUI = topBishopNode;
            this.add(topBishopNode, i, 0);
            
            
            BattlePieceGUI secondBishopNode = new BattlePieceGUI("teemteem", stateOfDaMove, this);
            this.boardObject.matrix[1][i].battlePieceGUI = secondBishopNode;
            this.add(secondBishopNode, i, 1);
            
            
            BattlePieceGUI knightBottomNode = new BattlePieceGUI("ayoh", stateOfDaMove, this);
            this.boardObject.matrix[7][i].battlePieceGUI = knightBottomNode;
            this.add(knightBottomNode, i, 7);
            
            BattlePieceGUI secondKnightBottomNode = new BattlePieceGUI("ayoh", stateOfDaMove, this);
            this.boardObject.matrix[6][i].battlePieceGUI = secondKnightBottomNode;
            this.add(secondKnightBottomNode, i, 6);
        }
    }


    private Board createGame(){
        HelperGUI.createBackground(this);
        
        Base[][] matrix = {{null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}};
    
        for (int i = 0; i < 8; i++){
            //Top layer bishops
            Base bishopTop = new Bishop("black", 0, i, "bishop");
            matrix[0][i] = bishopTop;


            //Second layer bishops
            Base bishopSecondTop = new Bishop("black", 1, i, "bishop");
            matrix[1][i] = bishopSecondTop;



            //Bottom Knights
            Base knightBottom = new Knight("white", 7, i, "knight");
            matrix[7][i] = knightBottom;



            //Second bottom knights layer

            Base knightSecondBottom = new Knight("white", 6, i, "knight");
            matrix[6][i] = knightSecondBottom;

        }
        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();

        //create board object
        Board boardObject = new Board(matrix, prevBoards);
        return boardObject;
    }


}
