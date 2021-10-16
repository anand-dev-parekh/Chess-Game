package pieces;

public class base {
    public String color;
    public int[] position;
    public String piece;

    public base(String color, int[] position, String piece)
    {
        this.color = color;
        this.position = position;
        this.piece = piece;
    }


    public boolean validMove(base[][] board, int[] newPos)
    {
        return false;
    }

    public boolean inCheck(base[][] board, int[] newPos){
        int x = this.position[1];
        int y = this.position[0];

        int newX = newPos[1];
        int newY = newPos[0];


        board[y][x] = null;
        board[newY][newX] = this;

        int[] daKing = this.findKing(board);

        if (this.knightCheck(board, daKing)) return true; // NEED TO ADD ALL PIECES TO THIS IF STATEMENT

        return false;
    }


    private int[] findKing(base[][] board)
    {
        for (int i = 0; i < board.length; i++){

            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j].piece == "king" && board[i][j].color == this.color){
                    int[] daKing = {i, j};
                    return daKing;
                }
            }
        }
        int[] fake = {0, 1};
        return fake;
    }

    private boolean knightCheck(base[][] board, int[] daKing) //STILL NEED TO WRITE FUNCTION
    {



        return false;


    }


}
