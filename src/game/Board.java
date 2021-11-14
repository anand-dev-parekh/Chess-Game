package game;
import java.util.ArrayList;


public class Board {

    public Base[][] matrix;
    public ArrayList<Base[][]> prevBoards;
    public int fiftyMove;
    public int earliestRepeatableBoard;
    public String turn;


    public Board(Base[][] matrix, ArrayList<Base[][]> prevBoards){
        this.matrix = matrix;
        this.prevBoards = prevBoards;
        this.fiftyMove = 0;
        this.earliestRepeatableBoard = 1;
        this.turn = "white";
    }


    public boolean isGameOver(){

        int[] daKing = this.findKing(this.turn); // Finds king of said colorz

        int kingY = daKing[0], kingX = daKing[1]; //Puts kings indexes in variables

        boolean isCheck = this.matrix[kingY][kingX].isCheck(this.matrix, kingY, kingX);  // Is king being checked

        if (this.isCheckmate(this.turn, kingY, kingX, isCheck) || this.isFiftyMove() || this.isRepetition() || this.isStalemate(this.turn, kingY, kingX, isCheck)) return true;

        return false;
    }


    private boolean isCheckmate (String color, int kingY, int kingX, boolean isCheck){

        if (!isCheck) return false;


        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;
                //If king has a valid move return false, Since the king can move out of check.
                if (inBounds(kingY + j, kingX + i) && this.matrix[kingY][kingX].validMove(this, kingY + j, kingX + i) && !this.matrix[kingY][kingX].isCheckAfterMove(this, kingY + j, kingX + i)) return false;
            }
        }

        ArrayList<ArrayList<int[]>> blockableCheckSquares = this.matrix[kingY][kingX].getBlockableSquares(this.matrix); //Gets squares you can block
       
        if (moreThanTwo(blockableCheckSquares)) return true; // If more than two pieces are checking then its check mate


        for (int y = 0; y < this.matrix.length; y++){       //Iterates all the colums/y value of board
            for (int x = 0; x < this.matrix[y].length; x++) //Iterates all x values for each column/y value
            {
                if (this.matrix[y][x] != null && this.matrix[y][x].color.equals(color) && !this.matrix[y][x].piece.equals("king")){ // Any piece of same color
                    if (this.matrix[y][x].canBlock(blockableCheckSquares, this)) return false; // Checks if the piece can block the check
                }   
            }
        }

        return true;
    }   


    private boolean isStalemate (String color, int kingY, int kingX, boolean isCheck){
        
        if (isCheck) return false; // If in check return false since it CANNOT be stalemate


        for (int y = 0; y < this.matrix.length; y++){       //Iterates all the colums/y value of board
            for (int x = 0; x < this.matrix[y].length; x++) //Iterates all x values for each column/y value
            {
                // If Piece of same color can move return false
                if (this.matrix[y][x] != null && this.matrix[y][x].color.equals(color) && this.matrix[y][x].canMove(this)) return false; 
            }
        }

        return true;
    }

    //checks for 3-fold repetition
    private boolean isRepetition(){
        int count = 0;

        for (int i = this.earliestRepeatableBoard; i < this.prevBoards.size() - 1; i++){
            if (arraysAreEqual(this.matrix, this.prevBoards.get(i))) count++;
            if (count == 2) return true;
        }
        
        return false;
    }

    //helper for repetition
    private boolean arraysAreEqual(Base[][] boardMatrix1, Base[][] boardMatrix2){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (boardMatrix1[y][x] == null && boardMatrix2[y][x] == null) continue;
                if (boardMatrix1[y][x] == null || boardMatrix2[y][x] == null) return false;
                if (!boardMatrix1[y][x].piece.equals(boardMatrix2[y][x].piece) || !boardMatrix1[y][x].color.equals(boardMatrix2[y][x].color)) return false;
            }
        }
        return true;
    }
    

    private boolean isFiftyMove(){
        //Checks 50 move rule
        if (this.fiftyMove > 50) return true; //i think this is supposed to be = 50 - akhil
        return false;
    }
    
    
    private boolean inBounds(int y, int x){ 
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;
        return false;
    }


    public int[] findKing(String color){
        for (int i = 0; i < this.matrix.length; i++){ //Iterates all the colums/y value of board

            for (int j = 0; j < this.matrix[i].length; j++) //Iterates all x values for each column/y value
            {
                if (this.matrix[i][j] != null && this.matrix[i][j].piece.equals("king") && this.matrix[i][j].color == color){ //If u find the king return its position
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {0, 0};
    }


    private boolean moreThanTwo(ArrayList<ArrayList<int[]>> checkSquares){ //Checks if there are more than two ways king is being checked
        int count = 0;
        for (int i = 0; i < checkSquares.size(); i++){
            if (checkSquares.get(i) != null) count++;
        }
        return count >= 2;
    }



    public void updateBoardObjectMatrix(int y, int x, int newY, int newX){
        if (this.matrix[newY][newX] == null && !this.matrix[y][x].piece.equals("pawn")) this.fiftyMove++; //Checks if no taking was done
        else {
            this.fiftyMove = 0; // Resets fifty move rule since there was a taking or pawn move
            this.earliestRepeatableBoard = this.prevBoards.size();
        }
        
        
        if (this.matrix[y][x].castle){ //if castle, move rook
            if (newX > x){ //if castling kingside
                this.matrix[y][x + 1] = this.matrix[y][x + 3]; //moves rook 2 square
                this.matrix[y][x + 3] = null; //sets prev rook square to null
                this.matrix[y][x + 1].x = x + 1; //updates x attribute for rook
            }
            else{ //if queenside - basically same thing happens but opposite side
                this.matrix[y][x - 1] = this.matrix[y][x - 4];
                this.matrix[y][x - 4] = null; 
                this.matrix[y][x - 1].x = x - 1;
            }
        }

        //update the attributes of da piece you moved
        this.matrix[y][x].x = newX; 
        this.matrix[y][x].y = newY; 

        //movin da piece on da board
        if (this.matrix[newY][newX] != null){
            if (this.matrix[newY][newX].pieceGUI != null) this.matrix[newY][newX].pieceGUI.boardGUI.destroyPiece(this.matrix[newY][newX].pieceGUI);
            else if (this.matrix[newY][newX].battlePieceGUI != null) this.matrix[newY][newX].battlePieceGUI.battleGUI.destroyPiece(this.matrix[newY][newX].battlePieceGUI, this.matrix[newY][newX].color);
        }

        this.matrix[newY][newX] = this.matrix[y][x];
        this.matrix[y][x] = null;
        
        //changes extra stuff if en pessant
        if (this.matrix[newY][newX].enPessant){
            if (this.matrix[newY][newX].color.equals("white")){

                if (this.matrix[newY + 1][newX].pieceGUI != null) this.matrix[newY + 1][newX].pieceGUI.boardGUI.destroyPiece(this.matrix[newY + 1][newX].pieceGUI);
                this.matrix[newY + 1][newX] = null;
            }
            else{     
                
                if (this.matrix[newY - 1][newX].pieceGUI != null) this.matrix[newY - 1][newX].pieceGUI.boardGUI.destroyPiece(this.matrix[newY - 1][newX].pieceGUI);
                this.matrix[newY - 1][newX] = null;

            }
            this.fiftyMove = 0; //Resets fifty move rule since en pessant
        }

        //if promotion
        if (this.matrix[newY][newX].promotion){
            System.out.println("Promotion Working");
        }

        // sets has moved for castling


        Base[][] tempBoardMatrix = new Base[8][8];
        for (int i = 0; i < this.matrix.length; i++){
            for (int j = 0; j < this.matrix[i].length; j++){
                tempBoardMatrix[i][j] = this.matrix[i][j]; 
            }
        }
        this.prevBoards.add(tempBoardMatrix); 

        if (this.turn.equals("white")) this.turn = "black";
        else this.turn = "white";
    }


    public void updateAttributesMoveWork(int newY, int newX){
        this.matrix[newY][newX].hasMoved = true; // Resets hasMoved
        this.matrix[newY][newX].promotion = false; //Resets promotions
        this.matrix[newY][newX].enPessant = false; // Resets enpessant to false
        this.matrix[newY][newX].castle = false; //Resets castling


    }
}
