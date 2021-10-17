package pieces;
import java.lang.Math;

public class King extends Base{

    public King(String color, int x, int y,  String piece)
    {
        super(color, x, y, piece);

    }
    
    public boolean validMove(Base[][] board, int newX, int newY)
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot

        int y = this.y, x = this.x;

        int changeY = Math.abs(newY - y),  changeX = Math.abs(newX - x);

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; //Checks to make sure piece isnt occupied by same color
        if ((changeY == 0 || changeY == 1) && (changeX == 1 || changeX == 0)) return true;    //Returns bubble around king if true

        return false;
    }
}
