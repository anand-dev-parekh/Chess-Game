package game;

public class Pawn extends Base{

    public Pawn(String color, int x, int y, String piece)
    {
        super(color, x, y, piece);
    }

    public boolean validMove(Board board, int newX, int newY)
    {  
        
        if (board.matrix[newY][newX] != null && board.matrix[newY][newX].color == this.color) return false; // Checks if move is occupied by piece of same color

        int decrement = 1;
        if (this.color == "white"){
            decrement = -1;
        }

        // tests validity for single and double square forward moves
        if (this.x == newX && board.matrix[newY][newX] == null){
            if (newY == this.y + 2*decrement){
                if ((this.y == 1 || this.y == 7) && board.matrix[newY + decrement][this.x] == null) return true;
            }
            else if (newY == this.y + decrement) return true;
            else return false;
        }
        
        // Diagonal taking + En Pessant [NOT DONE]
        int prevX = board.prevMoves.get(1); // Gets the previous moves coordanites
        int prevY = board.prevMoves.get(0);

        if (newX == this.x - 1){  // Checks that moved to left

            if(board.matrix[newY][newX] != null) return true; // Checks for taking on left
            if (this.y == prevY && this.x - 1 == prevX && board.matrix[prevY][prevX].piece == "pawn") return true; //Checks En peasant left
        }
        if (newX == this.x + 1){ // Checks that moved to right
            
            if (board.matrix[newY][newX] != null) return true; // Checks for taking on right
            if (this.y == prevY && this.x + 1 == prevX && board.matrix[prevY][prevX].piece == "pawn") return true; //Checks En peasant right
        }

        return false;  
    }    
}
