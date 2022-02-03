package game;

public class Knight extends Base{

    public Knight(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }

    @Override
    public boolean canMove(Board boardObject){
        int y = getY(), x = getX();

        //Jumps the Knights can make
        int[][] iterators = {{y - 2, x + 1},{y + 2, x + 1}, {y - 1, x + 2}, {y + 1, x + 2}, {y - 2, x - 1},{y + 2, x - 1}, {y - 1, x - 2}, {y + 1, x - 2}};
        
        for (int i = 0; i < iterators.length; i++){

            //If knight can make the move return true
            if (inBounds(iterators[i][0], iterators[i][1])){ 
                if ((boardObject.matrix[iterators[i][0]][iterators[i][1]] == null || !boardObject.matrix[iterators[i][0]][iterators[i][1]].color.equals(this.color)) && !boardObject.matrix[y][x].isCheckAfterMove(boardObject, iterators[i][0], iterators[i][1])) return true;
            }        

        }
        return false;
    }

    @Override
    public boolean validMove(Board boardObject, int newY, int newX)
    {        
        int y = getY(), x = getX();

        if (x == newX && y == newY) return false; //Cant move to same spot

        // Checks to make sure piece isnt capturing from its own color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false;

        // Uses distance formula to find knights move
        if ((newY - y) * (newY - y) + (newX - x) * (newX - x) == 5) return true;   

        return false;
    }
}
