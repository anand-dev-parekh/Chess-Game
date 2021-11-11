package game;

public class Pawn extends Base{

    public Pawn(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }


    @Override
    public boolean canMove(Board boardObject){
        int yChange = -1;
        if (this.color.equals("black")) yChange = 1;

        //if can move forward return true
        if (inBounds(this.y + yChange, this.x) && boardObject.matrix[this.y + yChange][this.x] == null && !boardObject.matrix[this.y][this.x].isCheckAfterMove(boardObject, this.y + yChange, this.x)) return true;
        
        //If can take right return true
        if (inBounds(this.y + yChange, this.x + 1) && boardObject.matrix[this.y + yChange][this.x + 1] != null && !boardObject.matrix[this.y + yChange][this.x + 1].color.equals(this.color) && !boardObject.matrix[this.y][this.x].isCheckAfterMove(boardObject, this.y + yChange, this.x + 1)) return true;
        
        //If can take left return true
        if (inBounds(this.y + yChange, this.x - 1) && boardObject.matrix[this.y + yChange][this.x - 1] != null && !boardObject.matrix[this.y + yChange][this.x - 1].color.equals(this.color) && !boardObject.matrix[this.y][this.x].isCheckAfterMove(boardObject, this.y + yChange, this.x - 1)) return true;

        return false;
    }


    @Override
    public boolean validMove(Board boardObject, int newY, int newX)
    {  
        // Checks if move is occupied by piece of same color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false; 

        int decrement = 1;
        if (this.color == "white"){
            decrement = -1;
        }

        // tests validity for single and double square forward moves
        if (this.x == newX && boardObject.matrix[newY][newX] == null){
            if (newY == this.y + 2*decrement){
                //Checks to make sure on 2nd rank or 7th rank. And piece in between is null for double jumping
                if ((this.y == 1 || this.y == 6) && boardObject.matrix[this.y + decrement][this.x] == null) return true;
            }
            // Checks 1 square movement forward
            else if (newY == this.y + decrement){
                if (newY == 7 || newY == 0) this.promotion = true;
                return true;
            }
            else return false;
        }
        
        // Diagonal taking + En Pessant      
        int changeX = Math.abs(this.x - newX);

        if (newY == this.y + decrement && changeX == 1){

            if (boardObject.matrix[newY][newX] != null){
                if (newY == 7 || newY == 0) this.promotion = true;
                return true; // Captures piece any direction.
            }
            
            if (boardObject.prevBoards.size() < 3) return false;
            Base[][] previousBoard = boardObject.prevBoards.get(boardObject.prevBoards.size() - 2); // This gets the board from last move   
            //en pessants
            if (this.color.equals("white") && this.y == 3){
                //Checks that pawn existed on second rank on old board, but not on new board.
                if (previousBoard[1][newX] != null && boardObject.matrix[this.y][newX] != null && boardObject.matrix[1][newX] == null){

                    if (previousBoard[1][newX].piece.equals("pawn") && boardObject.matrix[this.y][newX].piece.equals("pawn")){
                        boardObject.matrix[this.y][this.x].enPessant = true; //Sets En Pessant to True, so taking will work in inCheck() 
                        return true;
                    }
                }
            }
            else if (this.color.equals("black") && this.y == 4){
                if (previousBoard[6][newX] != null && boardObject.matrix[this.y][newX] != null){
                    
                    //Checks that pawn existed on seventh rank on old board, but not on new board.
                    if (previousBoard[6][newX].piece.equals("pawn") && boardObject.matrix[this.y][newX].piece.equals("pawn") && boardObject.matrix[6][newX] == null) {
                        boardObject.matrix[this.y][this.x].enPessant = true; //Sets En Pessant to True, so taking will work in inCheck()
                        return true;
                    }
                }
            }
        }
        return false;  
    }   
}
