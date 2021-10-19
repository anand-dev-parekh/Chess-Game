package game;
import java.lang.Math;

public class King extends Base{

    public King(String color, int y, int x,  String piece, boolean hasMoved)
    {
        super(color, y, x, piece, hasMoved);
    }
    
    public boolean validMove(Board board, int newY, int newX)
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot

        int changeY = Math.abs(newY - y),  changeX = Math.abs(newX - x);

        if (board.matrix[newY][newX] != null && board.matrix[newY][newX].color == this.color) return false; //Checks to make sure piece isnt occupied by same color
        if ((changeY == 0 || changeY == 1) && (changeX == 1 || changeX == 0)) return true;    //Returns bubble around king if true

        //castling
        if (this.y == 7 || this.y == 0){

            if (newX == this.x + 2 || newX == this.x - 2){
                if (board.matrix[this.y][this.x + 3] != null && board.matrix[this.y][this.x + 3].hasMoved){
                    int iterator, distance;
                    if (newX > this.x){
                        distance = 2;
                        iterator = 1;
                    }
                    else{
                        distance = 3;
                        iterator = -1;
                    }
                    
                    for (int i = 0; i < distance; i++){
                        if (board.matrix[this.y][this.x + iterator] != null) return false;
                        i++;
                    }

                }
            }


        }



        return false;
    }
}
