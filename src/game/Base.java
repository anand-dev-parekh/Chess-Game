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

        //Check all the possible ways the king could be checked
        if (this.knightCheck(board.matrix, daKing[1], daKing[0]) || this.pawnCheck(board.matrix, daKing[1], daKing[0]) || this.rookQcheck(board.matrix, daKing[1], daKing[0]) || this.bishopQcheck(board.matrix, daKing[1], daKing[0])) return true; // NEED TO ADD ALL PIECES TO THIS IF STATEMENT

        return false;
    }

    /*          BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
                BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
                BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
                BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
    */

    //finds kings position
    private int[] findKing(Base[][] board){
        for (int i = 0; i < board.length; i++){ //Iterates all the colums/y value of board

            for (int j = 0; j < board[i].length; j++) //Iterates all x values for each column/y value
            {
                if (board[i][j].piece == "king" && board[i][j].color == this.color){ //If u find the king return its position
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {0, 0};
    }

    //Checks if Knight is checking king
    private boolean knightCheck(Base[][] board, int kingX, int kingY){ 

        //All possible knight moves based from kings position
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){ //Checks all positions knight could be to check king
            
            if (inBounds(knightChecks[i][0], knightChecks[i][1])){ // If knight position is in board indexes range
                if (board[knightChecks[i][0]][knightChecks[i][1]].piece == "knight" && board[knightChecks[i][0]][knightChecks[i][1]].color != this.color) return true; //returns true if piece is knight and oposite color
            }

        }
        return false;
    }
    
    //Checks if coordanites are in bounds or out of board
    private boolean inBounds(int y, int x){
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;
        return false;
    }


    //Checks if pawn is Checking
    private boolean pawnCheck(Base[][] board, int kingX, int kingY){ 
        int possibleX1 = kingX - 1, possibleX2 = kingX + 1, possibleY;

        if (this.color == "white") possibleY = kingY - 1;            //King can only be checked by pond in front of it
        else possibleY = kingY + 1;                                 //King can only be checked by pond in front of it

        if ((board[possibleY][possibleX1].piece == "pawn" && board[possibleY][possibleX1].color != this.color) || (board[possibleY][possibleX2].piece == "pawn" && board[possibleY][possibleX2].color != this.color)) return true;
        return false;
    }

    //Checks if rook or queen is checking
    private boolean rookQcheck(Base[][] board, int kingX, int kingY){

        if (this.toRight(board, kingX, kingY) || this.toLeft(board, kingX, kingY) || this.toUp(board, kingX, kingY) || this.toDown(board, kingX, kingY)) return true;
        return false;
    }
    
    //Helper Function to rookQcheck() *Checks if there is rook or queen to right of king
    private boolean toRight(Base[][] board, int kingX, int kingY){

        while (kingX < 8){
            if (board[kingY][kingX] != null) {
                if (board[kingY][kingX].piece == "rook" && board[kingY][kingX].piece == "queen" && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX++;
        }
        return false;
    }
    
    //Helper Function to rookQcheck() *Checks if there is rook or queen to left of king
    private boolean toLeft(Base[][] board, int kingX, int kingY){

        while (kingX >= 0){
            if (board[kingY][kingX] != null) {
                if (board[kingY][kingX].piece == "rook" && board[kingY][kingX].piece == "queen" && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX--;            
        }
        return false;
    }

    //Helper Function to rookQcheck() *Checks if there is rook or queen to above of king
    private boolean toUp(Base[][] board, int kingX, int kingY){

        while (kingY >= 0){
            if (board[kingY][kingX] != null) {
                if (board[kingY][kingX].piece == "rook" && board[kingY][kingX].piece == "queen" && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingY--;
        }
        return false;
    }

    //Helper Function to rookQcheck() *Checks if there is rook or queen to bottom of king
    private boolean toDown(Base[][] board, int kingX, int kingY){
        
        while (kingY < 8){
            if (board[kingY][kingX] != null) {
                if (board[kingY][kingX].piece == "rook" && board[kingY][kingX].piece == "queen" && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingY++;
        }
        return false;
    }

    private boolean bishopQcheck(Base[][] board, int kingX, int kingY){
        return true;
    }


}
