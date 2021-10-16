package pieces;


public class rook extends base{
   

    public rook(String color, int[] position, String piece){
        super(color, position, piece);
    }

    public boolean validMove(base[] board, int[] newPos)
    {
        for (int i = 0; i < board.length; i++)
        {
            if (board[i].color == this.color && board[i] instanceof king)
            {
                ;
            }
        }
        return false;
    }



}