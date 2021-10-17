package pieces;

public class Bishop extends Base{

    public Bishop(String color, int x, int y, String piece)
    {
        super(color, x, y, piece);
    }

    public boolean validMove(Base[][] board, int newX, int newY){
        
        int x = this.x, y = this.y;                                     
        int xOffset = newX - x, yOffset = newY - y;

        if (Math.abs(yOffset) != Math.abs(xOffset)) return false; //Checks to make sure piece is on SAME DIAGNOL

        for (int i = 1; i < Math.abs(xOffset); i++)
        {
            x += xOffset / Math.abs(xOffset);                    //This tells us what to increment/decrement the x and y by
            y += yOffset / Math.abs(yOffset);

            if (board[y][x] != null) return false;               //If there is something blocking the path return false
        }
        return true;                                             //Return True because there is nothing blocking the path
    }
}

