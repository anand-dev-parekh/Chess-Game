package pieces;

public class knight extends base{

    public knight(String color, int[] position, String piece)
    {
        super(color, position, piece);
    }
    
    public boolean validMove(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1];
        int newY = newPos[0], newX = newPos[1];

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false;
        if ((newY - y) * (newY - y) + (newX - x) * (newX - x) == 5) return true;


        return false;
    }
}
