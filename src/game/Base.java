package game;

public abstract class Base {
    //SETS the base attributes of all pieces
    final public String color;              
    public int x;
    public int y;
    public String piece;

    public Base(String color, int x, int y, String piece){
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    //Base method for all pieces 
    public abstract boolean validMove(Board board, int x, int y);

    public boolean inCheck(Board board, int newX, int newY){ //Will check if king is in check after move has been played
        int x = this.x;
        int y = this.y;

        board.matrix[y][x] = null;
        board.matrix[newY][newX] = this;

        int[] daKing = this.findKing(board.matrix);

        if (this.knightCheck(board.matrix, daKing[1], daKing[0]) || this.pawnCheck(board.matrix, daKing[1], daKing[0]) || this.rookQcheck(board.matrix, daKing[1], daKing[0]) || this.bishopQcheck(board.matrix, daKing[1], daKing[0])) return true; // NEED TO ADD ALL PIECES TO THIS IF STATEMENT

        return false;
    }

    //BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
    private int[] findKing(Base[][] board){
        for (int i = 0; i < board.length; i++){

            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j].piece == "king" && board[i][j].color == this.color){
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {0, 0};
    }


    private boolean knightCheck(Base[][] board, int kingX, int kingY){ //LOOK BETTER????
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){
            
            if (inBounds(knightChecks[i][0], knightChecks[i][1])){
                if (board[knightChecks[i][0]][knightChecks[i][1]].piece == "knight" && board[knightChecks[i][0]][knightChecks[i][1]].color != this.color) return true;
            }

        }
        return false;
    }

    private boolean pawnCheck(Base[][] board, int kingX, int kingY){ // NEED TO FIX THE FUNCTION BROKEN RN BROKEN 
        int possibleX1 = kingX - 1, possibleX2 = kingX + 1, possibleY;
        if (this.color == "white"){
            possibleY = kingY - 1; 
        }
        else{
            possibleY = kingY + 1;
        }
        if ((board[possibleY][possibleX1].piece == "pawn" && board[possibleY][possibleX1].color != this.color) || (board[possibleY][possibleX2].piece == "pawn" && board[possibleY][possibleX2].color != this.color)) return true;
        return false;
    }

    private boolean rookQcheck(Base[][] board, int kingX, int kingY){ // NEED TO FINISH
        return false;
    }

    private boolean bishopQcheck(Base[][] board, int kingX, int kingY){
        return true;
    }

    private boolean inBounds(int y, int x){
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;

        return false;
    }

}
