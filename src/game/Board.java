package game;
import java.util.ArrayList;


public class Board {

    public Base[][] matrix;
    public ArrayList<Base[][]> prevBoards;
    public int fiftyMove;


    public Board(Base[][] matrix, ArrayList<Base[][]> prevBoards){
        this.matrix = matrix;
        this.prevBoards = prevBoards;
        this.fiftyMove = 0;
    }


    public boolean isGameOver(String color){

        int[] daKing = this.findKing(color); // Finds king of said colorz

        int kingY = daKing[0], kingX = daKing[1]; //Puts kings indexes in variables

        boolean isCheck = this.matrix[kingY][kingX].isCheck(this.matrix, kingY, kingX);  // Is king being checked

        if (this.isCheckmate(color, kingY, kingX, isCheck) || this.isFiftyMove() || this.isRepetition() || this.isStalemate(color, kingY, kingX, isCheck)) return true;

        return false;
    }


    private boolean isCheckmate (String color, int kingY, int kingX, boolean isCheck){

        if (!isCheck) return false;


        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;
                //If king has a valid move return false, Since the king can move out of check.
                if (inBounds(kingY + j, kingX + i) && this.matrix[kingY][kingX].validMove(this, kingY + j, kingX + i) && !this.matrix[kingY][kingX].inCheck(this, kingY + j, kingX + i)) return false;
            }
        }

        ArrayList<ArrayList<int[]>> blockableCheckSquares = getBlockableSquares(this.matrix, kingY, kingX, color); //Gets squares you can block
       
        if (moreThanTwo(blockableCheckSquares)) return true; // If more than two pieces are checking then its check mate


        for (int y = 0; y < this.matrix.length; y++){       //Iterates all the colums/y value of board
            for (int x = 0; x < this.matrix[y].length; x++) //Iterates all x values for each column/y value
            {
                if (this.matrix[y][x] != null && this.matrix[y][x].color.equals(color) && !this.matrix[y][x].piece.equals("king")){ // Any piece of same color
                    if (this.matrix[y][x].canBlock(blockableCheckSquares, this)) return false; // Checks if the piece can block the check
                }   
            }
        }

        return true;
    }   


    private boolean isStalemate (String color, int kingY, int kingX, boolean isCheck){
        
        if (isCheck) return false; // If in check return false since it CANNOT be stalemate


        for (int y = 0; y < this.matrix.length; y++){       //Iterates all the colums/y value of board
            for (int x = 0; x < this.matrix[y].length; x++) //Iterates all x values for each column/y value
            {
                // If Piece of same color can move return false
                if (this.matrix[y][x] != null && this.matrix[y][x].color.equals(color) && this.matrix[y][x].canMove(this)) return false; 
            }
        }

        return true;
    }

    //Unfinished
    private boolean isRepetition(){
        if (this.prevBoards.size() > 5)
            if (this.prevBoards.get(this.prevBoards.size() - 2) == this.matrix && this.matrix == this.prevBoards.get(this.prevBoards.size() - 4)) return true;

        return false;
    }
    

    private boolean isFiftyMove(){
        //Checks 50 move rule
        if (this.fiftyMove > 50) return true;
        return false;
    }
    
    
    private boolean inBounds(int y, int x){ 
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;
        return false;
    }


    public int[] findKing(String color){
        for (int i = 0; i < this.matrix.length; i++){ //Iterates all the colums/y value of board

            for (int j = 0; j < this.matrix[i].length; j++) //Iterates all x values for each column/y value
            {
                if (this.matrix[i][j] != null && this.matrix[i][j].piece.equals("king") && this.matrix[i][j].color == color){ //If u find the king return its position
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {0, 0};
    }

    

    //HELPER FUNCTIONS FOR isCheckmate()
    private ArrayList<ArrayList<int[]>> getBlockableSquares(Base[][] board, int kingY, int kingX, String color){
        ArrayList<ArrayList<int[]>> ayoh = new ArrayList<ArrayList<int[]>>(); //Blockable Squares
        
        int[][] lineIterators = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        for (int i = 0; i < lineIterators.length; i++){ 
            ayoh.add(addLines(board, kingY, kingX, lineIterators[i][0], lineIterators[i][1], color)); //Add all lines for bishop queen and rook
        }

        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};
        for (int i = 0; i < knightChecks.length; i++){
            ayoh.add(addKnightCheck(board, kingY, kingX, knightChecks[i][0], knightChecks[i][1], color));// Add all lines for knight
        }
        
        
        // Add pawn lines
        ayoh.add(addPawnRightCheck(board, kingY, kingX, color)); 
        ayoh.add(addPawnLeftCheck(board, kingY, kingX, color));


        return ayoh;
    }


    //Adds knight Check in ArrayList<int[]> Form
    private ArrayList<int[]> addKnightCheck(Base[][] board, int kingY, int kingX, int knightY, int knightX, String color){
        if (inBounds(knightY, knightX))
        {
            //Knight piece is in position to check king
            if (board[knightY][knightX] != null && board[knightY][knightX].piece.equals("knight") && board[knightY][knightX].color != color)
            {
                ArrayList<int[]> knightPos = new ArrayList<int[]>();
                int[] coordaintes = {knightY, knightX};
                knightPos.add(coordaintes);

                return knightPos;
            }
        }     
        return null;
    }


    //Adds bishop queen and rook checks in ArrayList<int[]> Form
    private ArrayList<int[]> addLines(Base[][] board, int kingY, int kingX, int decrementY, int decrementX, String color){
        ArrayList<int[]> output = new ArrayList<int[]>();
       
        // If decrementer is both 1 at abs val, then its on diagnol. Set piece to bishop.
        String piece = "rook";  
        if (Math.abs(decrementX) == 1 && Math.abs(decrementY) == 1) piece = "bishop"; 

        //Makes sure to skip checking the king for piece
        kingX += decrementX; 
        kingY += decrementY; 

        while (inBounds(kingY, kingX)){ // While in bounds of chess board

            int[] coords = {kingY, kingX}; 
            output.add(coords);         //Add coords leading up to first piece encounter
            
            //First piece encounter
            if (board[kingY][kingX] != null) { 

                //If encounter is opposite color and is checking king return ouput else return null
                if ((board[kingY][kingX].piece.equals(piece) || board[kingY][kingX].piece.equals("queen")) && board[kingY][kingX].color != color) return output;
                break;
            }
            kingX += decrementX;
            kingY += decrementY;
        }
        return null;
    }


    //Adds Pawn Left check in ArrayList<int[]> Form
    private ArrayList<int[]> addPawnLeftCheck(Base[][] board, int kingY, int kingX, String color){ 
        ArrayList<int[]> output = new ArrayList<int[]>();
        int possibleX1 = kingX - 1, possibleY;

        if (color.equals("white")) possibleY = kingY - 1;            //King can only be checked by pond in front of it
        else possibleY = kingY + 1;                                 //King can only be checked by pond in front of it


        //Check if there is pawn of opposite color checking on left
        if (inBounds(possibleY, possibleX1) && board[possibleY][possibleX1] != null && board[possibleY][possibleX1].piece.equals("pawn") && board[possibleY][possibleX1].color != color){
            int[] coords = {possibleY, possibleX1};
            output.add(coords);
        }

        return output;
    }


    //Adds Pawn Right check in ArrayList<int[]> Form
    private ArrayList<int[]> addPawnRightCheck(Base[][] board, int kingY, int kingX, String color){
        ArrayList<int[]> output = new ArrayList<int[]>();
        int possibleX2 = kingX + 1, possibleY;

        
        //King can only be checked by pond in front of it
        if (color.equals("white")) possibleY = kingY - 1;            
        else possibleY = kingY + 1;                                 


        //Check if there is pawn of opposite color checking on right
        if (inBounds(possibleY, possibleX2) && board[possibleY][possibleX2] != null && board[possibleY][possibleX2].piece.equals("pawn") && board[possibleY][possibleX2].color != color){
            int[] coords2 = {possibleY, possibleX2};
            output.add(coords2);
        }
        return output;
    }


    private boolean moreThanTwo(ArrayList<ArrayList<int[]>> checkSquares){ //Checks if there are more than two ways king is being checked
        int count = 0;
        for (int i = 0; i < checkSquares.size(); i++){
            if (checkSquares.get(i) != null) count++;
        }
        return count > 2;
    }



}
