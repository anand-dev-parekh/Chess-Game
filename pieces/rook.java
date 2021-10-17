package pieces;
import java.util.Arrays;

public class rook extends base{
   

    public rook(String color, int[] position, String piece){
        super(color, position, piece);
    }

    public boolean validMove(base[][] board, int[] newPos)
    {
        if (this.toRight(board, newPos) || this.toLeft(board, newPos) || this.toTop(board, newPos) || this.toBottom(board, newPos)) return true;
        return false;
    }

    private boolean toRight(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1] + 1;

        while (x < 8){
            int[] rightPossible = {y, x};
    
            if (Arrays.equals(rightPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            x++;
        }
        return false;
    }

    private boolean toLeft(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1] - 1;

        while (x >= 0){
            int[] leftPossible = {y, x};


            if (Arrays.equals(leftPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            x--;
        }
        return false;
    }

    private boolean toTop(base[][] board, int[] newPos)
    {
        int x = this.position[1], y = this.position[0] + 1;

        while (y < 8)
        {
            int[] topPossible = {y, x};

            if (Arrays.equals(topPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            y++;
        }
        return false;
    }
    private boolean toBottom(base[][] board, int[] newPos)
    {
        int x = this.position[1], y = this.position[0] - 1;

        while (y >= 0)
        {
            int[] bottomPossible = {y, x};

            if (Arrays.equals(bottomPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            y--;
        }
        return false;        
    }



}