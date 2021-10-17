import pieces.base;
import pieces.king;
import pieces.queen;
import pieces.rook;
import pieces.bishop;
import pieces.knight;

class chess{
    public static void main(String[] args)
    {




        king king1 = new king("white", 0, 5,"king");
        queen queen1 = new queen("white", 4, 0, "queen");
        rook rook1 = new rook("black", 5,  5,"rook");
        bishop bishop1 = new bishop("black", 5, 5, "bishop");
        knight knight1 = new knight("white", 2, 2, "knight");


        base[][] board = {{null, null, null, null, null, king1, queen1, null}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, knight1, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {rook1, null, null, null, null, null, null, null},
                          {null, null, null, null, null, bishop1, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                                }; 


        int[] bishopMove = {2, 2};
        System.out.println(board[5][5].validMove(board, bishopMove));

    }
}