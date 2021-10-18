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
        int changeX = Math.abs(this.x - newX);

        if (this.y == newY + decrement && changeX == 1){

            if (board.matrix[newY][newX] != null) return true; // Captures piece any direction.
            
            Base[][] previousBoard = board.prevBoards.get(board.prevBoards.size() - 2); // This gets the board from last move   

            //en pessant
            if (this.color == "white" && this.y == 3){
                if (previousBoard[1][newX].piece == "pawn" && board.matrix[this.y][newX].piece == "pawn" && board.matrix[1][newX] == null) return true;

            }
            else if (this.y == 4){
                if (previousBoard[6][newX].piece == "pawn" && board.matrix[this.y][newX].piece == "pawn" && board.matrix[6][newX] == null) return true;
            }
        }
        return false;  
    }    
}
