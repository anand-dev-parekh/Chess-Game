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

        ArrayList<ArrayList<int[]>> blockableCheckSquares = this.matrix[kingY][kingX].getBlockableSquares(this.matrix); //Gets squares you can block
       

        System.out.println(moreThanTwo(blockableCheckSquares));
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


    private boolean moreThanTwo(ArrayList<ArrayList<int[]>> checkSquares){ //Checks if there are more than two ways king is being checked
        int count = 0;
        for (int i = 0; i < checkSquares.size(); i++){
            if (checkSquares.get(i) != null) {
                count++;
            }
        }
        System.out.println(count);
        return count >= 2;
    }



}
