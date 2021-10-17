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

        if (this.knightCheck(board, daKing) || this.pawnCheck(board, daKing)) return true; // NEED TO ADD ALL PIECES TO THIS IF STATEMENT

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

    private boolean knightCheck(base[][] board, int[] daKing) //Looks better????? Figure out way of non hard coding????
    {
        int kingY = daKing[0], kingX = daKing[1];
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 2, kingX + 2}, {kingY + 2, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 2, kingX - 2}, {kingY + 2, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){
            if (knightChecks[i][0] < 8 && knightChecks[i][0] >= 0 && knightChecks[i][1] < 8 && knightChecks[i][1] >= 0){
                if (board[knightChecks[i][0]][knightChecks[i][1]].piece == "knight" && board[knightChecks[i][0]][knightChecks[i][1]].color != this.color) return true;
            }

        }
        return false;
    }

    private boolean pawnCheck(base[][] board, int[] daKing){ // NEED TO FIX THE FUNCTION BROKEN RN BROKEN 
        if (this.color == "white"){
            int kingY = daKing[0], kingX = daKing[1];

            if (kingY != 0 && kingX != 8 && kingX != 0){
                if ((board[--kingY][++kingX].piece == "pawn" && board[--kingY][++kingX].color != this.color) || (board[--kingY][--kingX].piece == "pawn" && board[--kingY][--kingX].color != this.color)) return true;
                return false;
            }
            return true;
        }
        return false;
    }


}