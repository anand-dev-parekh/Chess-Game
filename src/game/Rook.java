package game;

public class Rook extends Base{
   

    public Rook(String color, int y, int x, String piece){
        super(color, y, x, piece);
    }

    @Override
    public boolean canMove(Board boardObject){
        int y = getY(), x = getX();

        //Directions to check
        int[][] iterators = {{y + 1, x}, {y - 1, x}, {y, x - 1}, {y, x + 1}};
        
        for (int i = 0; i < iterators.length; i++){
            
            if (inBounds(iterators[i][0], iterators[i][1])){
                //If piece can make move then return true
                if ((boardObject.matrix[iterators[i][0]][iterators[i][1]] == null || !boardObject.matrix[iterators[i][0]][iterators[i][1]].color.equals(this.color)) && !boardObject.matrix[y][x].isCheckAfterMove(boardObject, iterators[i][0], iterators[i][1])) return true;
       
            }        
        }

        return false;
    }

    @Override
    public boolean validMove(Board boardObject, int newY, int newX) //THIS IS THE VALID METHOD
    {
        int y = getY(), x = getX();

        //Cant move to same spot    
        if (x == newX && y == newY) return false; 

        //Checks that either x or y value is same.
        if (x != newX && y != newY) return false; 

        // Checks to make sure new position isnt occupied by piece of same color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false; 
        
        int xOffset = newX - x, yOffset = newY - y;


        for (int i = 1; i < Math.max(Math.abs(xOffset), Math.abs(yOffset)); i++)
        {    
            //xOffset or yOffset will be 0 so must use if statment for the incrementing
            if (xOffset != 0) x += xOffset/Math.abs(xOffset);      
            else y += yOffset/Math.abs(yOffset);                    
            
            
            //Returns false since piece blocking path
            if (boardObject.matrix[y][x] != null) return false;                  
        }
        return true;
    }
}