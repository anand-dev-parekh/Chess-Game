package pieces;
import java.lang.Math;

public class King extends Base{

    public King(String color, int x, int y,  String piece)
    {
        super(color, x, y, piece);

    }
    

    public boolean validMove(Base[][] board, int[] newPos)
    {
        int y = this.y, x = this.x;
        int newY = newPos[0], newX = newPos[1];

        int changeY = Math.abs(newY - y),  changeX = Math.abs(newX - x);

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; 
        if ((changeY == 0 || changeY == 1) && (changeX == 1 || changeX == 0)) return true;

        return false;
    }
}
