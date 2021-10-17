package pieces;

public class Base {
    //SETS the base attributes of all pieces
    final public String color;              
    public int x;
    public int y;
    public String piece;

    public Base(String color, int x, int y, String piece)
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    //Base method for all pieces 
    public boolean validMove(Base[][] board, int x, int y)
    {
        return false;
    }



    public boolean inCheck(Base[][] board, int[] newPos){ //Will check if king is in check after move has been played
        int x = this.x;
        int y = this.y;

        int newX = newPos[1];
        int newY = newPos[0];


        board[y][x] = null;
        board[newY][newX] = this;

        int[] daKing = this.findKing(board);

        if (this.knightCheck(board, daKing) || this.pawnCheck(board, daKing) || this.rookQcheck(board, daKing)) return true; // NEED TO ADD ALL PIECES TO THIS IF STATEMENT

        return false;
    }


    private int[] findKing(Base[][] board)
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

    private boolean rookQcheck(Base[][] board, int[] daKing){


        return false;
    }


    //UGLY PART OF CODE
    private boolean knightCheck(Base[][] board, int[] daKing) //Looks better????? Figure out way of non hard coding????
    {
        int kingY = daKing[0], kingX = daKing[1];
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){
            if (knightChecks[i][0] < 8 && knightChecks[i][0] >= 0 && knightChecks[i][1] < 8 && knightChecks[i][1] >= 0){
                if (board[knightChecks[i][0]][knightChecks[i][1]].piece == "knight" && board[knightChecks[i][0]][knightChecks[i][1]].color != this.color) return true;
            }

        }
        return false;
    }

    private boolean pawnCheck(Base[][] board, int[] daKing){ // NEED TO FIX THE FUNCTION BROKEN RN BROKEN 
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
