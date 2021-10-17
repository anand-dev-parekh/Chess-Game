package game;

public class Rook extends Piece{
    public Rook(String color, String piece, int row, int col){
        super(color, piece, row, col);
    }

    public boolean validMove(Piece[][] grid, int row, int col){
        //If the move is the same as the starting position return false
        if(this.col == col || this.row == row) return false;
        //If final position is taken by piece of same color return false - CHANGE TO GETTERs AND SETTERS
        if(grid[row][col] != null && grid[row][col].color.compareTo(this.color) == 0) return false;
        //If both the row and col change return false
        if(this.row != row && this.col != col) return false;

        int colOffset = this.col - col;
        int rowOffset = this.row - row;

        int curCol = this.col;
        int curRow = this.row;

        for(int i = 0; i < Math.max(Math.abs(colOffset), Math.abs(rowOffset))-1; i++){
            curCol += colOffset/Math.abs(colOffset);
            rowOffset += rowOffset/Math.abs(rowOffset);

            if(grid[curRow][curCol] != null) return false;
        }

        return true;
    }
}
