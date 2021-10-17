import pieces.base;
import pieces.king;
import pieces.queen;
import pieces.rook;

class chess{
    public static void main(String[] args)
    {

        int[] test = {0, 1};
        int[] rookLocation = {4, 0};
        int[] wrongLocation = {3, 4};
        int[] rightRookLocation = {2, 0};



        king king1 = new king("white", test, "king");
        queen queen1 = new queen("white", test, "queen");
        rook rook1 = new rook("black", rookLocation, "rook");


        base[][] board = {{null, null, null, null, null, king1, queen1, null, null}, 
                          {null, null, null, null, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null, null},
                          {rook1, null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null, null}
                                }; 
        System.out.println(rook1.validMove(board, rightRookLocation));

    }
}