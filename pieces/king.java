package pieces;
import java.lang.Math;

public class king extends base{

    public king(String color, int[] position,  String piece)
    {
        super(color, position, piece);

    }
    

    public boolean validMove(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1];
        int newY = newPos[0], newX = this.position[1];

        int changeY = Math.abs(newY - y),  changeX = Math.abs(newX - x);

        if ((changeY == 0 || changeY == 1) && (changeX == 1 || changeX == 0)) return true;

        return false;
    }
}
