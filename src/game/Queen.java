package game;

public class Queen extends Base{

    public Queen(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }

    @Override
    public boolean canMove(Board boardObject){
        int y = getY(), x = getX();

        //moves to check for queen
        int[][] iterators = {{y + 1, x}, {y - 1, x}, {y, x - 1}, {y, x + 1}, {y + 1, x + 1}, {y + 1, x - 1}, {y - 1, x - 1}, {y - 1, x + 1}};
        
        for (int i = 0; i < iterators.length; i++){
            
            if (inBounds(iterators[i][0], iterators[i][1])){
                //If queen can make a move return True
                if ((boardObject.matrix[iterators[i][0]][iterators[i][1]] == null || !boardObject.matrix[iterators[i][0]][iterators[i][1]].color.equals(this.color)) && !boardObject.matrix[y][x].isCheckAfterMove(boardObject, iterators[i][0], iterators[i][1])) return true;
       
            }
        }
        return false;
    }

    @Override
    public boolean validMove(Board boardObject, int newY, int newX){
        int y = getY(), x = getX();

        if (x == newX && y == newY) return false; //Cant move to same spot
        else if (x == newX || y == newY) return this.validRowColumn(boardObject.matrix, newY, newX);
        else return this.validDiagnols(boardObject.matrix, newY, newX);
        
    }

    private boolean validRowColumn(Base[][] board, int newY, int newX){ //This function checks the rows and columns for queen moves
        int y = getY(), x = getX();

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; // Checks to make sure new position isnt occupied by piece of same color
        
        int xOffset = newX - x, yOffset = newY - y;

        for (int i = 1; i < Math.max(Math.abs(xOffset), Math.abs(yOffset)); i++)
        {   
            if (xOffset != 0) x += xOffset/Math.abs(xOffset);       //xOffset or yOffset will be 0 so must use if statment for the incrementing
            else y += yOffset/Math.abs(yOffset);                    

            if (board[y][x] != null) return false;                  //Returns false since piece blocking path
        }
        return true;
    }

    private boolean validDiagnols(Base[][] board, int newY, int newX){
        int y = getY(), x = getX();
        int xOffset = newX - x, yOffset = newY - y;
        
        
        // Checks to make sure new position isnt occupied by piece of same color
        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; 
        
        //Checks to make sure piece is on SAME DIAGNOL
        if (Math.abs(yOffset) != Math.abs(xOffset)) return false; 

        for (int i = 1; i < Math.abs(xOffset); i++)
        {
            x += xOffset / Math.abs(xOffset);                    //This tells us what to increment/decrement the x and y by
            y += yOffset / Math.abs(yOffset);

            if (board[y][x] != null) return false;               //If there is something blocking the path return false
        }
        return true;                                             //Return True because there is nothing blocking the path
    }

}
