package game;

public class Pawn extends Base{

    public Pawn(String color, int x, int y, String piece)
    {
        super(color, x, y, piece);
    }

    public boolean validMove(Board board, int newX, int newY) //NEED TO IMPLEMENT EN PEASSANT FIX Pond Color
    {   


      

        if (newY == this.y + 2 && this.x == newX && board.matrix[newY][newX] == null && board.matrix[this.y + 1][this.x] == null && this.x == 6) return true; //CHECKS for jumping twice up
        if (newY == this.y + 1 && this.x == newX && board.matrix[newY][newX] == null) return true;

        if (newY == this.y + 1 && newX == this.x + 1 && board.matrix[newY][newX] != null && board.matrix[newY][newX].color != this.color) return true;


        return false;
        
       
    }

    
}
