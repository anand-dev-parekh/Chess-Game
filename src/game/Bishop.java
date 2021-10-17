package game;

public class Bishop extends Piece{

    public Bishop(String color, String piece, int col, int row){
        super(color, piece, col, row);
    }

    public boolean validMove(Piece[][] grid, int col, int row){
        //If the move is the same as the starting position return false
        if(this.col == col || this.row == row) return false;
        //If final position is taken by piece of same color return false - CHANGE TO GETTERs AND SETTERS
        if(grid[row][col] != null && grid[row][col].color.compareTo(this.color) == 0) return false;

        int colOffset = this.col - col;
        int rowOffset = this.row - row;

        //If change in row != change in col return false
        if(Math.abs(colOffset) != Math.abs(rowOffset)) return false;

        //Board Traversal Nodes
        int curCol = this.col;
        int curRow = this.row;

        //Check positions in-between
        for(int i = 0; i < Math.abs(colOffset)-1; i++){
            curCol += colOffset/Math.abs(colOffset);
            curRow += rowOffset/Math.abs(rowOffset);

            if(grid[curRow][curCol] != null){
                return false;
            }
        }

        //If it passes everything return true
        return true;
    }
}