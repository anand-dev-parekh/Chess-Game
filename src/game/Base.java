package game;

public abstract class Base {
    //SETS the base attributes of all pieces
    final public String color;              
    public int x;
    public int y;
    public String piece;
    public boolean enPessant;
    public boolean promotion;
    public boolean hasMoved;
    public boolean castle;

    public Base(String color, int y, int x, String piece, boolean enPessant, boolean promotion){
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.enPessant = enPessant;
        this.promotion = promotion;
        this.castle = promotion;
    }

    public Base(String color, int y, int x, String piece){
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }
    public Base(String color, int y, int x, String piece, boolean hasMoved){
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.hasMoved = hasMoved;
    }


    //Base method for all pieces 
    public abstract boolean validMove(Board board, int y, int x);

    public boolean inCheck(Base[][] tempBoard, int newY, int newX){ //Will check if king is in check after move has been played
        int x = this.x;
        int y = this.y;

        Base[][] board = new Base[8][8];
        
        for (int i = 0; i < tempBoard.length; i++){
            for (int j = 0; j < tempBoard[i].length; j++){
                board[i][j] = tempBoard[i][j]; 
            }
        }

        if (this.enPessant){ //If EnPessant remove piece that was enpessanted on temporary board
            if (this.color == "white"){
                board[newY + 1][newX] = null;
            }
            else{
                board[newY - 1][newX] = null;
            }
        }
        if (this.castle){
            int decrement = 1;
            if (newX < this.x) decrement = -1;
            return (this.isCheck(board, this.y, this.x) || this.isCheck(board, this.y, this.x + decrement) || this.isCheck(board, this.y, this.x + decrement * 2));
        }


        board[y][x] = null;
        board[newY][newX] = this;

        int[] daKing = this.findKing(board);

        //Check all the possible ways the king could be checked
        return this.isCheck(board, daKing[0], daKing[1]);
    }

    /*          BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
                BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
                BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
                BELOW IS ALL HELPER FUNCTIONS FOR THE InCheck() function
    */

    private boolean isCheck(Base[][] board, int kingY, int kingX){
        if (this.knightCheck(board, kingY, kingX) || this.pawnCheck(board, kingY, kingX) || this.rookQcheck(board, kingY, kingX) || this.bishopQcheck(board, kingY, kingX)) return true;

        return false;
    }


    //finds kings position
    private int[] findKing(Base[][] board){
        for (int i = 0; i < board.length; i++){ //Iterates all the colums/y value of board

            for (int j = 0; j < board[i].length; j++) //Iterates all x values for each column/y value
            {
                if (board[i][j] != null && board[i][j].piece == "king" && board[i][j].color == this.color){ //If u find the king return its position
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {0, 0};
    }

    //Checks if Knight is checking king
    private boolean knightCheck(Base[][] board, int kingY, int kingX){ 

        //All possible knight moves based from kings position
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){ //Checks all positions knight could be to check king
            
            if (inBounds(knightChecks[i][0], knightChecks[i][1])){ // If knight position is in board indexes range
                if (board[knightChecks[i][0]][knightChecks[i][1]] != null && board[knightChecks[i][0]][knightChecks[i][1]].piece == "knight" && board[knightChecks[i][0]][knightChecks[i][1]].color != this.color) return true; //returns true if piece is knight and oposite color
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
    private boolean pawnCheck(Base[][] board, int kingY, int kingX){ 
        int possibleX1 = kingX - 1, possibleX2 = kingX + 1, possibleY;

        if (this.color == "white") possibleY = kingY - 1;            //King can only be checked by pond in front of it
        else possibleY = kingY + 1;                                 //King can only be checked by pond in front of it

        if (board[possibleY][possibleX1] != null && ((board[possibleY][possibleX1].piece == "pawn" && board[possibleY][possibleX1].color != this.color) || board[possibleY][possibleX2] != null && (board[possibleY][possibleX2].piece == "pawn" && board[possibleY][possibleX2].color != this.color))) return true;
        return false;
    }

    //Checks if rook or queen is checking
    private boolean rookQcheck(Base[][] board, int kingY, int kingX){

        if (this.toRight(board, kingY, kingX) || this.toLeft(board, kingY, kingX) || this.toUp(board, kingY, kingX) || this.toDown(board, kingY, kingX)) return true;
        return false;
    }
    
    //Helper Function to rookQcheck() *Checks if there is rook or queen to right of king
    private boolean toRight(Base[][] board, int kingY, int kingX){
        kingX++;
        while (kingX < 8){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "rook" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX++;
        }
        return false;
    }
    
    //Helper Function to rookQcheck() *Checks if there is rook or queen to left of king
    private boolean toLeft(Base[][] board, int kingY, int kingX){
        kingX--;
        while (kingX >= 0){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "rook" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX--;            
        }
        return false;
    }

    //Helper Function to rookQcheck() *Checks if there is rook or queen to above of king
    private boolean toUp(Base[][] board, int kingY, int kingX){
        kingY--;
        while (kingY >= 0){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "rook" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingY--;
        }
        return false;
    }

    //Helper Function to rookQcheck() *Checks if there is rook or queen to bottom of king
    private boolean toDown(Base[][] board, int kingY, int kingX){
        kingY++;
        while (kingY < 8){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "rook" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingY++;
        }
        return false;
    }

    //Checks if bishop or queen is checking king
    private boolean bishopQcheck(Base[][] board, int kingY, int kingX){
        if (this.toTopRight(board, kingY, kingX) || this.toBottomRight(board, kingY, kingX) || this.toTopLeft(board, kingY, kingX) || this.toBottomLeft(board, kingY, kingX)) return true;
        return false;
    }

    //Helper function to bishoQcheck() *Checks if bishop or queen is to top right of king
    private boolean toTopRight(Base[][] board, int kingY, int kingX){
        kingX++; kingY--;
        while (kingX < 8 && kingY >= 0){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "bishop" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX++; kingY--;
        }
        return false;
    }

    //Helper function to bishoQcheck() *Checks if bishop or queen is to bottom right of king
    private boolean toBottomRight(Base[][] board, int kingY, int kingX){
        kingX++; kingY++;
        while (kingX < 8 && kingY < 8){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "bishop" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX++; kingY++;
        }
        return false;
    }

    //Helper function to bishoQcheck() *Checks if bishop or queen is to top left of king
    private boolean toTopLeft(Base[][] board, int kingY, int kingX){
        kingX--; kingY--;
        while (kingX >= 0 && kingY >= 0){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "bishop" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX--; kingY--;
        }
        return false;
    }

    //Helper function to bishoQcheck() *Checks if bishop or queen is to top right of king
    private boolean toBottomLeft(Base[][] board, int kingY, int kingX){
        kingX--; kingY++;
        while (kingX > 0 && kingY < 8){
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece == "bishop" || board[kingY][kingX].piece == "queen") && board[kingY][kingX].color != this.color) return true;
                return false;
            }
            kingX--; kingY++;
        }
        return false;
    }


}
