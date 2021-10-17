package pieces;

public class Knight extends Base{

    public Knight(String color, int x, int y, String piece)
    {
        super(color, x, y, piece);
    }
    
    public boolean validMove(Base[][] board, int newX, int newY)
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot

        int y = this.y, x = this.x;

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; // Checks to make sure piece isnt capturing from its own color
        if ((newY - y) * (newY - y) + (newX - x) * (newX - x) == 5) return true;              // Uses distance formula to find knights move

        return false;
    }
}
