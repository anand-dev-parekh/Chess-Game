import java.util.ArrayList;
import game.Board;
import game.Base;
import game.King;
import game.Queen;
import game.Rook;
import game.Bishop;
import game.Knight;
import game.Pawn;

class Chess{
    public static void main(String[] args)
    {

        King king1 = new King("white", 0, 3,"king");
        Queen queen1 = new Queen("white", 0, 6, "queen");
        Rook rook1 = new Rook("black", 4,  0,"rook");
        Bishop bishop1 = new Bishop("black", 5, 5, "bishop");
        Knight knight1 = new Knight("black", 2, 2, "knight");
        Pawn onePond = new Pawn("white", 4, 7, "pond");


        Base[][] matrix = {{null, null, null, king1, null, null, queen1, null}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, knight1, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {rook1, null, null, null, null, null, null, onePond},
                          {null, null, null, null, null, bishop1, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                                }; 

        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();
        Board board = new Board(matrix, prevBoards);

        System.out.println(board.matrix[0][6].inCheck(board, 0, 5));




    }
}
