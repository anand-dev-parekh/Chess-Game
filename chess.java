import pieces.base;
import pieces.king;
import pieces.queen;
import pieces.rook;
import pieces.bishop;
import pieces.knight;

class chess{
    public static void main(String[] args)
    {

        int[] test = {0, 1};
        int[] rookLocation = {4, 0};
        int[] bishopLocation = {5, 5};
        int[] knightLocation = {2, 2};


        king king1 = new king("white", test, "king");
        queen queen1 = new queen("white", test, "queen");
        rook rook1 = new rook("black", rookLocation, "rook");
        bishop bishop1 = new bishop("black", bishopLocation, "bishop");
        knight knight1 = new knight("white", knightLocation, "knight");


        base[][] board = {{null, null, null, null, null, king1, queen1, null}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, knight1, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {rook1, null, null, null, null, null, null, null},
                          {null, null, null, null, null, bishop1, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                                }; 


        int[] bishopMove = {1, 1};
        System.out.println(bishop1.validMove(board, bishopMove));

    }
}