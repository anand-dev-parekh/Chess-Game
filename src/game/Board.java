package game;
import java.util.ArrayList;


public class Board {

    public Base[][] matrix;
    public ArrayList<Base[][]> prevBoards;

    public Board(Base[][] matrix, ArrayList<Base[][]> prevBoards){
        this.matrix = matrix;
        this.prevBoards = prevBoards;
    }



    public boolean isCheckmate (String color, int kingY, int kingX){

        int[] daKing = this.findKing(board);

        if (!this.matrix[kingY][kingX].isCheck(this.matrix, kingY, kingX)) return false; // if king not being checked then it's not checkmate
        
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;

                if (this.matrix[kingY][kingX].validMove(this.matrix, kingY + j, kingX + i) && )
            }
        }

        return false;
    }   

    private int[] findking(){

    }

    public boolean isDraw (String color){

    }

    //HELPER FUNCTIONS FOR isDraw()
    private boolean isStatemate (String color){

    }

    private boolean isRepetition(){

    }
    
    private boolean isFiftyMove(int count){

    }

}
