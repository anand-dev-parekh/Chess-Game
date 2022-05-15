package game;
import java.util.ArrayList;

import ChessGUIObjects.BasePieceGUI;

public abstract class Base {

    //declares the base attributes of all pieces
    final public String color;              
    private int x;
    private int y;
    final public String piece;

    public boolean enPessant = false;
    public boolean promotion = false;
    public boolean hasMoved = false;
    public boolean castle = false;

    public BasePieceGUI basePieceGUI = null;

    public Base(String color, int y, int x, String piece){
        this.color = color;
        this.x = x;
        this.y = y;
        this.piece = piece;
    }


    //Base method for all pieces 
    public abstract boolean validMove(Board boardObject, int y, int x);
    public abstract boolean canMove(Board boardObject);

    //Getters and setters of coordanites
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }


    //Checks if coordanites are in bounds of the board
    protected boolean inBounds(int y, int x){
        if (x >= 0 && x <= 7 && y >=0 && y <= 7) return true;
        return false;
    }


    //If piece can block squares
    public boolean canBlock(ArrayList<ArrayList<int[]>> blockSquares, Board board){
        int y = getY(), x = getX();
        for (int i = 0; i < blockSquares.size();i++){ //Iterates through Blockable Squares
            if (blockSquares.get(i) != null){
                for (int j = 0; j < blockSquares.get(i).size(); j++){

                    //System.out.println(y + " " + x);
                    // If piece can make a valid and non check move to block squares return true
                    if (board.matrix[y][x].validMove(board, blockSquares.get(i).get(j)[0], blockSquares.get(i).get(j)[1]) && !board.matrix[y][x].isCheckAfterMove(board, blockSquares.get(i).get(j)[0], blockSquares.get(i).get(j)[1])) return true;
                }
            }
        }
        return false;
    }



    //Will check if king is in check after move has been played
    public boolean isCheckAfterMove(Board boardObject, int newY, int newX){
        int y = getY(), x = getX();

        //If EnPessant remove piece that was enpessanted on temporary board
        Base tempEnPessant = null;
        if (this.enPessant){ 
            if (this.color.equals("white")){
                tempEnPessant = boardObject.matrix[newY + 1][newX];
                boardObject.matrix[newY + 1][newX] = null;

            }
            else{
                tempEnPessant = boardObject.matrix[newY - 1][newX];
                boardObject.matrix[newY - 1][newX] = null;
            }
        }
        
        // If move was castle, We check three positions king could have been checked from.
        if (this.castle){ 
            int decrement = 1;
            if (newX < x) decrement = -1;
            return (this.isCheck(boardObject.matrix, y, x) || this.isCheck(boardObject.matrix, y, x + decrement) || this.isCheck(boardObject.matrix, y, x + decrement * 2));
        }


        //Rearranges the board temporarily
        boardObject.matrix[y][x] = null; 
        Base tempPiece = boardObject.matrix[newY][newX];
        boardObject.matrix[newY][newX] = this; 

        int[] daKing = boardObject.findKing(this.color); //Finds kings coordanites


        //Check all the possible ways the king could be checked
        boolean isCheck =  this.isCheck(boardObject.matrix, daKing[0], daKing[1]);

        //Reset the board
        boardObject.matrix[y][x] = this;
        boardObject.matrix[newY][newX] = tempPiece;
        if (this.enPessant){
            if (this.color.equals("white")) boardObject.matrix[newY + 1][newX] = tempEnPessant;
            else boardObject.matrix[newY - 1][newX] = tempEnPessant;
        }


        return isCheck;
    }

   
   
    //BELOW IS ALL HELPER FUNCTIONS FOR inCheck()


    public boolean isCheck(Base[][] boardMatrix, int kingY, int kingX){
        if (this.knightCheck(boardMatrix, kingY, kingX) || this.pawnCheck(boardMatrix, kingY, kingX) || this.rookQBishopcheck(boardMatrix, kingY, kingX) || this.kingCheck(boardMatrix, kingY, kingX)) return true;

        return false;
    }


    //Checks if Knight is checking king
    private boolean knightCheck(Base[][] boardMatrix, int kingY, int kingX){ 

        //All possible knight moves based from kings position
        int[][] knightChecks = {{kingY - 2, kingX + 1},{kingY + 2, kingX + 1}, {kingY - 1, kingX + 2}, {kingY + 1, kingX + 2}, {kingY - 2, kingX - 1},{kingY + 2, kingX - 1}, {kingY - 1, kingX - 2}, {kingY + 1, kingX - 2}};

        for (int i = 0; i < knightChecks.length; i++){ //Checks all positions knight could be to check king
            
            if (inBounds(knightChecks[i][0], knightChecks[i][1])){ // If knight position is in board indexes range
                if (boardMatrix[knightChecks[i][0]][knightChecks[i][1]] != null && boardMatrix[knightChecks[i][0]][knightChecks[i][1]].piece.equals("knight") && !boardMatrix[knightChecks[i][0]][knightChecks[i][1]].color.equals(this.color)) return true; //returns true if piece is knight and oposite color
            }

        }
        return false;
    }


    //Checks if king is in new kings positions bubble
    private boolean kingCheck(Base[][] boardMatrix, int kingY, int kingX){
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                if (i == 0 && j == 0) continue;
                //If king has a valid move return false, Since the king can move out of check.
                if (inBounds(kingY + i, kingX + j) && boardMatrix[kingY + i][kingX + j] != null && boardMatrix[kingY + i][kingX + j].piece.equals("king") && boardMatrix[kingY + i][kingX + j].color != this.color) return true;
            }
        }

        return false;
    }    


    //Checks if pawn is Checking
    private boolean pawnCheck(Base[][] boardMatrix, int kingY, int kingX){ 
        int possibleX1 = kingX - 1, possibleX2 = kingX + 1, possibleY;

        //King can only be checked by pond in front of it
        if (this.color == "white") possibleY = kingY - 1;            
        else possibleY = kingY + 1;                                 

        if (inBounds(possibleY, possibleX1) && boardMatrix[possibleY][possibleX1] != null && boardMatrix[possibleY][possibleX1].piece.equals("pawn") && !boardMatrix[possibleY][possibleX1].color.equals(this.color)) return true;
        if (inBounds(possibleY, possibleX2) && boardMatrix[possibleY][possibleX2] != null && boardMatrix[possibleY][possibleX2].piece.equals("pawn") && !boardMatrix[possibleY][possibleX2].color.equals(this.color)) return true;
        return false;
    }


    //Checks if rook queen and bishop are checking
    private boolean rookQBishopcheck(Base[][] boardMatrix, int kingY, int kingX){
        int[][] iterators = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}}; //The directions of iterators from the king


        int tempKingX = kingX, tempKingY = kingY, kingXiterator, kingYiterator;
        for (int i = 0; i < iterators.length; i++){//Iterates through all the different directions

            kingYiterator = iterators[i][0];
            kingXiterator = iterators[i][1];
        
            String piece = "rook";
            if (Math.abs(kingXiterator) == 1 && Math.abs(kingYiterator) == 1) piece = "bishop"; //If iterators are both 1, then it is checking diagnol. Set piece to Bishop


            //Skips checking the kings position
            kingX = tempKingX + kingXiterator; 
            kingY = tempKingY + kingYiterator; 

            while (inBounds(kingY, kingX)){
                if (boardMatrix[kingY][kingX] != null) {
                    if ((boardMatrix[kingY][kingX].piece.equals(piece) || boardMatrix[kingY][kingX].piece.equals("queen")) && !boardMatrix[kingY][kingX].color.equals(this.color)) return true;
                    break;
                }
                kingX += kingXiterator;
                kingY += kingYiterator;
            }
        }
        return false;
    }


    //Gets squares pieces can move to to block checkmate
    public ArrayList<ArrayList<int[]>> getBlockableSquares(Base[][] boardMatrix){
        int y = getY(), x = getX();

        ArrayList<ArrayList<int[]>> ayoh = new ArrayList<ArrayList<int[]>>(); //Blockable Squares

        int[][] lineIterators = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        for (int i = 0; i < lineIterators.length; i++){ 
            ayoh.add(this.addLines(boardMatrix, lineIterators[i][0], lineIterators[i][1])); //Add all lines for bishop queen and rook
        }

        int[][] knightChecks = {{y - 2, x + 1},{y + 2, x + 1}, {y - 1, x + 2}, {y + 1, x + 2}, {y - 2, x - 1},{y + 2, x - 1}, {y - 1, x - 2}, {y + 1, x - 2}};
        for (int i = 0; i < knightChecks.length; i++){
            ayoh.add(this.addKnightCheck(boardMatrix, knightChecks[i][0], knightChecks[i][1]));// Add all lines for knight
        }
        
        // Add pawn lines
        ayoh.add(addPawnRightCheck(boardMatrix)); 
        ayoh.add(addPawnLeftCheck(boardMatrix));


        return ayoh;
    }


    //Adds knight Check in ArrayList<int[]> Form
    private ArrayList<int[]> addKnightCheck(Base[][] boardMatrix, int knightY, int knightX){
        if (inBounds(knightY, knightX))
        {
            //Knight piece is in position to check king
            if (boardMatrix[knightY][knightX] != null && boardMatrix[knightY][knightX].piece.equals("knight") && !boardMatrix[knightY][knightX].color.equals(this.color))
            {
                ArrayList<int[]> knightPos = new ArrayList<int[]>();
                int[] coordaintes = {knightY, knightX};
                knightPos.add(coordaintes);

                return knightPos;
            }
        }     

        return null;
    }


    //Adds bishop queen and rook checks in ArrayList<int[]> Form
    private ArrayList<int[]> addLines(Base[][] boardMatrix, int decrementY, int decrementX){
        ArrayList<int[]> output = new ArrayList<int[]>();
       
        // If decrementer is both 1 at abs val, then its on diagnol. Set piece to bishop.
        String piece = "rook";  
        if (Math.abs(decrementX) == 1 && Math.abs(decrementY) == 1) piece = "bishop"; 

        //Makes sure to skip checking the king for piece
        int x = getX() + decrementX; 
        int y = getY() + decrementY; 

        while (inBounds(y, x)){ // While in bounds of chess board

            int[] coords = {y, x}; 
            output.add(coords);         //Add coords leading up to first piece encounter
            
            //First piece encounter
            if (boardMatrix[y][x] != null) { 

                //If encounter is opposite color and is checking king return ouput else return null
                if ((boardMatrix[y][x].piece.equals(piece) || boardMatrix[y][x].piece.equals("queen")) && !boardMatrix[y][x].color.equals(this.color)) return output;
                break;
            }
            x += decrementX;
            y += decrementY;
        }

        return null;
    }


    //Adds Pawn Left check in ArrayList<int[]> Form
    private ArrayList<int[]> addPawnLeftCheck(Base[][] boardMatrix){ 
        ArrayList<int[]> output = new ArrayList<int[]>();
        int possibleX1 = getY() - 1, possibleY;

        //King can only be checked by pond in front of it
        if (color.equals("white")) possibleY = getY() - 1;           
        else possibleY = getY() + 1;                                 

        //Check if there is pawn of opposite color checking on left
        if (inBounds(possibleY, possibleX1) && boardMatrix[possibleY][possibleX1] != null && boardMatrix[possibleY][possibleX1].piece.equals("pawn") && !boardMatrix[possibleY][possibleX1].color.equals(this.color)){
            int[] coords = {possibleY, possibleX1};
            output.add(coords);
            return output;
        }

        return null;
    }


    //Adds Pawn Right check in ArrayList<int[]> Form
    private ArrayList<int[]> addPawnRightCheck(Base[][] boardMatrix){
        ArrayList<int[]> output = new ArrayList<int[]>();
        int possibleX2 = getX() + 1, possibleY;

        
        //King can only be checked by pond in front of it
        if (color.equals("white")) possibleY = getY() - 1;            
        else possibleY = getY()+ 1; 
        
        //Check if there is pawn of opposite color checking on right
        if (inBounds(possibleY, possibleX2) && boardMatrix[possibleY][possibleX2] != null && boardMatrix[possibleY][possibleX2].piece.equals("pawn") && !boardMatrix[possibleY][possibleX2].color.equals(this.color)){
            int[] coords2 = {possibleY, possibleX2};
            output.add(coords2);

            return output;
        }

        return null;        
    }

}