package pieces;
import java.util.Arrays;

public class queen extends base{

    public queen(String color, int[] position, String piece)
    {
        super(color, position, piece);
    }

    //I DO NOT KNOW IF THERES SOME OOP THInG THAT WOULD MAKE THIS MORE CLEAN BUT WE CAN JUST COPY FUNCTIONS FROM BISHOP AND ROOK CLASS FOR VALID MOVE

    public boolean validMove(base[][] board, int[] newPos){

        if (this.toRight(board, newPos) || this.toLeft(board, newPos) || this.toTop(board, newPos) || this.toBottom(board, newPos) || this.toTopRight(board, newPos) || this.toBottomRight(board, newPos) || this.toBottomLeft(board, newPos) || this.toTopLeft(board, newPos)) return true;

        return false;
    }


    private boolean toRight(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1] + 1;

        while (x < 8){

            int[] rightPossible = {y, x};

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(rightPossible, newPos)) return true;
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

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(leftPossible, newPos)) return true;
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

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(topPossible, newPos)) return true;
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
            
            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(bottomPossible, newPos)) return true;
            else if (board[y][x] != null) return false;
            y--;
        }
        return false;        
    }

    public boolean toTopRight(base[][] board, int[] newPos)
    {
        int y = this.position[0] - 1, x = this.position[1] + 1;

        while (x < 8 && y >= 0)
        {
            int[] topRightPossible = {y, x};
            
            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(topRightPossible, newPos)) return true;
            else if (board[y][x] != null) return false;

            x++; y--;
        }
        return false;
    }
    public boolean toBottomRight(base[][] board, int[] newPos)
    {
        int y = this.position[0] + 1, x = this.position[1] + 1;

        while (x < 8 && y >= 0)
        {
            int[] bottomRightPossible = {y, x};
            
            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(bottomRightPossible, newPos)) return true;
            else if (board[y][x] != null) return false;

            x++; y++;
        }
        return false;
    }
    public boolean toTopLeft(base[][] board, int[] newPos){

        int y = this.position[0] - 1, x = this.position[1] - 1;

        while (x < 8 && y >= 0)
        {
            int[] topLeftPossible = {y, x};

            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(topLeftPossible, newPos)) return true;
            else if (board[y][x] != null) return false;

            x--; y--;
        }

        return false;
    }
    public boolean toBottomLeft(base[][] board, int[] newPos){

        int y = this.position[0] + 1, x = this.position[1] - 1;

        while (x >= 0 && y < 8)
        {
            int[] bottomLeftPossible = {y, x};
            
            if (board[y][x] != null && board[y][x].color == this.color) return false;
            else if (Arrays.equals(bottomLeftPossible, newPos)) return true;
            else if (board[y][x] != null) return false;

            x--; y++;
        }

        return false;
    }




}