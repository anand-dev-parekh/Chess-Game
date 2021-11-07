package game;
import java.util.ArrayList;

public abstract class Base {
    //SETS the base attributes of all pieces
    final public String color;              
    public int x;
    public int y;
    final public String piece;
    public boolean enPessant;
    public boolean promotion;
    public boolean hasMoved;
    public boolean castle;

    public Base(String color, int y, int x, String piece){
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.enPessant = false;
        this.promotion = false;
        this.castle = false;
        this.hasMoved = false;
    }


    //Base method for all pieces 
    public abstract boolean validMove(Board boardObject, int y, int x);
    public abstract boolean canMove(Board boardObject);


    //Checks if coordanites are in bounds of the board
    protected boolean inBounds(int y, int x){
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;
        return false;
    }


    //If piece can block squares
    public boolean canBlock(ArrayList<ArrayList<int[]>> blockSquares, Board board){
        for (int i = 0; i < blockSquares.size();i++){ //Iterates through Blockable Squares
            if (blockSquares.get(i) != null){
                for (int j = 0; j < blockSquares.get(i).size(); j++){
                    // If piece can make a valid and non check move to block squares return true
                    if (board.matrix[this.y][this.x].validMove(board, blockSquares.get(i).get(j)[0], blockSquares.get(i).get(j)[1]) && !board.matrix[y][x].inCheck(board, blockSquares.get(i).get(j)[0], blockSquares.get(i).get(j)[1])) return true;
                }
            }
        }
        return false;

    }



    //Will check if king is in check after move has been played
    public boolean inCheck(Board boardObject, int newY, int newX){
        int x = this.x;
        int y = this.y;

        //If EnPessant remove piece that was enpessanted on temporary board
        Base tempEnPessant = new Pawn("white", 0, 0, "pawn");
        if (this.enPessant){ 
            if (this.color.equals("white")){
                tempEnPessant = boardObject.matrix[newY + 1][newX];
                boardObject.matrix[newY + 1][newX] = null;

            }
            else{
                tempEnPessant = boardObject.matrix[newY + 1][newX];
                boardObject.matrix[newY - 1][newX] = null;
            }
        }
        
        // If move was castle, We check three positions king could have been checked from.
        if (this.castle){ 
            int decrement = 1;
            if (newX < this.x) decrement = -1;
            return (this.isCheck(boardObject.matrix, this.y, this.x) || this.isCheck(boardObject.matrix, this.y, this.x + decrement) || this.isCheck(boardObject.matrix, this.y, this.x + decrement * 2));
        }


        //Rearranges the board temporarily
        boardObject.matrix[y][x] = null; 
        Base tempPiece = boardObject.matrix[newY][newX];
        boardObject.matrix[newY][newX] = this; 

        int[] daKing = boardObject.findKing(this.color); //Finds kings coordanites


        //Check all the possible ways the king could be checked
        boolean isCheck =  this.isCheck(boardObject.matrix, daKing[0], daKing[1]);

        //Reset the board
        boardObject.matrix[y][x] = this;
        boardObject.matrix[newY][newX] = tempPiece;
        if (this.enPessant){
            if (this.color.equals("white")) boardObject.matrix[newY + 1][newX] = tempEnPessant;
            else boardObject.matrix[newY - 1][newX] = tempEnPessant;
        }


        return isCheck;
    }

   
   
    //BELOW IS ALL HELPER FUNCTIONS FOR inCheck()


    public boolean isCheck(Base[][] board, int kingY, int kingX){
        if (this.knightCheck(board, kingY, kingX) || this.pawnCheck(board, kingY, kingX) || this.rookQBishopcheck(board, kingY, kingX) || this.kingCheck(board, kingY, kingX)) return true;

        return false;
    }


    //Checks if Knight is checking king
    private boolean knightCheck(Base[][] board, int kingY, int kingX){ 

        //All possible knight moves based from kings position
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){ //Checks all positions knight could be to check king
            
            if (inBounds(knightChecks[i][0], knightChecks[i][1])){ // If knight position is in board indexes range
                if (board[knightChecks[i][0]][knightChecks[i][1]] != null && board[knightChecks[i][0]][knightChecks[i][1]].piece.equals("knight") && board[knightChecks[i][0]][knightChecks[i][1]].color != this.color) return true; //returns true if piece is knight and oposite color
            }

        }
        return false;
    }


    //Checks if king is in new kings positions bubble
    private boolean kingCheck(Base[][] board, int kingY, int kingX){
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;
                //If king has a valid move return false, Since the king can move out of check.
                if (inBounds(kingY + i, kingX + j) && board[kingY + i][kingX + j] != null && board[kingY + i][kingX + j].piece.equals("king") && board[kingY + i][kingX + j].color != this.color) return true;
            }
        }

        return false;
    }    


    //Checks if pawn is Checking
    private boolean pawnCheck(Base[][] board, int kingY, int kingX){ 
        int possibleX1 = kingX - 1, possibleX2 = kingX + 1, possibleY;

        //King can only be checked by pond in front of it
        if (this.color == "white") possibleY = kingY - 1;            
        else possibleY = kingY + 1;                                 

        if (inBounds(possibleY, possibleX1) && board[possibleY][possibleX1] != null && board[possibleY][possibleX1].piece.equals("pawn") && !board[possibleY][possibleX1].color.equals(this.color)) return true;
        if (inBounds(possibleY, possibleX2) && board[possibleY][possibleX2] != null && board[possibleY][possibleX2].piece.equals("pawn") && !board[possibleY][possibleX2].color.equals(this.color)) return true;
        return false;
    }


    //Checks if rook queen and bishop are checking
    private boolean rookQBishopcheck(Base[][] board, int kingY, int kingX){
        int[][] iterators = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}}; //The directions of iterators from the king


        int tempKingX = kingX, tempKingY = kingY, kingXiterator, kingYiterator;
        for (int i = 0; i < iterators.length; i++){//Iterates through all the different directions

            kingYiterator = iterators[i][0];
            kingXiterator = iterators[i][1];
        
            String piece = "rook";
            if (Math.abs(kingXiterator) == 1 && Math.abs(kingYiterator) == 1) piece = "bishop"; //If iterators are both 1, then it is checking diagnol. Set piece to Bishop


            //Skips checking the kings position
            kingX = tempKingX + kingXiterator; 
            kingY = tempKingY + kingYiterator; 

            while (inBounds(kingY, kingX)){
                if (board[kingY][kingX] != null) {
                    if ((board[kingY][kingX].piece.equals(piece) || board[kingY][kingX].piece.equals("queen")) && board[kingY][kingX].color != this.color) return true;
                    break;
                }
                kingX += kingXiterator;
                kingY += kingYiterator;
            }
        }
        return false;
    }

}