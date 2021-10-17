package pieces;

public class Rook extends Base{
   

    public Rook(String color, int x, int y, String piece){
        super(color, x, y, piece);
    }

    public boolean validMove(Board board, int newX, int newY) //THIS IS THE VALID METHOD
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot    

        if (this.x != newX && this.y != newY) return false;
        if (board.matrix[newY][newX] != null && board.matrix[newY][newX].color == this.color) return false; // Checks to make sure new position isnt occupied by piece of same color
        
        int x = this.x, y = this.y;
        int xOffset = newX - x, yOffset = newY - y;


        for (int i = 1; i < Math.max(xOffset, yOffset); i++)
        {   
            if (xOffset != 0) x += xOffset/Math.abs(xOffset);       //xOffset or yOffset will be 0 so must use if statment for the incrementing
            else y += yOffset/Math.abs(yOffset);                    

            if (board.matrix[y][x] != null) return false;                  //Returns false since piece blocking path
        }
        return true;
    }




}
