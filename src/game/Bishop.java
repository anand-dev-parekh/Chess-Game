package game;

public class Bishop extends Base{

    public Bishop(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }
    
    
    @Override
    public boolean canMove(Board boardObject){

        //Directions to check for making move
        int[][] iterators = {{this.y + 1, this.x + 1}, {this.y + 1, this.x - 1}, {this.y - 1, this.x - 1}, {this.y - 1, this.x + 1}};

        for (int i = 0; i < iterators.length; i++){

            if (inBounds(iterators[i][0], iterators[i][1])){
                //If bishop can make move return true
                if ((boardObject.matrix[iterators[i][0]][iterators[i][1]] == null || !boardObject.matrix[iterators[i][0]][iterators[i][1]].color.equals(this.color)) && boardObject.matrix[this.y][this.x].inCheck(boardObject, iterators[i][0], iterators[i][1])) return true;
            }        
        }

        return false;
    }


    @Override
    public boolean validMove(Board boardObject, int newY, int newX){
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot
        
        int xOffset = newX - x, yOffset = newY - y;
        
        //Checks to make sure piece is on SAME DIAGNOL
        if (Math.abs(yOffset) != Math.abs(xOffset)) return false; 
        
        // Checks to make sure new position isnt occupied by piece of same color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false; 

        int x = this.x, y = this.y;                                     

        for (int i = 1; i < Math.abs(xOffset); i++)
        {
            //This tells us what to increment/decrement the x and y by
            x += xOffset / Math.abs(xOffset);                    
            y += yOffset / Math.abs(yOffset);

            //If there is something blocking the path return false
            if (boardObject.matrix[y][x] != null) return false;               
        }
        return true;                                             //Return True because there is nothing blocking the path
    }
}

