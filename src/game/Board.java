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

        int[] daKing = this.findKing(color); // Finds king of said color

        int kingY = daKing[0], kingX = daKing[1]; //Puts kings indexes in variables

        if (!this.matrix[kingY][kingX].isCheck(this.matrix, kingY, kingX)) return false; // if king not being checked then it's not checkmate
        
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;
                //If king has a valid move return false, Since the king can move out of check.
                if (this.matrix[kingY][kingX].validMove(this, kingY + j, kingX + i) && !this.matrix[kingY][kingX].inCheck(this.matrix, kingY + j, kingX + i)) return false;
            }
        }
        //Now we have to do da hard part.

        return false;
    }   
    //HELPER FUNCTIONS FOR isCheckmate()
    private int[] findKing(String color){
        for (int i = 0; i < this.matrix.length; i++){ //Iterates all the colums/y value of board

            for (int j = 0; j < this.matrix[i].length; j++) //Iterates all x values for each column/y value
            {
                if (this.matrix[i][j] != null && this.matrix[i][j].piece == "king" && this.matrix[i][j].color == color){ //If u find the king return its position
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {0, 0};
    }



    //isDraw() Function
    public boolean isDraw (String color){
        if (this.isFiftyMove() || this.isRepetition() || this.isStalemate(color)) return true;
        return false;
    }

    //HELPER FUNCTIONS FOR isDraw()
    private boolean isStalemate (String color){
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
