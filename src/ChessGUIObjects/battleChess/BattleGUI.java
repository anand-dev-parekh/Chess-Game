package ChessGUIObjects.battleChess;

import javafx.scene.control.Label;

import ChessGUIObjects.BaseBoardGUI;
import ChessGUIObjects.BasePieceGUI;

public class BattleGUI extends BaseBoardGUI{
    
    
    public BattleGUI(Label stateOfDaMove){

        this.stateOfDaMove = stateOfDaMove;
        this.isGalooeh = false;


        createBackground();

        String[] top = {"bishop", "bishop", "bishop", "bishop", "bishop", "bishop", "bishop", "bishop"};
        String[] bot = {"knight", "knight", "knight", "knight", "knight", "knight", "knight", "knight"};

        this.boardObject = intializeBoardObject(top, top, bot, bot);
        createBattleGUI(stateOfDaMove);
    }


    private void createBattleGUI(Label stateOfDaMove){
        for (int i = 0; i < 8; i++){
            BattlePieceGUI topBishopNode = new BattlePieceGUI("teemteem", this);
            this.boardObject.matrix[0][i].basePieceGUI = topBishopNode;
            this.add(topBishopNode, i, 0);
            
            
            BattlePieceGUI secondBishopNode = new BattlePieceGUI("teemteem", this);
            this.boardObject.matrix[1][i].basePieceGUI = secondBishopNode;
            this.add(secondBishopNode, i, 1);
            
            
            BattlePieceGUI knightBottomNode = new BattlePieceGUI("ayoh", this);
            this.boardObject.matrix[7][i].basePieceGUI = knightBottomNode;
            this.add(knightBottomNode, i, 7);
            
            BattlePieceGUI secondKnightBottomNode = new BattlePieceGUI("ayoh", this);
            this.boardObject.matrix[6][i].basePieceGUI = secondKnightBottomNode;
            this.add(secondKnightBottomNode, i, 6);
        }
    }


    public void updateBoardGUI(int y, int x, int newY, int newX, BasePieceGUI pieceGUI){
        this.movePiece(pieceGUI, newY, newX);
    }
}
