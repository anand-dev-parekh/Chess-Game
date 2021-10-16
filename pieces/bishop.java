package pieces;

public class bishop extends base{

    public bishop(String color, int[] position, String piece)
    {
        super(color, position, piece);
    }

    public boolean validMove(base[][] board, int[] newPos){

        if (this.toTopRight(board, newPos) || this.toBottomRight(board, newPos) || this.toBottomLeft(board, newPos) || this.toTopLeft(board, newPos)) return true;
        return false;
    }

    public boolean toTopRight(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1];

        while (x < 9 && y >= 0)
        {
            int[] topRightPossible = {y, x};
            if (topRightPossible == newPos) return true;
            else if (board[y][x] != null) return false;

            x++; y--;
        }
        return false;
    }
    public boolean toBottomRight(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1];

        while (x < 9 && y >= 0)
        {
            int[] bottomRightPossible = {y, x};
            if (bottomRightPossible == newPos) return true;
            else if (board[y][x] != null) return false;

            x++; y++;
        }
        return false;
    }
    public boolean toTopLeft(base[][] board, int[] newPos){

        int y = this.position[0], x = this.position[1];

        while (x < 9 && y >= 0)
        {
            int[] topLeftPossible = {y, x};
            if (topLeftPossible == newPos) return true;
            else if (board[y][x] != null) return false;

            x--; y--;
        }

        return false;
    }
    public boolean toBottomLeft(base[][] board, int[] newPos){

        int y = this.position[0], x = this.position[1];

        while (x < 9 && y >= 0)
        {
            int[] bottomLeftPossible = {y, x};
            if (bottomLeftPossible == newPos) return true;
            else if (board[y][x] != null) return false;

            x--; y++;
        }

        return false;
    }


    
}
