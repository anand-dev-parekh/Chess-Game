package game;
import java.lang.Math;

public class King extends Base{

    public King(String color, int y, int x,  String piece)
    {
        super(color, y, x, piece);
    }

    
    @Override
    public boolean canMove(Board boardObject){
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;

                //If king has a valid move return true, since king can move.
                if (inBounds(this.y + i, this.x + j) && (boardObject.matrix[this.y + i][this.x + j] == null || boardObject.matrix[this.y + i][this.x + j].color != this.color) && !boardObject.matrix[this.y][this.x].isCheckAfterMove(boardObject, this.y + i, this.x + j)) return true;
            
            }
        }
        return false;
    }

    @Override
    public boolean validMove(Board boardObject, int newY, int newX)
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot

        int changeY = Math.abs(newY - y),  changeX = Math.abs(newX - x);

        //Checks to make sure piece isnt occupied by same color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false; 

         //Returns bubble around king if true
        if ((changeY == 0 || changeY == 1) && (changeX == 1 || changeX == 0)) return true;   

        //castling
        if ((this.y == 7 || this.y == 0) && !boardObject.matrix[this.y][this.x].hasMoved){ //Checks that its on second to top and bottom rank


            int iterator, distance; // Declares which way u have to check, and distance to check null

            //if castle right side
            if (newX == this.x + 2 && boardObject.matrix[this.y][this.x + 3] != null && !boardObject.matrix[this.y][this.x + 3].hasMoved){
                iterator = 1;
                distance = 3;
            }
            //Else if castle left side
            else if (newX == this.x - 2 && (boardObject.matrix[this.y][this.x - 4] != null && !boardObject.matrix[this.y][this.x - 4].hasMoved)){
                iterator = -1;
                distance = 4;
            }
            else return false;

            for (int i = 1; i < distance; i++){ //Iterates the pieces in between castling
                // If piece in between castle return false
                if (boardObject.matrix[this.y][this.x + iterator] != null) return false;
            }

            //Sets castle attribute to true so the inCheck() function will be altered for one move
            boardObject.matrix[this.y][this.x].castle = true;
            return true;
            
        }
        return false;
    }
}
