package pieces;

public class Knight extends Base{

    public Knight(String color, int x, int y, String piece)
    {
        super(color, x, y, piece);
    }
    
    public boolean validMove(Base[][] board, int[] newPos)
    {
        int y = this.y, x = this.x;
        int newY = newPos[0], newX = newPos[1];

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false;
        if ((newY - y) * (newY - y) + (newX - x) * (newX - x) == 5) return true;


        return false;
    }
}
