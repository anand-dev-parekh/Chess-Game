package pieces;
import java.util.Arrays;

public class Rook extends Base{
   

    public Rook(String color, int x, int y, String piece){
        super(color, x, y, piece);
    }

    public boolean validMove(Base[][] board, int[] newPos) //THIS IS THE VALID METHOD
    {
        if (this.x != newPos[1] || this.y != newPos[0]) return false;

        //CHECKS IF NEW POSITION 
        if (this.toRight(board, newPos) || this.toLeft(board, newPos) || this.toTop(board, newPos) || this.toBottom(board, newPos)) return true;
        return false;
    }

    private boolean toRight(Base[][] board, int[] newPos) 
    {
        int y = this.y, x = this.x + 1;

        while (x < 8){
            int[] rightPossible = {y, x};

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(rightPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            x++;
        }
        return false;
    }

    private boolean toLeft(Base[][] board, int[] newPos)
    {
        int y = this.y, x = this.x - 1;

        while (x >= 0){
            int[] leftPossible = {y, x};

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(leftPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            x--;
        }
        return false;
    }

    private boolean toTop(Base[][] board, int[] newPos)
    {
        int x = this.x, y = this.y + 1;

        while (y < 8)
        {
            int[] topPossible = {y, x};

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(topPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            y++;
        }
        return false;
    }
    private boolean toBottom(Base[][] board, int[] newPos)
    {
        int x = this.x, y = this.y - 1;

        while (y >= 0)
        {
            int[] bottomPossible = {y, x};

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(bottomPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            y--;
        }
        return false;        
    }



}