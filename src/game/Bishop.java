package game;

public class Bishop extends Base{

    public Bishop(String color, int x, int y, String piece)
    {
        super(color, x, y, piece);
    }

    public boolean validMove(Board board, int newX, int newY){
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot
        
        int xOffset = newX - x, yOffset = newY - y;

        if (Math.abs(yOffset) != Math.abs(xOffset)) return false; //Checks to make sure piece is on SAME DIAGNOL
        if (board.matrix[newY][newX] != null && board.matrix[newY][newX].color == this.color) return false; // Checks to make sure new position isnt occupied by piece of same color

        int x = this.x, y = this.y;                                     

        for (int i = 1; i < Math.abs(xOffset); i++)
        {
            x += xOffset / Math.abs(xOffset);                    //This tells us what to increment/decrement the x and y by
            y += yOffset / Math.abs(yOffset);

            if (board.matrix[y][x] != null) return false;               //If there is something blocking the path return false
        }
        return true;                                             //Return True because there is nothing blocking the path
    }
}

