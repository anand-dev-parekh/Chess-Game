import game.Base;
import game.King;
import game.Queen;
import game.Rook;
import game.Bishop;
import game.Knight;

class Chess{
    public static void main(String[] args)
    {

        King king1 = new King("white", 0, 5,"king");
        Queen queen1 = new Queen("white", 4, 0, "queen");
        Rook rook1 = new Rook("black", 5,  5,"rook");
        Bishop bishop1 = new Bishop("black", 5, 5, "bishop");
        Knight knight1 = new Knight("black", 2, 2, "knight");


        Base[][] board = {{null, null, null, null, null, king1, queen1, null}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, knight1, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {rook1, null, null, null, null, null, null, null},
                          {null, null, null, null, null, bishop1, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                                }; 



    }
}
