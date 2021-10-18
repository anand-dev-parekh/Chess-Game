package game;
import java.lang.Math;

public class King extends Base{

    public King(String color, int y, int x,  String piece)
    {
        super(color, y, x, piece);

    }
    
    public boolean validMove(Board board, int newY, int newX)
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot

        int changeY = Math.abs(newY - y),  changeX = Math.abs(newX - x);

        if (board.matrix[newY][newX] != null && board.matrix[newY][newX].color == this.color) return false; //Checks to make sure piece isnt occupied by same color
        if ((changeY == 0 || changeY == 1) && (changeX == 1 || changeX == 0)) return true;    //Returns bubble around king if true

        return false;
    }
}
