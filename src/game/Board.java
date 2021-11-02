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



    public boolean isCheckmate (String color){

        int[] daKing = this.findKing(color); // Finds king of said colorz

        int kingY = daKing[0], kingX = daKing[1]; //Puts kings indexes in variables

        if (!this.matrix[kingY][kingX].isCheck(this.matrix, kingY, kingX)) return false; // if king not being checked then it's not checkmate
        
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;
                //If king has a valid move return false, Since the king can move out of check.
                if (inBounds(kingY + j, kingX + i) && this.matrix[kingY][kingX].validMove(this, kingY + j, kingX + i) && !this.matrix[kingY][kingX].inCheck(this.matrix, kingY + j, kingX + i)) return false;
            }
        }
        ArrayList<ArrayList<int[]>> checkSquares = getSquares(this.matrix, kingY, kingX, color); //Gets squares you can block

        if (moreThanTwo(checkSquares)) return true;


        for (int y = 0; y < this.matrix.length; y++){ //Iterates all the colums/y value of board

            for (int x = 0; x < this.matrix[y].length; x++) //Iterates all x values for each column/y value
            {
                if (this.matrix[y][x] != null && this.matrix[y][x].color.equals(color) && !this.matrix[y][x].piece.equals("king")){ // Any piece of same color
                    if (this.canBlock(checkSquares, y, x)) return false;
                }   
            }
        }



        return true;
    }   
    
    //HELPER FUNCTIONS FOR isCheckmate()
    private int[] findKing(String color){
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

    private ArrayList<ArrayList<int[]>> getSquares(Base[][] board, int kingY, int kingX, String color){
        ArrayList<ArrayList<int[]>> ayoh = new ArrayList<ArrayList<int[]>>();
        
        int[][] lineIterators = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        for (int i = 0; i < lineIterators.length; i++){
            ayoh.add(getLines(board, kingY, kingX, lineIterators[i][0], lineIterators[i][1], color));
        }

        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};
        for (int i = 0; i < knightChecks.length; i++){
            ayoh.add(knightCheck(board, kingY, kingX, knightChecks[i][0], knightChecks[i][1], color));
        }

        ayoh.add(pawnCheck(board, kingY, kingX, color));
        return ayoh;
    }

    private ArrayList<int[]> knightCheck(Base[][] board, int kingY, int kingX, int knightY, int knightX, String color){
        if (inBounds(knightY, knightX))
        {
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
    private ArrayList<int[]> getLines(Base[][] board, int kingY, int kingX, int decrementY, int decrementX, String color){
        ArrayList<int[]> output = new ArrayList<int[]>();
        String piece = "rook";
        if (Math.abs(decrementX) == 1 && Math.abs(decrementY) == 1) piece = "bishop";


        while (kingX < 8 && kingX >= 0 && kingY < 8 && kingY >= 0){
            int[] coords = {kingY, kingX};
            output.add(coords);
            if (board[kingY][kingX] != null) {
                if ((board[kingY][kingX].piece.equals(piece) || board[kingY][kingX].piece.equals("queen")) && board[kingY][kingX].color != color) return output;
                break;
            }
            kingX += decrementX;
            kingY += decrementY;
        }

        return null;
    }
    private ArrayList<int[]> pawnCheck(Base[][] board, int kingY, int kingX, String color){ 
        ArrayList<int[]> output = new ArrayList<int[]>();
        int possibleX1 = kingX - 1, possibleX2 = kingX + 1, possibleY;

        if (color == "white") possibleY = kingY - 1;            //King can only be checked by pond in front of it
        else possibleY = kingY + 1;                                 //King can only be checked by pond in front of it

        if (inBounds(possibleY, possibleX1) && board[possibleY][possibleX1] != null && board[possibleY][possibleX1].piece.equals("pawn") && board[possibleY][possibleX1].color != color){
            int[] coords = {possibleY, possibleX1};
            output.add(coords);
        }
        if (inBounds(possibleY, possibleX2) && board[possibleY][possibleX2] != null && board[possibleY][possibleX2].piece.equals("pawn") && board[possibleY][possibleX2].color != color){
            int[] coords2 = {possibleY, possibleX2};
            output.add(coords2);
        }
        return output;
    }

    private boolean inBounds(int y, int x){
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;
        return false;
    }

    private boolean moreThanTwo(ArrayList<ArrayList<int[]>> checkSquares){
        int count = 0;
        for (int i = 0; i < checkSquares.size(); i++){
            if (checkSquares.get(i) != null) count++;
        }
        return count >= 2;
    }
    private boolean canBlock(ArrayList<ArrayList<int[]>> blockSquares, int y, int x){
        for (int i = 0; i < blockSquares.size();i++){
            if (blockSquares.get(i) != null){
                for (int j = 0; j < blockSquares.get(i).size(); j++){
                    if (this.matrix[y][x].validMove(this, blockSquares.get(i).get(j)[0], blockSquares.get(i).get(j)[1]) && !this.matrix[y][x].inCheck(this.matrix, blockSquares.get(i).get(j)[0], blockSquares.get(i).get(j)[1])) return true;
                }
            }
        }
        return false;
    }  






    //isDraw() Function
    public boolean isDraw (String color){
        if (this.isFiftyMove() || this.isRepetition() || this.isStalemate(color)) return true;
        return false;
    }

    //HELPER FUNCTIONS FOR isDraw()
    private boolean isStalemate (String color){
        if (this.prevBoards.size() > 5)
            if (this.prevBoards.get(this.prevBoards.size() - 2) == this.matrix && this.matrix == this.prevBoards.get(this.prevBoards.size() - 4)) return true;
            
            return false;
    }

    private boolean isRepetition(){
        return false;
    }
    
    private boolean isFiftyMove(){
        if (this.fiftyMove > 50) return true;
        return false;
    }

}
