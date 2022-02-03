package game;

public class Pawn extends Base{

    public Pawn(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }


    @Override
    public boolean canMove(Board boardObject){
        int y = getY(), x = getX();

        int yChange = -1;
        if (this.color.equals("black")) yChange = 1;

        //if can move forward return true
        if (inBounds(y + yChange, x) && boardObject.matrix[y + yChange][x] == null && !boardObject.matrix[y][x].isCheckAfterMove(boardObject, y + yChange, x)) return true;
        
        //If can take right return true
        if (inBounds(y + yChange, x + 1) && boardObject.matrix[y + yChange][x + 1] != null && !boardObject.matrix[y + yChange][x + 1].color.equals(this.color) && !boardObject.matrix[y][x].isCheckAfterMove(boardObject, y + yChange, x + 1)) return true;
        
        //If can take left return true
        if (inBounds(y + yChange, x - 1) && boardObject.matrix[y + yChange][x - 1] != null && !boardObject.matrix[y + yChange][x - 1].color.equals(this.color) && !boardObject.matrix[y][x].isCheckAfterMove(boardObject, y + yChange, x - 1)) return true;

        return false;
    }


    @Override
    public boolean validMove(Board boardObject, int newY, int newX)
    {
        int y = getY(), x = getX();

        // Checks if move is occupied by piece of same color
        if (boardObject.matrix[newY][newX] != null && boardObject.matrix[newY][newX].color == this.color) return false; 

        int decrement = 1;
        if (this.color == "white"){
            decrement = -1;
        }

        // tests validity for single and double square forward moves
        if (x == newX && boardObject.matrix[newY][newX] == null){
            if (newY == y + 2*decrement){
                //Checks to make sure on 2nd rank or 7th rank. And piece in between is null for double jumping
                if ((y == 1 || y == 6) && boardObject.matrix[y + decrement][x] == null) return true;
            }
            // Checks 1 square movement forward
            else if (newY == y + decrement){
                if (newY == 7 || newY == 0) this.promotion = true;
                return true;
            }
            else return false;
        }
        
        // Diagonal taking + En Pessant      
        int changeX = Math.abs(x - newX);

        if (newY == y + decrement && changeX == 1){

            if (boardObject.matrix[newY][newX] != null){
                if (newY == 7 || newY == 0) this.promotion = true;
                return true; // Captures piece any direction.
            }
            
            if (boardObject.prevBoards.size() < 3) return false;
            Base[][] previousBoard = boardObject.prevBoards.get(boardObject.prevBoards.size() - 2); // This gets the board from last move   
            //en pessants
            if (this.color.equals("white") && y == 3){
                //Checks that pawn existed on second rank on old board, but not on new board.
                if (previousBoard[1][newX] != null && boardObject.matrix[y][newX] != null && boardObject.matrix[1][newX] == null){

                    if (previousBoard[1][newX].piece.equals("pawn") && boardObject.matrix[y][newX].piece.equals("pawn")){
                        boardObject.matrix[y][x].enPessant = true; //Sets En Pessant to True, so taking will work in inCheck() 
                        return true;
                    }
                }
            }
            else if (this.color.equals("black") && y == 4){
                if (previousBoard[6][newX] != null && boardObject.matrix[y][newX] != null){
                    
                    //Checks that pawn existed on seventh rank on old board, but not on new board.
                    if (previousBoard[6][newX].piece.equals("pawn") && boardObject.matrix[y][newX].piece.equals("pawn") && boardObject.matrix[6][newX] == null) {
                        boardObject.matrix[y][x].enPessant = true; //Sets En Pessant to True, so taking will work in inCheck()
                        return true;
                    }
                }
            }
        }
        return false;  
    }   
}
