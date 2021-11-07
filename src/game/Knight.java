package game;

public class Knight extends Base{

    public Knight(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }

    @Override
    public boolean canMove(Board boardObject){
        //Jumps the Knights can make
        int[][] iterators = {{this.y - 2, this.x + 1},{this.y + 2, this.x + 1}, {this.y - 1, this.x + 2}, {this.y + 1, this.x + 2}, {this.y - 2, this.x - 1},{this.y + 2, this.x - 1}, {this.y - 1, this.x - 2}, {this.y + 1, this.x - 2}};
        
        for (int i = 0; i < iterators.length; i++){

            //If knight can make the move return true
            if (inBounds(iterators[i][0], iterators[i][1])){ 
                if ((boardObject.matrix[iterators[i][0]][iterators[i][1]] == null || !boardObject.matrix[iterators[i][0]][iterators[i][1]].color.equals(this.color)) && boardObject.matrix[this.y][this.x].inCheck(boardObject, iterators[i][0], iterators[i][1])) return true;
            }        

        }
        return false;
    }

    @Override
    public boolean validMove(Board boardObject, int newY, int newX)
    {
        if (this.x == newX && this.y == newY) return false; //Cant move to same spot

        int y = this.y, x = this.x;

        // Checks to make sure piece isnt capturing from its own color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false;

        // Uses distance formula to find knights move
        if ((newY - y) * (newY - y) + (newX - x) * (newX - x) == 5) return true;   

        return false;
    }
}
