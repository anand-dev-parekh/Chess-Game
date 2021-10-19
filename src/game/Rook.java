package game;

public class Rook extends Base{
   

    public Rook(String color, int y, int x, String piece, boolean hasMoved){
        super(color, y, x, piece, hasMoved);
    }

    public boolean validMove(Board board, int newY, int newX) //THIS IS THE VALID METHOD
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot    

        if (this.x != newX && this.y != newY) return false; //Checks that either x or y value is same.
        if (board.matrix[newY][newX] != null && board.matrix[newY][newX].color == this.color) return false; // Checks to make sure new position isnt occupied by piece of same color
        
        int x = this.x, y = this.y;
        int xOffset = newX - x, yOffset = newY - y;


        for (int i = 1; i < Math.max(Math.abs(xOffset), Math.abs(yOffset)); i++)
        {   
            if (xOffset != 0) x += xOffset/Math.abs(xOffset);       //xOffset or yOffset will be 0 so must use if statment for the incrementing
            else y += yOffset/Math.abs(yOffset);                    

            if (board.matrix[y][x] != null) return false;                  //Returns false since piece blocking path
        }
        return true;
    }
}